package com.example.data.user

interface UserDataSource {
    suspend fun getUserbyName (username : String) : User?
    suspend fun  insertUser(user: User) : Boolean
}