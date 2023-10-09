package dev.haqim.myrawg.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.haqim.myrawg.domain.model.GamesListItem
import dev.haqim.myrawg.domain.usecase.GetGameListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GamesListVM @Inject constructor(
    private val getGameListUseCase: GetGameListUseCase
): ViewModel() {
    private val _state = MutableStateFlow(GamesListUiState())
    val state get() = _state.asStateFlow()
    val pagingFlow: Flow<PagingData<GamesListItem>>

    init {
        pagingFlow = state.map { it.search }
            .distinctUntilChanged()
            .flatMapLatest { search ->
                getGamesList(search).cachedIn(viewModelScope)
            }
    }
    
    private fun getGamesList(search: String?) = getGameListUseCase(search)
    private var searchJob: Job? = null
    fun searchGames(query: String?){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            _state.update { state ->
                state.copy(search = query)
            }
        }
    }
}

data class GamesListUiState(
    val search: String? = null
)