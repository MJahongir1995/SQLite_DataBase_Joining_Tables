package uz.jahongir.sqlite_database_joining_tables.db

import uz.jahongir.sqlite_database_joining_tables.models.Booking
import uz.jahongir.sqlite_database_joining_tables.models.Customer
import uz.jahongir.sqlite_database_joining_tables.models.Salesman

interface MyDbInterface {

    fun addSalesman(salesman: Salesman)
    fun addCustomer(customer: Customer)
    fun addBooking(booking: Booking)

    fun getSalesman():List<Salesman>
    fun getCustomer():List<Customer>
    fun getBooking():List<Booking>

    fun longClick(salesman: Salesman)
    fun longClick(customer: Customer)

    fun getSalesmanById(id:Int):Salesman
    fun getCustomerById(id:Int):Customer

    fun deleteSalesman(salesman: Salesman)
    fun editSalesman(salesman: Salesman)

    fun editCustomer(customer: Customer)
    fun deleteCustomer(customer: Customer)
}