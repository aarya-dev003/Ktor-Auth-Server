package com.example.crudData

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.text.SimpleDateFormat


data class UserData(
    @BsonId
    val username : String,
    val emission : String,
    val timeStamp : SimpleDateFormat,
    @BsonId
    val id : ObjectId = ObjectId()
)
