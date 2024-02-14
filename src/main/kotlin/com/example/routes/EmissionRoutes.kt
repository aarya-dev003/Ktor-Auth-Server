package com.example.routes

import com.example.crudData.UserData
import com.example.data.requests.EmissionDataRequests
import io.ktor.server.application.*
import io.ktor.server.request.*
import com.example.data.emissionData.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserData(
    UserEmissionData : UserEmissionDataSource
){
    route("/get-user-data"){
        get{
            val username = call.receive<EmissionDataRequests>().username
            val user = UserEmissionData.getUserByUsername(username)
            user?.let {
                call.respond(
                    HttpStatusCode.OK, it
                )
            }?: call.respond(
                HttpStatusCode.OK, "There is no such user"
            )
        }
    }
    route ("/insert-user-data"){
        post{
            val request = try {
                call.receive<UserData>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            if(UserEmissionData.insertUserEmission(request)){
                call.respond(HttpStatusCode.OK, "Data Added Succesfully")
            }else{
                call.respond(HttpStatusCode.Conflict, "Some error occured")
            }
        }
    }
}