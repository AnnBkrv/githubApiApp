package com.example.example_application.domain

import com.example.example_application.model.Repository

interface GithubService {

    suspend fun getRepos(query : String) : List<Repository>

}