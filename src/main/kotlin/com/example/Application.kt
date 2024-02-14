package com.example

import com.example.crudData.UserData
import com.example.data.emissionData.UserEmissionDataSource
import com.example.data.emissionData.UserEmissionDataSourceImpl
import com.example.data.user.MongoUserDataSource
import com.example.data.user.User
import com.example.data.user.UserDataSource
import com.example.plugins.*
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val mongoPw= System.getenv("MONGO_PW")
    val dbName = "ktor-auth"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://aaryagupta2003:$mongoPw@cluster0.vgmsqf1.mongodb.net/$dbName?retryWrites=true&w=majority"
    ).coroutine
        .getDatabase(dbName)

    val database = db.getCollection<UserData>()

    val  userEmissionDataSource = UserEmissionDataSourceImpl(db)

    val userEmissionData = UserEmissionDataSourceImpl(db)
    val userDataSource = MongoUserDataSource(db)
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L* 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

// for testing purpose
//    GlobalScope.launch {
//        val user = User(
//            userName = "test",
//            password = "test-password",
//            salt = "salt"
//        )
//        UserDataSource.insertUser(user)
//    }

    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(userDataSource , hashingService, tokenService , tokenConfig, userEmissionData)
}
