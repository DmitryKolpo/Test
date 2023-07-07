package com.demacia.test.ui.screens.main

import androidx.lifecycle.ViewModel
import com.demacia.test.domain.model.Point
import com.demacia.test.domain.repos.Repository
import com.demacia.test.ui.screens.main.state.Effect
import com.demacia.test.ui.screens.main.state.State
import com.demacia.test.ui.screens.main.state.UiState
import com.demacia.test.ui.uiutils.postEffect
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
class MainViewModel @Inject constructor(
    private val repository: Repository,
) : ContainerHost<State, Effect>, ViewModel() {

    internal val uiState: Flow<UiState>
        get() = container.stateFlow.map { it.toUiState() }.flowOn(Dispatchers.Default)

    override val container = container<State, Effect>(
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
        data class OnGoClick(val count: String) : Intent
    }

    private sealed interface Event {
        object PointsLoading : Event
        class PointsLoaded(val points: List<Point>) : Event
        class PointsFailed(val t: Throwable) : Event
    }

    private fun act(state: State, intent: Intent): Flow<Event> {
        return when (intent) {
            is Intent.OnGoClick -> loadData(state, intent.count)
        }
    }

    private fun loadData(state: State, count: String): Flow<Event> = flow {
        if (state.pointsLoading) return@flow

        emit(Event.PointsLoading)
        //TODO: implement more user friendly input validation
        val count = count.toIntOrNull()
        if (count == null || count !in 1..1000) {
            emit(Event.PointsFailed(Exception("Incorrect input")))
            postEffect(Effect.ShowError)
            return@flow
        }

        val points = repository.getPoints(count)
        emit(Event.PointsLoaded(points))
        postEffect(Effect.OpenChartScreen)
    }.catch {
        emit(Event.PointsFailed(it))
        postEffect(Effect.ShowError)
    }

    private fun reduce(state: State, event: Event): State {
        return when (event) {
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
            loading = pointsLoading,
        )
    }
}