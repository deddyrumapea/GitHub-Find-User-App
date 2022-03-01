package com.romnan.githubfinduser.feature_fav_users_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_fav_users_list.domain.use_case.FavUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavUsersListViewModel @Inject constructor(
    private val useCase: FavUsersListUseCase
) : ViewModel() {

    private val _favUsersListState = MutableLiveData(FavUsersListState())
    val favUsersListState: LiveData<FavUsersListState> = _favUsersListState

    private var findAllJob: Job? = null

    init {
        findAll()
    }

    fun onEvent(event: FavUsersListEvent) {
        if (event is FavUsersListEvent.ActivityResume) findAll()
    }

    private fun findAll() {
        findAllJob?.cancel()
        findAllJob = useCase.findAll().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favUsersListState.value = favUsersListState.value?.copy(
                        usersList = result.data ?: emptyList(),
                        isLoading = false,
                        errorMessage = ""
                    )
                }
                is Resource.Loading -> {
                    _favUsersListState.value = favUsersListState.value?.copy(
                        usersList = result.data ?: emptyList(),
                        isLoading = true,
                        errorMessage = ""
                    )
                }
                is Resource.Error -> {
                    _favUsersListState.value = favUsersListState.value?.copy(
                        usersList = result.data ?: emptyList(),
                        isLoading = false,
                        errorMessage = result.message ?: "Something went wrong"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}