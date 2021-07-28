package com.example.example_application.data

import com.example.example_application.model.Repository

interface GithubCache {
    fun insertRepos(repos : List<Repository>)

    fun loadRepos(query : String): List<Repository>

}