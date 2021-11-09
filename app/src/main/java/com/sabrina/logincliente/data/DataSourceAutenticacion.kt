package com.sabrina.logincliente.data

import com.google.firebase.auth.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sabrina.logincliente.valueobject.Resource
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class DataSourceAutenticacion {

    val db = Firebase.database
    val reference = db.getReference("clientes")
    val storedVerificationId=""



    suspend fun autenticacionFacebook(token: String): Resource<Boolean> {

        try {

            val credencial = FacebookAuthProvider.getCredential(token)
            FirebaseAuth.getInstance().signInWithCredential(credencial).await()
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }

    suspend fun autenticacionTelefono(credential: PhoneAuthCredential): Resource<Boolean> {

        try {
            FirebaseAuth.getInstance().signInWithCredential(credential).await()
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }


}