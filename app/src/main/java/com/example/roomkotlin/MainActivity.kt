package com.example.roomkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //navFragment initialization- base rule
        val navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    //for navigateUp to different screen - base rule
    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this,R.id.fragment),null
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}