package com.itis.android2coursepart21.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.itis.android2coursepart21.R

class MainActivity : AppCompatActivity() {

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = findController(R.id.container)
    }

    fun AppCompatActivity.findController(
        id: Int
    ) = (supportFragmentManager.findFragmentById(id) as NavHostFragment)
        .navController

}