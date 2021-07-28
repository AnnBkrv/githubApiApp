package com.example.example_application.remote
import com.example.example_application.data.GithubRemote
import com.example.example_application.model.Repository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

//import io.ktor.routing.*


// File -> invalidate cashes/ restart to restart android studio



class GithubRemoteImpl : GithubRemote {

    override suspend fun fetchRepositories(query : String) : List<Repository> {

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
        //val response: HttpResponse = client.get("https://api.github.com/search/repositories?q=memorize%20in:name")
        val response: HttpResponse = client.get("https://api.github.com/search/repositories?q=$query%20in:name")

        val responseBody: RemoteRepositoryContainer = response.receive()

        return responseBody.items.map { it.mapToRepository() }
    }

    // results from previous api calls are stored using a sqlite database so that the app is functional offline
    // that sqlite database can be used for offline searches

    // get the response from github's api and parse the response, storing it in a structure like in the swift project
    }