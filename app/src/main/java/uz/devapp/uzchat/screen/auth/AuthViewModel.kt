package uz.devapp.uzchat.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.devapp.uzchat.data.model.response.LoginResponse
import uz.devapp.uzchat.data.model.sealed.DataResult
import uz.devapp.uzchat.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _authResponseData = MutableLiveData<LoginResponse>()
    var authResponseData: LiveData<LoginResponse> = _authResponseData


    fun login(phone: String, password: String) {
        viewModelScope.launch {
            _progressLiveData.value = true
            when (val data = repository.login(phone, password)) {
                is DataResult.Error -> _errorLiveData.value = data.message
                is DataResult.Success -> _authResponseData.value = (data.result)
            }
            _progressLiveData.value = false
        }
    }

    fun registration(fullname: String, phone: String, password: String) {
        viewModelScope.launch {
            _progressLiveData.value = true
            when (val data = repository.registration(fullname, phone, password)) {
                is DataResult.Error -> _errorLiveData.value = data.message
                is DataResult.Success -> _authResponseData.value = (data.result)
            }
            _progressLiveData.value = false
        }
    }
}