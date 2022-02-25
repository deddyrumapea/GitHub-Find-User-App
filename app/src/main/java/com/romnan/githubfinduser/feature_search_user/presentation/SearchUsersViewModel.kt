package com.romnan.githubfinduser.feature_search_user.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_search_user.domain.use_case.SearchUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUsersViewModel @Inject constructor(
    private val searchUsers: SearchUsers
) : ViewModel() {
    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _searchUsersState = MutableLiveData(SearchUsersState())
    val searchUsersState: LiveData<SearchUsersState> = _searchUsersState

    fun onEvent(event: SearchUsersEvent) {
        if (event is SearchUsersEvent.QueryTextSubmit) {
            _searchQuery.value = event.query
            fetchUsers()
        }
    }

    private fun fetchUsers() {
        // TODO: add fetch user job
        viewModelScope.launch {
            searchQuery.value?.let {
                searchUsers(query = it).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _searchUsersState.value = searchUsersState.value?.copy(
                                usersList = result.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = ""
                            )
                        }

                        is Resource.Loading -> {
                            _searchUsersState.value = searchUsersState.value?.copy(
                                usersList = result.data ?: emptyList(),
                                isLoading = true,
                                errorMessage = ""
                            )
                        }

                        is Resource.Error -> {
                            _searchUsersState.value = searchUsersState.value?.copy(
                                usersList = result.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = result.message ?: "Something went wrong"
                            )
                        }
                    }
                }.launchIn(this)
            }
        }
    }
}