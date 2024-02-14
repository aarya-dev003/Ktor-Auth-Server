package com.example.data.emissionData

import com.example.crudData.UserData

interface UserEmissionDataSource  {
    suspend fun getUserByUsername(id : String) : UserData?
    suspend fun insertUserEmission(user : UserData) : Boolean

}