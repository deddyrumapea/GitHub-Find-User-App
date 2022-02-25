package com.romnan.githubfinduser.feature_user_detail.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romnan.githubfinduser.core.domain.util.Resource
import com.romnan.githubfinduser.feature_user_detail.domain.use_case.UserDetailUseCase
import com.romnan.githubfinduser.feature_user_detail.presentation.followers_list.FollowersListState
import com.romnan.githubfinduser.feature_user_detail.presentation.following_list.FollowingListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val useCase: UserDetailUseCase
) : ViewModel() {
    private val _userDetailState = MutableLiveData(UserDetailState())
    val userDetailState: LiveData<UserDetailState> = _userDetailState

    private val _followersListState = MutableLiveData(FollowersListState())
    val followersListState: LiveData<FollowersListState> = _followersListState

    private val _followingListState = MutableLiveData(FollowingListState())
    val followingListState: LiveData<FollowingListState> = _followingListState

    private val _isFav = MutableLiveData(false)
    val isFav: LiveData<Boolean> = _isFav

    private var _username: String? = null

    fun onEvent(event: UserDetailEvent) {
        when (event) {
            is UserDetailEvent.UsernameReceived -> {
                _username = event.username
                fetchUserDetail()
                fetchUserFollowersList()
                fetchUserFollowingList()
                fetchFavState()
            }

            is UserDetailEvent.ToggleFav -> toggleFavUser()
        }
    }

    private fun fetchFavState() {
        val username = _username ?: return
        viewModelScope.launch { _isFav.value = useCase.isFavUser(username) }
    }

    private fun toggleFavUser() {
        val user = userDetailState.value?.userDetail?.toUser() ?: return
        val isFav = _isFav.value ?: return

        viewModelScope.launch {
            if (isFav) useCase.deleteFavUser(user)
            else useCase.insertFavUser(user)
            fetchFavState()
        }
    }

    private fun fetchUserDetail() {
        val username = _username ?: return
        viewModelScope.launch {
            useCase.getUserDetail(username).onEach { result ->
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

    private fun fetchUserFollowersList() {
        val username = _username ?: return
        viewModelScope.launch {
            useCase.getUserFollowersList(username).onEach { result ->
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

    private fun fetchUserFollowingList() {
        val username = _username ?: return

        viewModelScope.launch {
            useCase.getUserFollowingList(username).onEach { result ->
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