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
import uz.jahongir.sqlite_database_joining_tables.databinding.FragmentSalesmanBinding
import uz.jahongir.sqlite_database_joining_tables.databinding.ItemDialogBinding
import uz.jahongir.sqlite_database_joining_tables.db.MyDbHelper
import uz.jahongir.sqlite_database_joining_tables.models.Customer
import uz.jahongir.sqlite_database_joining_tables.models.Salesman

class SalesmanFragment : Fragment(), RvEvent {
    private lateinit var binding: FragmentSalesmanBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var salesmanRvAdapter: SalesmanRvAdapter<Salesman>
    private lateinit var list: ArrayList<Salesman>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSalesmanBinding.inflate(layoutInflater)
        myDbHelper = MyDbHelper(context)

        list = ArrayList()
        list.addAll(myDbHelper.getSalesman())
        salesmanRvAdapter = SalesmanRvAdapter(list,this)
        binding.rv.adapter = salesmanRvAdapter

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            dialog.setView(itemDialogBinding.root)
            dialog.show()

            itemDialogBinding.btnSave.setOnClickListener {
                val salesman = Salesman(
                    itemDialogBinding.edtName.text.toString(),
                    itemDialogBinding.edtNumber.text.toString()
                )
                myDbHelper.addSalesman(salesman)
                list.add(salesman)
                salesmanRvAdapter.notifyItemInserted(list.size - 1)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
        }

        return binding.root
    }

    override fun salesmanMenuCLick(salesman: Salesman, view: ImageView) {
        val popupMenu = PopupMenu(binding.root.context, view)
        popupMenu.inflate(R.menu.s_pop_up_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    myDbHelper.deleteSalesman(salesman)
                    salesmanRvAdapter.list = myDbHelper.getSalesman()
                    salesmanRvAdapter.notifyDataSetChanged()
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                }
                R.id.edit -> {
                    val dialog = AlertDialog.Builder(binding.root.context).create()
                    val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                    dialog.setView(itemDialogBinding.root)
                    dialog.show()

                    itemDialogBinding.edtName.setText(salesman.name)
                    itemDialogBinding.edtNumber.setText(salesman.number)

                    itemDialogBinding.btnSave.setOnClickListener {
                        salesman.name = itemDialogBinding.edtName.text.toString().trim()
                        salesman.number = itemDialogBinding.edtNumber.text.toString().trim()

                        myDbHelper.editSalesman(salesman)
                        salesmanRvAdapter.list = myDbHelper.getSalesman()
                        salesmanRvAdapter.notifyDataSetChanged()
                        dialog.dismiss()
                        Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun customerMenuCLick(customer: Customer, view: ImageView) {

    }
}