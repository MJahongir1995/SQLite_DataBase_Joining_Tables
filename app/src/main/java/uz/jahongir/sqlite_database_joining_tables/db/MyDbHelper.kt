package uz.jahongir.sqlite_database_joining_tables.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.lang.UProperty.NAME
import android.os.Build.ID
import uz.jahongir.sqlite_database_joining_tables.models.Booking
import uz.jahongir.sqlite_database_joining_tables.models.Customer
import uz.jahongir.sqlite_database_joining_tables.models.Salesman
import javax.xml.xpath.XPathConstants.NUMBER

class MyDbHelper(context: Context?):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), MyDbInterface{

    companion object{
        const val DB_NAME = "market_db"
        const val DB_VERSION = 1

        const val SALESMAN_TABLE = "salesman_table"
        const val SALESMAN_ID = "id"
        const val SALESMAN_NAME = "name"
        const val SALESMAN_NUMBER = "number"

        const val CUSTOMER_TABLE = "customer_table"
        const val CUSTOMER_ID = "id"
        const val CUSTOMER_NAME = "name"
        const val CUSTOMER_NUMBER = "number"
        const val CUSTOMER_ADDRESS = "address"

        const val BOOKING_TABLE = "booking"
        const val BOOKING_ID ="id"
        const val BOOKING_NAME ="name"
        const val BOOKING_PRICE ="price"
        const val BOOKING_SALESMAN_ID = "salesman_id"
        const val BOOKING_CUSTOMER_ID = "customer_id"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val querySalesman = "create table $SALESMAN_TABLE($SALESMAN_ID integer not null primary key autoincrement unique, $SALESMAN_NAME text not null, $SALESMAN_NUMBER text not null)"
        val queryCustomer = "create table $CUSTOMER_TABLE($CUSTOMER_ID integer not null primary key autoincrement unique, $CUSTOMER_NAME text not null, $CUSTOMER_NUMBER text not null, $CUSTOMER_ADDRESS text not null)"
        val queryBooking = "create table $BOOKING_TABLE($BOOKING_ID integer not null primary key autoincrement unique, $BOOKING_NAME text not null, $BOOKING_PRICE integer not null, $BOOKING_SALESMAN_ID integer not null, $BOOKING_CUSTOMER_ID integer not null, foreign key($BOOKING_SALESMAN_ID) references $SALESMAN_TABLE($SALESMAN_ID), foreign key ($BOOKING_CUSTOMER_ID) references $CUSTOMER_TABLE($CUSTOMER_ID))"
        db?.execSQL(querySalesman)
        db?.execSQL(queryCustomer)
        db?.execSQL(queryBooking)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addSalesman(salesman: Salesman) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SALESMAN_NAME, salesman.name)
        contentValues.put(SALESMAN_NUMBER, salesman.number)
        database.insert(SALESMAN_TABLE, null,contentValues)
        database.close()
    }

    override fun addCustomer(customer: Customer) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CUSTOMER_NAME, customer.name)
        contentValues.put(CUSTOMER_NUMBER, customer.number)
        contentValues.put(CUSTOMER_ADDRESS, customer.address)
        database.insert(CUSTOMER_TABLE, null, contentValues)
        database.close()
    }

    override fun addBooking(booking: Booking) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(BOOKING_NAME, booking.name)
        contentValues.put(BOOKING_PRICE, booking.price)
        contentValues.put(BOOKING_SALESMAN_ID, booking.salesman?.id)
        contentValues.put(BOOKING_CUSTOMER_ID, booking.customer?.id)
        database.insert(BOOKING_TABLE, null, contentValues)
        database.close()
    }

    override fun getSalesman(): List<Salesman> {
        val list = ArrayList<Salesman>()
        val query = "select * from $SALESMAN_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                list.add(
                    Salesman(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getCustomer(): List<Customer> {
        val list = ArrayList<Customer>()
        val query = "select * from $CUSTOMER_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query,null)

        if (cursor.moveToFirst()){
            do {
                list.add(
                    Customer(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getBooking(): List<Booking> {
        val list = ArrayList<Booking>()

        val database = readableDatabase
        val query = "select * from $BOOKING_TABLE"
        val cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    Booking(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        getSalesmanById(cursor.getInt(3)),
                        getCustomerById(cursor.getInt(4))
                ))
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun longClick(salesman: Salesman) {

    }

    override fun longClick(customer: Customer) {

    }

    override fun getSalesmanById(id: Int): Salesman {
        val database = readableDatabase
        val cursor = database.query(
            SALESMAN_TABLE,
            arrayOf(
                SALESMAN_ID,
                SALESMAN_NAME,
                SALESMAN_NUMBER
            ),
            "$SALESMAN_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        return Salesman(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
    }

    override fun getCustomerById(id: Int): Customer {
        val database = readableDatabase
        val cursor = database.query(
            CUSTOMER_TABLE,
            arrayOf(
                CUSTOMER_ID,
                CUSTOMER_NAME,
                CUSTOMER_NUMBER,
                CUSTOMER_ADDRESS
            ),
                "$CUSTOMER_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        return Customer(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3))
    }

    override fun deleteSalesman(salesman: Salesman) {
        val database = this.writableDatabase
        database.delete(SALESMAN_TABLE, "$ID =?", arrayOf(salesman.id.toString()))
        database.close()
    }

    override fun editSalesman(salesman: Salesman) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SALESMAN_ID, salesman.id)
        contentValues.put(SALESMAN_NAME, salesman.name)
        contentValues.put(SALESMAN_NUMBER, salesman.number)
        database.update(SALESMAN_TABLE, contentValues, "$ID = ?", arrayOf(salesman.id.toString()))
    }

    override fun editCustomer(customer: Customer) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CUSTOMER_ID, customer.id)
        contentValues.put(CUSTOMER_NAME, customer.name)
        contentValues.put(CUSTOMER_NUMBER, customer.number)
        contentValues.put(CUSTOMER_ADDRESS, customer.address)
        database.update(SALESMAN_TABLE, contentValues, "$ID = ?", arrayOf(customer.id.toString()))
    }

    override fun deleteCustomer(customer: Customer) {
        val database = this.writableDatabase
        database.delete(CUSTOMER_TABLE, "$ID =?", arrayOf(customer.id.toString()))
        database.close()
    }
}