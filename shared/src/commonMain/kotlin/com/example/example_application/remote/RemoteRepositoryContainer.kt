package com.example.example_application.remote

import kotlinx.serialization.Serializable

@Serializable
data class RemoteRepositoryContainer(val total_count : Int,
                                     val incomplete_results : Boolean,
                                     val items : List<RemoteRepository>)
// man benutzt Lists statt Arrays in Kotlin
