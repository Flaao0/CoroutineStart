package com.flaao0.coroutinestart

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.flaao0.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException(
            "123"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonLoad.setOnClickListener {
            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false
            val deferredCity: Deferred<String> = lifecycleScope.async {
               loadCity()
            }

            val deferredTemp: Deferred<Int> = lifecycleScope.async {
                loadTemperature()
            }

            lifecycleScope.launch {
                val city = deferredCity.await()
                val temp = deferredTemp.await()
                binding.tvLocation.text = city
                binding.tvTemperature.text = temp.toString()
                Toast.makeText(
                    this@MainActivity,
                    "City: $city, Temp: $temp",
                    Toast.LENGTH_SHORT
                ).show()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private suspend fun loadData() {
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"

    }

    private suspend fun loadTemperature(): Int {
        delay(5000)
        return 17
    }
}