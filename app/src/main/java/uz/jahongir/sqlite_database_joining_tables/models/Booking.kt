package uz.jahongir.sqlite_database_joining_tables.models

class Booking {
    var id:Int = 0
    var name:String = ""
    var price = 0
    var salesman:Salesman? = null
    var customer:Customer? = null


    constructor(id: Int, name: String, price: Int, salesman: Salesman?, customer: Customer?) {
        this.id = id
        this.name = name
        this.price = price
        this.salesman = salesman
        this.customer = customer
    }

    constructor(name: String, price: Int, salesman: Salesman?, customer: Customer?) {
        this.name = name
        this.price = price
        this.salesman = salesman
        this.customer = customer
    }

    override fun toString(): String {
        return "Booking(id=$id, name='$name', price=$price, salesman=$salesman, customer=$customer)"
    }

}