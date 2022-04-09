package com.ss.moviedb_kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ss.moviedb_kotlin.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}