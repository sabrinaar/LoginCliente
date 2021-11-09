package com.sabrina.logincliente.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sabrina.logincliente.domain.RepoCliente

class ViewModelFactoryCliente (private val repoP: RepoCliente): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepoCliente::class.java).newInstance(repoP)
    }
}