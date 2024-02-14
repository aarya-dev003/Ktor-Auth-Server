package com.example.data.emissionData

import com.example.crudData.UserData
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserEmissionDataSourceImpl(db: CoroutineDatabase) : UserEmissionDataSource {
    private val users = db.getCollection<UserData>()

    override suspend fun getUserByUsername(id: String): UserData? {
        return users.findOne(UserData::username eq id)
    }

    override suspend fun insertUserEmission(user: UserData): Boolean {
        val userExists: Boolean = users.findOneById(user.username) != null
        return if (userExists) {
            users.updateOneById(user.username, user).wasAcknowledged()
        } else {
            users.insertOne(user).wasAcknowledged()
        }
    }

}
