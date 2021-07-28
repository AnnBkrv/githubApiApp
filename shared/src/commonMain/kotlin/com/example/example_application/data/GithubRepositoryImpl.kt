package com.example.example_application.data

import com.example.example_application.domain.GithubService
import com.example.example_application.model.Repository

class GithubServiceImpl (private val cache : GithubCache,
    private val remote:GithubRemote) : GithubService {


    override suspend fun getRepos(query: String) : List<Repository> {
        val fetched = remote.fetchRepositories(query)
        cache.insertRepos(fetched)
        return cache.loadRepos(query)
    }
}


// TODO: Use the githubServiceImplementation in Swift
// add a search field and make it work
// go over all the new information
// do something else on startup. a random item out of a list of search terms like "kotlin"/ "swift", search for most popular repos