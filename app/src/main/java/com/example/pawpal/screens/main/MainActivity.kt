package com.example.pawpal.screens.main

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pawpal.app.receiver.Receiver
import com.example.pawpal.app.receiver.Receiver.Companion.ACTION
import com.example.pawpal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: Receiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION)
        receiver = Receiver()
        registerReceiver(receiver, intentFilter)

    }
}