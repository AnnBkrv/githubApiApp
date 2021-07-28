package com.example.example_application.remote

import com.example.example_application.model.Repository
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRepository(val full_name: String,
                            val description: String?,
                            val stargazers_count : Long?,
                            val id : Long)


// extension
fun RemoteRepository.mapToRepository() = Repository(
        id = id,
        name = full_name,
        description = description,
        stars = stargazers_count?.toInt()
    )
