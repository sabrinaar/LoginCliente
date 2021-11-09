package com.sabrina.logincliente.data

import android.net.Uri
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sabrina.logincliente.valueobject.Resource

class DataSourceCliente {

    val db = Firebase.database
    val reference = db.getReference("clientes")


    ////////////////////////////////// ADD /////////////////////////////////////
    /**
     * AÑADIR CLIENTE A FIREBASE REALTIME DATABASE
     */
    suspend fun crearCliente(
        filePath: Uri,
        id_regalo: String,
        id_user: String
    ): Resource<Boolean> {

        return Resource.Success(true)
        //  reference.setValue("")


    }
}