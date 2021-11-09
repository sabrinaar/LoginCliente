package com.sabrina.logincliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}