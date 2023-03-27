package com.example.trans.screens.login_screen.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trans.data.database.AppDatabase
import com.example.trans.data.datastore.DataStoreRepository
import com.example.trans.network.Enums.NavigateTo
import com.example.trans.network.Enums.RegStatus
import com.example.trans.network.Enums.RequestStatus
import com.example.trans.network.NetworkRepository
import com.example.trans.network.responses.UserDetails
import com.example.trans.utillity.SingleLiveEvent
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val networkInterface: NetworkRepository,
    private val database: AppDatabase,
) :
    ViewModel() {
    suspend fun userLoggedIn(firebaseUSer: FirebaseUser) {
        dataStoreRepository.userLoggedIn()
        dataStoreRepository.saveUserId(firebaseUSer.uid)
        dataStoreRepository.saveUserPhoneNo(phoneNumber)
        database.getUserDetailsDao()
            .saveUser(
                UserDetails(
                    usrId = firebaseUSer.uid,
                    usrPhnNo = phoneNumber,
                    regStatus = RegStatus.FIREBASE_REG.status
                )
            )
    }


    private val _ifUserIsWhitelistedResponseLiveData = SingleLiveEvent<Boolean>()
    val ifUserIsWhitelistedResponseLiveData: LiveData<Boolean>
        get() = _ifUserIsWhitelistedResponseLiveData
    private val _requestStatusLiveData = SingleLiveEvent<RequestStatus>()
    val requestStatusLiveData: LiveData<RequestStatus>
        get() = _requestStatusLiveData

    private val _navigationLiveData = SingleLiveEvent<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo>
        get() = _navigationLiveData
    private val _userDataStatusLiveData = SingleLiveEvent<RequestStatus>()
    val userDataStatusLiveData: LiveData<RequestStatus>
        get() = _userDataStatusLiveData

    suspend fun checkIfUserIsWhitelisted() {
        try {
            _requestStatusLiveData.postValue(RequestStatus.REQ_PROGRESS)
            _ifUserIsWhitelistedResponseLiveData.postValue(
                networkInterface.checkIfUserIsWhitelisted(
                    phoneNumber
                )
            )
            _requestStatusLiveData.postValue(RequestStatus.REQ_SUCCESS)

        } catch (e: ApiException) {
            _requestStatusLiveData.postValue(RequestStatus.REQ_ERROR)
        }
    }

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userDataStatusLiveData.postValue(RequestStatus.REQ_PROGRESS)
                val userData =
                    networkInterface.getUserData(dataStoreRepository.userPhoneNumber.first())
                Log.d("test121", "getUserData: "+userData.regStatus)
                val regStatus=userData.regStatus;
                if (regStatus == RegStatus.REG.status) {
                    userData.user.regStatus = RegStatus.REG.status
                    database.getUserDetailsDao().saveUser(userData.user)
                    _navigationLiveData.postValue(NavigateTo.HOME_SCREEN)
                } else {
                    _navigationLiveData.postValue(NavigateTo.REG_SCREEN)
                }
                _userDataStatusLiveData.postValue(RequestStatus.REQ_SUCCESS)

            } catch (e: ApiException) {
                _userDataStatusLiveData.postValue(RequestStatus.REQ_ERROR)
            } catch (e: Exception) {
                Log.d("text121", "getUserData: "+e)
                _userDataStatusLiveData.postValue(RequestStatus.REQ_ERROR)

            }
        }
    }


    var phoneNumber = ""
    var verificationId = ""
    var otp = "111111"
    var resendingToken: PhoneAuthProvider.ForceResendingToken? = null

}