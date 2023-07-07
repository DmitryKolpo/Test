package com.demacia.test.ui.screens.chart

import androidx.lifecycle.ViewModel
import com.demacia.test.domain.model.Point
import com.demacia.test.domain.storage.PointsMemoryStorage
import com.demacia.test.ui.screens.chart.state.State
import com.demacia.test.ui.screens.chart.state.UiState
import com.demacia.test.ui.components.linechart.data.ChartData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val pointsMemoryStorage: PointsMemoryStorage,
) : ContainerHost<State, Any>, ViewModel() {

    internal val uiState: Flow<UiState>
        get() = container.stateFlow.map { it.toUiState() }.flowOn(Dispatchers.Default)

    override val container = container<State, Any>(
        State()
    ) {
        handleIntent(Intent.LoadData)
    }

    internal fun handleIntent(intent: Intent) {
        intent {
            act(state, intent).collect { event ->
                reduce { reduce(state, event) }
            }
        }
    }

    sealed interface Intent {
        object LoadData : Intent
    }

    private sealed interface Event {
        class PointsLoaded(val points: List<Point>) : Event
    }

    private fun act(state: State, intent: Intent): Flow<Event> {
        return when (intent) {
            is Intent.LoadData -> loadData()
        }
    }

    private fun loadData(): Flow<Event> = flow {
        val points = pointsMemoryStorage.getPoints() ?: emptyList()
        emit(Event.PointsLoaded(points))
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
            is Event.PointsLoaded -> state.copy(points = event.points)
        }
    }

    private fun State.toUiState(): UiState {
        val chartData = ChartData(
            entries = points
                .sortedBy { it.x }
                .map { ChartData.Entry(it.x, it.y) },
        )

        return UiState(
            points = points,
            chartData = chartData,
        )
    }
}