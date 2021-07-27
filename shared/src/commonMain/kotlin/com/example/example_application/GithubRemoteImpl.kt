package com.example.example_application
import com.example.GithubDatabase
import com.example.GithubRepoDatabaseQueries
import com.example.GithubRepos
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.engine.cio.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

//import io.ktor.routing.*


// invalidate cashes/ restart to restart android studio

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

// ############################################################################################################
// ############################################################################################################
// ############################################################################################################
// TODO: research repository patterns. cache and remote coordination

class GithubCacheImpl(driverFactory : DriverFactory) {
    val driver = driverFactory.createDriver()
    val database = GithubDatabase(driver)

    val repoQueries : GithubRepoDatabaseQueries = database.githubRepoDatabaseQueries

    // Der Name der Klasse basiert sich auf dem Namen des .sq Files

    // need: inserting calls into the database

    fun insertSearchResults(callList: List<GithubRemoteImpl.githubRepo>){
        // what are transactions??? is it a kotlin concept or a sqldelight concept?
        repoQueries.transaction {
            callList.forEach { repo ->
                repoQueries.insert(
                    id = repo.id,
                    full_name = repo.full_name,
                    description = repo.description,
                    rating = repo.stargazers_count
                )
            }
        }
    }

    fun searchDatabase(query: String) : List<GithubRemoteImpl.githubRepo> {
        // the results should be processed to be a list of githubApiCalls
        // and those should be returned by the fct
        val repoItems = mutableListOf<GithubRemoteImpl.githubRepo>()
        val results: List<GithubRepos> = repoQueries.transactionWithResult {
            repoQueries.searchByName(query).executeAsList()
        }
        results.forEach { call ->
            repoItems.add(
                GithubRemoteImpl.githubRepo(
                    full_name = call.full_name,
                    description = call.description,
                    stargazers_count = call.rating,
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

class GithubRemoteImpl {
    // die beiden Klassen in eigene Datei nehmen; benutze den Style-Guide
    @Serializable
    data class RepoContainer(val total_count : Int, val incomplete_results : Boolean, val items : List<githubRepo>)
    // man benutzt Lists statt Arrays in Kotlin

    @Serializable
    data class githubRepo(val full_name: String, val description: String?, val stargazers_count : Long, val id : Long)
    // use Kotlin's annotations

    suspend fun githubApiCall() : List<githubRepo> {

        // suspend: die fkt kann gestoppt und fortgesetzt werden. die fkt wird entweder in einer
        // suspend fkt oder in einer coroutine aufgerufen
        val client = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    // prevents the invalid mutability exception from appearing when using the return of this in swift code
                    useAlternativeNames = false
                })
            }
        }
        val response: HttpResponse = client.get("https://api.github.com/search/repositories?q=memorize%20in:name")
        val responseBody: RepoContainer = response.receive()

        return responseBody.items
    }

    // results from previous api calls are stored using a sqlite database so that the app is functional offline
    // that sqlite database can be used for offline searches

    // get the response from github's api and parse the response, storing it in a structure like in the swift project
    }