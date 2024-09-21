package com.faceplugin.facerecognition.model

import com.google.gson.annotations.SerializedName

data class EmailModel(
    @SerializedName("email")
    var email: String? // camilla@gmail.com
)