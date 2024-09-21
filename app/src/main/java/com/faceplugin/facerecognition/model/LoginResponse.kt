package com.faceplugin.facerecognition.model

data class LoginResponse(
    val `data`: LoginResponseData,
    val message: String,
    val status: Boolean
)

data class LoginResponseData(
    val createdAt: String,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: Int,
    val updatedAt: String
)
