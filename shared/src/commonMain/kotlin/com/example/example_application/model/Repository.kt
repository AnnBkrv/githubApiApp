package com.example.example_application.model

data class Repository(
    var id : Long,
    var name : String,
    var description : String?,
    var stars : Int?
)