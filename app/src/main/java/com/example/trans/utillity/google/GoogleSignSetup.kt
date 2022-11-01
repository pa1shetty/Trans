package com.example.trans.utillity.google

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.example.trans.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.FirebaseException
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class GoogleSignSetup @Inject constructor(@ActivityContext private val context: Context) {
    fun beginSignIn(
        onSuccess: (intentSenderRequest: IntentSenderRequest) -> Unit,
        onError: (exception: Exception) -> Unit,

        ) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(context as Activity) { result ->
                try {
                    onSuccess(
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    )

                } catch (e: IntentSender.SendIntentException) {
                    onError(
                        e
                    )
                }
            }
            .addOnFailureListener(context) { e ->
                onError(
                    e
                )
            }
    }

    val oneTapClient: SignInClient by lazy { Identity.getSignInClient(context) }
    private val signInRequest: BeginSignInRequest by lazy {
        BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
    fun getGoogleCredential(data: Intent?) =oneTapClient.getSignInCredentialFromIntent(data)
    init {

    }
}