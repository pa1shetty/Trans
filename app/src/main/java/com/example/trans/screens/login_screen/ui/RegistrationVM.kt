package com.example.trans.screens.login_screen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trans.data.database.AppDatabase
import com.example.trans.data.datastore.DataStoreRepository
import com.example.trans.network.Enums.RegStatus
import com.example.trans.network.Enums.RequestStatus
import com.example.trans.network.NetworkRepository
import com.example.trans.network.responses.UserDetails
import com.example.trans.utillity.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationVM @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val networkInterface: NetworkRepository,
    private val database: AppDatabase,
) : ViewModel() {
    init {
        getUserPhoneNumber()
    }


    private val _userPhoneNumber = SingleLiveEvent<String>()
    val userPhoneNumber: LiveData<String>
        get() = _userPhoneNumber

    private val _requestStatusLiveData = SingleLiveEvent<RequestStatus>()
    val requestStatusLiveData: LiveData<RequestStatus>
        get() = _requestStatusLiveData

    fun sendUserData(userDetails: UserDetails) {
        _requestStatusLiveData.value = RequestStatus.REQ_PROGRESS
        viewModelScope.launch(Dispatchers.IO) {
            userDetails.usrPhnNo =
                database.getUserDetailsDao().getUser(dataStoreRepository.userId.first()).usrPhnNo
            userDetails.usrId = dataStoreRepository.userId.first()
            try {
                if (networkInterface.sendUserData(userDetails)) {
                    userDetails.regStatus = RegStatus.REG.status
                    database.getUserDetailsDao().saveUser(userDetails)
                }
                _requestStatusLiveData.postValue(RequestStatus.REQ_SUCCESS)
            } catch (e: Exception) {
                _requestStatusLiveData.postValue(RequestStatus.REQ_ERROR)
            }
        }
    }

    private fun getUserPhoneNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            _userPhoneNumber.postValue(dataStoreRepository.userPhoneNumber.first())
        }
    }
}