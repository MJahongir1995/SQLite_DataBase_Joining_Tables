package uz.jahongir.sqlite_database_joining_tables.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.jahongir.sqlite_database_joining_tables.databinding.ItemRvBinding
import uz.jahongir.sqlite_database_joining_tables.models.Customer
import uz.jahongir.sqlite_database_joining_tables.models.Salesman

class SalesmanRvAdapter<T>(var list: List<T>, val callback: RvEvent):RecyclerView.Adapter<SalesmanRvAdapter<T>.VH>() {
    inner class VH(private var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {

        fun onBindSalesman(salesman: Salesman, position: Int) {
            itemRvBinding.tvName.text = salesman.name
            itemRvBinding.tvNumber.text = salesman.number
            itemRvBinding.menuImage.setOnClickListener {
                callback.salesmanMenuCLick(salesman, it as ImageView)
            }
        }

        fun onBindCustomer(customer: Customer, position: Int) {
            itemRvBinding.tvName.text = customer.name
            itemRvBinding.tvNumber.text = customer.number
            itemRvBinding.tvAddress.visibility = View.VISIBLE
            itemRvBinding.tvAddress.text = customer.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            val salesman: Salesman = list[position] as Salesman
            holder.onBindSalesman(salesman, position)

        } catch (e: Exception) {

        }
        try {
            val customer: Customer = list[position] as Customer
            holder.onBindCustomer(customer, position)

        } catch (e: Exception) {

        }
    }

    override fun getItemCount(): Int = list.size
}