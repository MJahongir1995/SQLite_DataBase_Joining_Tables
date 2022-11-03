package uz.jahongir.sqlite_database_joining_tables.adapters

import android.widget.ImageView
import uz.jahongir.sqlite_database_joining_tables.models.Customer
import uz.jahongir.sqlite_database_joining_tables.models.Salesman

interface RvEvent {
    fun salesmanMenuCLick(salesman: Salesman, view:ImageView)
    fun customerMenuCLick(customer: Customer, view:ImageView)
}