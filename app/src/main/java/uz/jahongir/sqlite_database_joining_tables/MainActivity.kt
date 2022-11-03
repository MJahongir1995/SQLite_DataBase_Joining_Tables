package uz.jahongir.sqlite_database_joining_tables

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.jahongir.sqlite_database_joining_tables.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}