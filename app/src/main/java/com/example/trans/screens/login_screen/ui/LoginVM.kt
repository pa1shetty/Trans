package com.example.trans.screens.login_screen.ui

import androidx.lifecycle.ViewModel
import com.example.trans.data.datastore.DataStoreRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {
    suspend fun userLoggedIn(firebaseUSer: FirebaseUser) {
        dataStoreRepository.userLoggedIn()
        dataStoreRepository.saveUserId(firebaseUSer.uid)
    }

    var phoneNumber = "9741028810"
    var verificationId = ""
    var otp = "111111"
    var resendingToken: PhoneAuthProvider.ForceResendingToken? = null
}