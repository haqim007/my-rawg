package dev.haqim.myrawg.ui.gamedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.haqim.myrawg.data.local.entity.GameDetailEntity
import dev.haqim.myrawg.data.mechanism.Resource
import dev.haqim.myrawg.domain.model.BasicMessage
import dev.haqim.myrawg.domain.model.GameDetail
import dev.haqim.myrawg.domain.usecase.AddCollectionUseCase
import dev.haqim.myrawg.domain.usecase.GetGameDetailUseCase
import dev.haqim.myrawg.domain.usecase.RemoveCollectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailVM @Inject constructor(
    private val getGameDetailUseCase: GetGameDetailUseCase,
    private val addCollectionUseCase: AddCollectionUseCase,
    private val removeCollectionUseCase: RemoveCollectionUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(GameDetailUiState())
    val state get() = _state.asStateFlow()
    
    fun getDetail(id: Int){
        viewModelScope.launch { 
            getGameDetailUseCase(id).collectLatest { 
                _state.update { state ->
                    state.copy(
                        detail = it
                    )
                }
            }
        }
    }
    
    fun toggleCollection(){
        state.value.detail.data?.let {
            viewModelScope.launch {
                _state.update { state ->
                    state.copy(
                        toggleCollection = if(state.detail.data?.isCollected == true){
                            removeCollectionUseCase(it.id)
                        }else{
                            addCollectionUseCase(it)
                        }
                    )
                }
            }
        }
        
    }
    
    fun afterToggleCollection(){
        _state.update { state ->
            state.copy(
                toggleCollection = Resource.Idle()
            )
        }
    }
}

data class GameDetailUiState(
    val detail: Resource<GameDetail?> = Resource.Idle(),
    val toggleCollection: Resource<BasicMessage> = Resource.Idle()
)