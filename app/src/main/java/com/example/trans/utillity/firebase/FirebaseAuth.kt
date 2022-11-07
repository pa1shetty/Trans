package com.example.trans.utillity.firebase

import android.app.Activity
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@FragmentScoped
class FirebaseAuth @Inject constructor(@ActivityContext private val context: Context) {
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun startPhoneNumberVerification(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken? = null,
        onVerificationCompleted: (credential: PhoneAuthCredential) -> Unit,
        onVerificationFailed: (e: FirebaseException) -> Unit,
        onCodeSent: (verificationId: String, token: PhoneAuthProvider.ForceResendingToken) -> Unit
    ) {
        val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                onVerificationCompleted(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                onVerificationFailed(e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                onCodeSent(verificationId, token)
            }
        }

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context as Activity) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
        token?.let { options.setForceResendingToken(it) }

        PhoneAuthProvider.verifyPhoneNumber(options.build())
    }

    fun signInWithPhoneAuthCredential(
        credential: AuthCredential,
        signInSuccessFull: (firebaseUSer: FirebaseUser) -> Unit,
        invalidCredential: () -> Unit,
        error: () -> Unit
    ) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                context as Activity
            ) { task ->
                if (task.isSuccessful) {
                    signInSuccessFull(task.result.user!!)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        invalidCredential()
                    } else {
                        error()
                    }
                }
            }
    }

    fun verifyPhoneNumberWithCode(verificationId: String, code: String): AuthCredential {
        return PhoneAuthProvider.getCredential(verificationId, code)
    }

    fun getGoogleAuthCredential(idToken: String) = GoogleAuthProvider.getCredential(idToken, null)


}