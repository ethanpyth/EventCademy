package com.yvkalume.eventcademy.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.yvkalume.eventcademy.data.entity.User
import com.yvkalume.eventcademy.data.util.FirestoreCollections
import com.yvkalume.eventcademy.data.util.toDomainUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signInWithCredential(credential: AuthCredential) {
        withContext(Dispatchers.IO) {
            try {
                val user = firebaseAuth.signInWithCredential(credential).await().user
                saveUserToFirestore(user)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    private suspend fun saveUserToFirestore(firebaseUser: FirebaseUser?) {
        withContext(Dispatchers.IO) {
            val user = firebaseUser?.toDomainUser()
            if (user != null) {
                firestore.collection(FirestoreCollections.USERS).document(user.uid).set(user).await()
            }
        }
    }

    suspend fun getCurrentUser() : User {
       return withContext(Dispatchers.IO) {
           val uid = firebaseAuth.uid
           val task = firestore.document("${FirestoreCollections.USERS}/$uid").get()
           return@withContext task.await().toObject(User::class.java)!!
       }
    }
}