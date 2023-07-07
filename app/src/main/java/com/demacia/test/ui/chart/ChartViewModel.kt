package com.demacia.test.ui.chart

import androidx.lifecycle.ViewModel
import com.demacia.test.domain.model.Point
import com.demacia.test.domain.repos.Repository
import com.demacia.test.ui.chart.linechart.data.ChartData
import com.demacia.test.ui.chart.state.State
import com.demacia.test.ui.chart.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val repository: Repository,
) : ContainerHost<State, Any>, ViewModel() {

    internal val uiState: Flow<UiState>
        get() = container.stateFlow.map { it.toUiState() }.flowOn(Dispatchers.Default)

    override val container = container<State, Any>(
        State()
    )

    internal fun handleIntent(intent: Intent) {
        intent {
            act(state, intent).collect { event ->
                reduce { reduce(state, event) }
            }
        }
    }

    sealed interface Intent {
        object OnGoClick : Intent
        data class OnInputChange(val count: String) : Intent
    }

    private sealed interface Event {
        object Refreshing : Event

        object PointsLoading : Event
        class PointsLoaded(val points: List<Point>) : Event
        class PointsFailed(val t: Throwable) : Event

        data class ChangeCount(val count: Int?) : Event
    }

    private fun act(state: State, intent: Intent): Flow<Event> {
        return when (intent) {
            is Intent.OnGoClick -> loadData(state)
            is Intent.OnInputChange -> changeCount(intent.count)
        }
    }

    private fun loadData(state: State): Flow<Event> = flow<Event> {
        emit(Event.PointsLoading)
        val count = state.count ?: return@flow
        //TODO: validate input count and show error if something wrong
        val points = repository.getPoints(count)
        println("test points=$points")
        emit(Event.PointsLoaded(points))
    }.catch {
        emit(Event.PointsFailed(it))
    }

    private fun changeCount(count: String): Flow<Event> = flow {
        //TODO: implement debounce
        //TODO: implement filter only integer
        val count: Int? = count.toIntOrNull()
        emit(Event.ChangeCount(count))
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.Refreshing -> TODO()
            is Event.PointsLoading -> state.copy(
                pointsLoading = true,
                pointsError = null,
            )

            is Event.PointsFailed -> state.copy(
                pointsLoading = false,
                pointsError = event.t,
            )

            is Event.PointsLoaded -> state.copy(
                pointsLoading = false,
                points = event.points,
            )

            is Event.ChangeCount -> state.copy(
                count = event.count,
            )
        }
    }

    private fun State.toUiState(): UiState {
        val chartData = ChartData(
            entries = points
                .sortedBy { it.x }
                .map { ChartData.Entry(it.x, it.y) },
        )

        return UiState(
            count = count?.toString() ?: "",
            points = points,
            chartData = chartData,
        )
    }
}