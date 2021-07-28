package com.example.example_application.data

import com.example.example_application.model.Repository

interface GithubRemote {

    suspend fun fetchRepositories(query : String) : List<Repository>

}