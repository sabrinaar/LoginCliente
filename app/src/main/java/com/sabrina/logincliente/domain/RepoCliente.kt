package com.sabrina.logincliente.domain

import android.net.Uri
import com.sabrina.logincliente.valueobject.Resource

interface RepoCliente {
    suspend fun crearCliente(fieldpath: Uri, id_r: String, id_u: String): Resource<Boolean>

}