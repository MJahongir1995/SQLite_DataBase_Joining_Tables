package uz.jahongir.sqlite_database_joining_tables.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.jahongir.sqlite_database_joining_tables.R
import uz.jahongir.sqlite_database_joining_tables.adapters.BookingAdapter
import uz.jahongir.sqlite_database_joining_tables.databinding.FragmentBookingBinding
import uz.jahongir.sqlite_database_joining_tables.databinding.ItemDialogOrdersBinding
import uz.jahongir.sqlite_database_joining_tables.db.MyDbHelper
import uz.jahongir.sqlite_database_joining_tables.models.Booking

class BookingFragment : Fragment() {
    private lateinit var binding: FragmentBookingBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Booking>
    private lateinit var bookingAdapter: BookingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookingBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        list.addAll(myDbHelper.getBooking())
        bookingAdapter = BookingAdapter(list)
        binding.rv.adapter = bookingAdapter

        addOrder()
        return binding.root
    }

    private fun addOrder() {
        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context).create()
            val itemDialogOrdersBinding = ItemDialogOrdersBinding.inflate(layoutInflater)
            dialog.setView(itemDialogOrdersBinding.root)
            dialog.show()

            val listSalesman = myDbHelper.getSalesman()
            val listSalesmanName = ArrayList<String>()
            listSalesman.forEach{
                listSalesmanName.add(it.name)
            }
            val sAdapter = ArrayAdapter<String>(binding.root.context,android.R.layout.simple_list_item_1, listSalesmanName)
            itemDialogOrdersBinding.spSalesman.adapter = sAdapter

            val listCustomer = myDbHelper.getCustomer()
            val listCustomerName = ArrayList<String>()
            listCustomer.forEach{
                listCustomerName.add(it.name)
            }
            val cAdapter = ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listCustomerName)
            itemDialogOrdersBinding.spCustomer.adapter = cAdapter

            itemDialogOrdersBinding.btnSave.setOnClickListener {
                val booking = Booking(
                    itemDialogOrdersBinding.edtName.text.toString(),
                    itemDialogOrdersBinding.edtPrice.text.toString().toInt(),
                    listSalesman[itemDialogOrdersBinding.spSalesman.selectedItemPosition],
                    listCustomer[itemDialogOrdersBinding.spCustomer.selectedItemPosition]
                )
                myDbHelper.addBooking(booking)
                list.add(booking)
                bookingAdapter.notifyItemInserted(list.size-1)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            }
        }
    }
}