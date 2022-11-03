package uz.jahongir.sqlite_database_joining_tables.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.jahongir.sqlite_database_joining_tables.R
import uz.jahongir.sqlite_database_joining_tables.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.btnSalesman.setOnClickListener {
            findNavController().navigate(R.id.salesmanFragment)
        }

        binding.btnCustomer.setOnClickListener {
            findNavController().navigate(R.id.customerFragment)
        }

        binding.btnBooking.setOnClickListener {
            findNavController().navigate(R.id.bookingFragment)
        }

        return binding.root
    }

}