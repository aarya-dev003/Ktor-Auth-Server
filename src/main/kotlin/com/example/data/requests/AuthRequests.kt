package com.example.data.requests

import kotlinx.serialization.Serializable

@Serializable
class AuthRequests (
    val username: String,
    val password : String
)