package com.example.example_application.cache
import com.example.GithubDatabase
import com.example.GithubRepoDatabaseQueries
import com.example.GithubRepos
import com.example.example_application.DriverFactory
import com.example.example_application.data.GithubCache
import com.example.example_application.model.Repository
import com.example.example_application.remote.RemoteRepository
import com.example.example_application.remote.mapToRepository


class GithubCacheImpl(driverFactory : DriverFactory) : GithubCache {
    private val driver = driverFactory.createDriver()
    private val database = GithubDatabase(driver)
    private val repoQueries : GithubRepoDatabaseQueries = database.githubRepoDatabaseQueries

    // Der Name der Klasse basiert sich auf dem Namen des .sq Files

    override fun insertRepos(repos: List<Repository>) {
        repoQueries.transaction {
            // transactions make the processes not parallel, so that the program is not gonna be
            // halted by your call to the database
            // the database determines itself when the most opportune moment is to
            // complete the needed operation
            repos.forEach { repo ->
                repoQueries.insert(
                    id = repo.id,
                    full_name = repo.name,
                    description = repo.description,
                    rating = repo.stars?.toLong()
                )
            }
        }
    }

    override fun loadRepos(query: String): List<Repository> {
        val repoItems = mutableListOf<Repository>()
        val results: List<GithubRepos> = repoQueries.transactionWithResult {
            repoQueries.searchByName(query).executeAsList()
        }
        results.forEach { call ->
            repoItems.add(
                Repository(
                    name = call.full_name,
                    description = call.description,
                    stars = call.rating?.toInt(),
                    id = call.id
                )
            )
        }
        return repoItems
    }


    /*fun searchDatabase(query: String, parameterName: String) : List<GithubRemoteImpl.githubRepo> {
        // the results should be processed to be a list of githubApiCalls
        // and those should be returned by the fct
        val repoItems = mutableListOf<GithubRemoteImpl.githubRepo>()
        if (parameterName == "full_name") {
            val results : List<GithubRepos> = repoQueries.transactionWithResult {
                repoQueries.searchByName(query).executeAsList()
            }
            results.forEach { call ->
                repoItems.add(GithubRemoteImpl.githubRepo(
                    full_name = call.full_name,
                    description = call.description,
                    stargazers_count = call.rating,
                    id = call.id))
            }
            return repoItems

        }
        else if (parameterName == "descr") {
            repoQueries.searchByDescription(descr = query)
        }
        else {
            print("Invalid Parameter Name")
            return
        }
    }*/

}