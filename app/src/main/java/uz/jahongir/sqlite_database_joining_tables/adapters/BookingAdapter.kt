package uz.jahongir.sqlite_database_joining_tables.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.jahongir.sqlite_database_joining_tables.databinding.ItemRvBinding
import uz.jahongir.sqlite_database_joining_tables.models.Booking

class BookingAdapter(private val list: List<Booking>) : RecyclerView.Adapter<BookingAdapter.VH>() {

    inner class VH(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(booking: Booking, position: Int) {
            itemRvBinding.tvName.text = booking.name
            itemRvBinding.tvNumber.text = booking.price.toString()
            itemRvBinding.tvAddress.visibility = View.VISIBLE
            itemRvBinding.tvAddress.text = booking.salesman?.name
            itemRvBinding.tvCustomer.visibility = View.VISIBLE
            itemRvBinding.tvCustomer.text = booking.customer?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}