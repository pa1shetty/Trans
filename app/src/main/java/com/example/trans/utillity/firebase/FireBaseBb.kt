package com.example.trans.utillity.firebase

import com.example.trans.network.NetworkInterface
import com.example.trans.network.responses.UserData
import com.example.trans.network.responses.UserDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonObject
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@ViewModelScoped
class FireBaseBb : NetworkInterface {
    val database = Firebase.database


    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getUserById(phoneNumber: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val fruitsRef = database.getReference("whiteListedUsers")
            val query = fruitsRef.orderByValue().equalTo(phoneNumber)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    continuation.resume(snapshot.exists(), null)
                }

                override fun onCancelled(error: DatabaseError) {
                    throw com.example.trans.network.ApiException(error.message)
                }
            })
        }
    }

    override suspend fun configDownload(currentConfigVersion: String): JsonObject {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun checkIfUserIsWhitelisted(phoneNumber: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val fruitsRef = database.getReference("whiteListedUsers")
            val query = fruitsRef.orderByValue().equalTo(phoneNumber)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    continuation.resume(snapshot.exists(), null)
                }

                override fun onCancelled(error: DatabaseError) {
                    throw com.example.trans.network.ApiException(error.message)
                }
            })
        }
    }

    override suspend fun getUserData(phoneNumber: String): UserData {
        TODO("Not yet implemented")
    }

    override suspend fun sendUserData(userDetails: UserDetails): Boolean {
        TODO("Not yet implemented")
    }


}