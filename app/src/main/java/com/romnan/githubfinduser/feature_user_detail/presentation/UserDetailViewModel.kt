package com.romnan.githubfinduser.feature_user_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romnan.githubfinduser.core.util.Resource
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.UserDetailUseCases
import com.romnan.githubfinduser.feature_user_detail.presentation.follower_list.FollowersListState
import com.romnan.githubfinduser.feature_user_detail.presentation.following_list.FollowingListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userDetailUseCases: UserDetailUseCases
) : ViewModel() {
    private val _userDetailState = MutableLiveData(UserDetailState())
    val userDetailState: LiveData<UserDetailState> = _userDetailState

    private val _followersListState = MutableLiveData(FollowersListState())
    val followersListState: LiveData<FollowersListState> = _followersListState

    private val _followingListState = MutableLiveData(FollowingListState())
    val followingListState: LiveData<FollowingListState> = _followingListState

    private var _username: String? = null

    fun onEvent(event: UserDetailEvent) {
        if (event is UserDetailEvent.UsernameReceived) {
            _username = event.username
            _username?.let {
                fetchUserDetail(it)
                fetchUserFollowersList(it)
                fetchUserFollowingList(it)
            }
        }
    }

    private fun fetchUserDetail(username: String) {
        viewModelScope.launch {
            userDetailUseCases.getUserDetail(username).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _userDetailState.value = userDetailState.value?.copy(
                            userDetail = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _userDetailState.value = userDetailState.value?.copy(
                            userDetail = result.data,
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _userDetailState.value = userDetailState.value?.copy(
                            userDetail = result.data,
                            isLoading = false,
                            errorMessage = result.message ?: "Something went wrong"
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun fetchUserFollowersList(username: String) {
        viewModelScope.launch {
            userDetailUseCases.getUserFollowersList(username).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _followersListState.value = followersListState.value?.copy(
                            usersList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _followersListState.value = followersListState.value?.copy(
                            usersList = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _followersListState.value = followersListState.value?.copy(
                            usersList = result.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = result.message ?: "Something went wrong"
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun fetchUserFollowingList(username: String) {
        viewModelScope.launch {
            userDetailUseCases.getUserFollowingList(username).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _followingListState.value = followingListState.value?.copy(
                            usersList = result.data ?: emptyList(),
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                    is Resource.Loading -> {
                        _followingListState.value = followingListState.value?.copy(
                            usersList = result.data ?: emptyList(),
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
                    is Resource.Error -> {
                        _followingListState.value = followingListState.value?.copy(
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