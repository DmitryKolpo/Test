package com.demacia.test.ui.chart

import androidx.lifecycle.ViewModel
import com.demacia.test.domain.model.Point
import com.demacia.test.domain.repos.Repository
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
    }

    private sealed interface Event {
        object Refreshing : Event

        object PointsLoading : Event
        class PointsLoaded(val points: List<Point>) : Event
        class PointsFailed(val t: Throwable) : Event
    }

    private fun act(state: State, intent: Intent): Flow<Event> {
        return when (intent) {
            is Intent.OnGoClick -> loadData()
        }
    }

    private fun loadData(): Flow<Event> = flow<Event> {
        emit(Event.PointsLoading)
        val count = 1 //TODO: implement counter input
        println("test points start")
        val points = repository.getPoints(count)
        println("test points=$points")
        emit(Event.PointsLoaded(points))
    }.catch {
        emit(Event.PointsFailed(it))
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
        }
    }

    private fun State.toUiState(): UiState {
        return UiState(
            points = points,
        )
    }
}