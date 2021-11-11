package com.sabrina.logincliente.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sabrina.logincliente.data.model.Cliente
import com.sabrina.logincliente.valueobject.Resource
import kotlinx.coroutines.tasks.await
import java.util.*

class DataSourceCliente {

    private lateinit var database: DatabaseReference


    ////////////////////////////////// ADD /////////////////////////////////////
    /**
     * AÑADIR CLIENTE A FIREBASE REALTIME DATABASE
     */
    suspend fun crearCliente(
        nombre: String,
        apellido: String,
        edad: String,
        fecha_nac: String
    ): Resource<Boolean> {

        val id: String = UUID.randomUUID().toString().replace("-", "");
        val cliente = Cliente(id, nombre, apellido, edad, fecha_nac)

        try {
            database = Firebase.database.reference
            database.child("cliente").child(cliente.id).setValue(cliente).await()

            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Failure(e)
        }
    }
}