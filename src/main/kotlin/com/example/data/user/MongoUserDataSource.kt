package com.example.data.user

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.eq

class MongoUserDataSource(db : CoroutineDatabase): UserDataSource{
    private val users = db.getCollection<User>()
    override suspend fun getUserbyName(username: String): User? {
        return users.findOne(User :: userName eq username)
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

}