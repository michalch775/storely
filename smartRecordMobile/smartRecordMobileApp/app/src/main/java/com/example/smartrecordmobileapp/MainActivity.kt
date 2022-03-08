package com.example.smartrecordmobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartrecordmobileapp.databinding.ActivityMainBinding
import com.example.smartrecordmobileapp.ui.scanning.Scan
import com.example.smartrecordmobileapp.ui.scanning.ScanningFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_store, R.id.navigation_rentals, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // próbuje zrobić tak, żeby porównać aktualny fragment z fragmentem skanowania, jeśli to to samo to chowam pasek nawigacji
        // można też spróbować dostać się do frgamentu nawigacji poprzez fragment skanowania
        // może dałoby się użyć tutaj liveData - jeśli fragment skanowania jest używany zmienia wartość zmiennej, mainActivity to widzi i chowa pasek, jak skanowanie nie jest używane to main activity włącza pasek spowrotem

        //dobra, poza tym to trzeba jeszcze ogarnąć tak żeby fragment skanowania w ogóle działał


//        val btnGoToScan = findViewById<Button>(R.id.btnGoToScan)
//
//        btnGoToScan.setOnClickListener {
//            val Intent = Intent(this, Scan::class.java)
//            startActivity(Intent)
//        }
    }

}


// w projekcie będę używał aktywności (activities), żywych danych (live data), fragmentów (fragments) i modeli widoków (view model) i wątków (threads)
// live data jest jeszcze do ogarnięcia, nie do końca rozumiem na czym polega jego działanie