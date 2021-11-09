package com.sabrina.logincliente.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Cliente(
    var id: String = "",
    var nombre: String = "",
    var apellido: String = "",
    var edad: String = "",
    var fecha_nac: String = ""
): Parcelable


