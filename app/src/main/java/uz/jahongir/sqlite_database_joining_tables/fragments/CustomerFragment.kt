package uz.jahongir.sqlite_database_joining_tables.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.jahongir.sqlite_database_joining_tables.R
import uz.jahongir.sqlite_database_joining_tables.adapters.RvEvent
import uz.jahongir.sqlite_database_joining_tables.adapters.SalesmanRvAdapter
import uz.jahongir.sqlite_database_joining_tables.databinding.FragmentCustomerBinding
import uz.jahongir.sqlite_database_joining_tables.databinding.ItemDialogBinding
import uz.jahongir.sqlite_database_joining_tables.db.MyDbHelper
import uz.jahongir.sqlite_database_joining_tables.models.Customer
import uz.jahongir.sqlite_database_joining_tables.models.Salesman

class CustomerFragment : Fragment(), RvEvent {
    private lateinit var binding: FragmentCustomerBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var list: ArrayList<Customer>
    private lateinit var salesmanRvAdapter: SalesmanRvAdapter<Customer>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerBinding.inflate(layoutInflater)
        myDbHelper = MyDbHelper(context)
        list = ArrayList()
        list.addAll(myDbHelper.getCustomer())
        salesmanRvAdapter = SalesmanRvAdapter(list)
        binding.rv.adapter = SalesmanRvAdapter(list)


        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            itemDialogBinding.edtAddress.visibility = View.VISIBLE
            dialog.setView(itemDialogBinding.root)
            dialog.show()

            itemDialogBinding.btnSave.setOnClickListener {
                val customer = Customer(
                    itemDialogBinding.edtName.text.toString(),
                    itemDialogBinding.edtNumber.text.toString(),
                    itemDialogBinding.edtAddress.text.toString()
                )
                myDbHelper.addCustomer(customer)
                list.add(customer)
                salesmanRvAdapter.notifyItemInserted(list.size - 1)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
        }
        return binding.root
    }

    override fun salesmanMenuCLick(salesman: Salesman, view: ImageView) {
    }

    override fun customerMenuCLick(customer: Customer, view: ImageView) {
        val popupMenu = PopupMenu(binding.root.context, view)
        popupMenu.inflate(R.menu.s_pop_up_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    myDbHelper.deleteCustomer(customer)
                    salesmanRvAdapter.list = myDbHelper.getCustomer()
                    salesmanRvAdapter.notifyItemInserted(list.size - 1)
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                }
                R.id.edit -> {
                    val dialog = AlertDialog.Builder(binding.root.context).create()
                    val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                    dialog.setView(itemDialogBinding.root)
                    dialog.show()

                    itemDialogBinding.edtName.setText(customer.name)
                    itemDialogBinding.edtNumber.setText(customer.number)

                    itemDialogBinding.btnSave.setOnClickListener {
                        customer.name = itemDialogBinding.edtName.text.toString().trim()
                        customer.number = itemDialogBinding.edtNumber.text.toString().trim()

                        myDbHelper.editCustomer(customer)
                        salesmanRvAdapter.list = myDbHelper.getCustomer()
                        salesmanRvAdapter.notifyItemInserted(list.size - 1)
                        dialog.dismiss()
                        Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }

        popupMenu.show()
    }
}