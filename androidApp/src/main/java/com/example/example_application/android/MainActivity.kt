package com.example.example_application.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.example_application.remote.GithubRemoteImpl

//import com.example.kmmsample.shared.Greeting
import android.widget.TextView
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// download the new BETA version of Android Studio to get rid of error messages. this is the stable version.
// canary is the newest version with maybe-working features for when you wanna experiment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        //tv.text = greet()
        runBlocking {
            // diese Fkt wartet, bis diese Fkt ganz durchläuft
            //ne suspend Fkt kann man nur von einer anderen Suspend Fkt oder aus einer Coroutine aufrufen. das ist eine Coroutine
            launch { tv.text = GithubRemoteImpl().fetchRepositories("memorize").toString() } // simultaneous functions
        }
    }
}
