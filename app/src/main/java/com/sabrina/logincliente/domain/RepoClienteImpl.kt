package com.sabrina.logincliente.domain

import android.net.Uri
import com.sabrina.logincliente.data.DataSourceCliente
import com.sabrina.logincliente.valueobject.Resource

class RepoClienteImpl (private val dataSourceCliente: DataSourceCliente) : RepoCliente{
    override suspend fun crearCliente(fieldpath: Uri, id_r:String, id_u:String) : Resource<Boolean> {
        return dataSourceCliente.crearCliente(fieldpath,id_r,id_u)
    }
}