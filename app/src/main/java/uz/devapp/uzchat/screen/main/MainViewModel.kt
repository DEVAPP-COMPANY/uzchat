package uz.devapp.uzchat.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.devapp.uzchat.data.model.ChatMessagesModel
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.data.model.UserModel
import uz.devapp.uzchat.data.model.sealed.DataResult
import uz.devapp.uzchat.data.repository.MainRepository
import uz.devapp.uzchat.utils.PrefUtils
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _chatListLiveData = MutableLiveData<List<ChatModel>>()
    var chatListLiveData: LiveData<List<ChatModel>> = _chatListLiveData

    private var _chatMessagesLiveData = MutableLiveData<ChatMessagesModel>()
    var chatMessagesLiveData: LiveData<ChatMessagesModel> = _chatMessagesLiveData

    private var _searchFriendLiveData = MutableLiveData<UserModel>()
    var searchFriendLiveData: LiveData<UserModel> = _searchFriendLiveData

    private var _addFriendLiveData = MutableLiveData<Boolean>()
    var addFriendLiveData: LiveData<Boolean> = _addFriendLiveData

    fun getUser() {
        viewModelScope.launch {
            when (val result = repository.getUser()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setUser(result.result)
                }
            }
        }
    }

    fun getChatList() {
        viewModelScope.launch {
            when (val result = repository.getChatList()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _chatListLiveData.value = result.result ?: listOf()
                }
            }
        }
    }

    fun getChatMessageList(chatId: Int) {
        viewModelScope.launch {
            when (val result = repository.getChatMessages(chatId)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _chatMessagesLiveData.value = result.result!!
                }
            }
        }
    }

    fun searchFriend(phone: String) {
        viewModelScope.launch {
            _progressLiveData.value = true
            when (val result = repository.searchFriend(phone)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _searchFriendLiveData.value = result.result!!
                }
            }
            _progressLiveData.value = false
        }
    }

    fun addFriend(friendId: Int) {
        viewModelScope.launch {
            _progressLiveData.value = true
            when (val result = repository.addChat(friendId)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _addFriendLiveData.value = true
                }
            }
            _progressLiveData.value = false
        }
    }
}