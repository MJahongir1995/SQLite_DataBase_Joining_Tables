package uz.jahongir.sqlite_database_joining_tables.models

class Customer {
    var id:Int = 0
    var name:String = ""
    var number:String = ""
    var address:String = ""

    constructor(id: Int, name: String, number: String, address: String) {
        this.id = id
        this.name = name
        this.number = number
        this.address = address
    }

    constructor(name: String, number: String, address: String) {
        this.name = name
        this.number = number
        this.address = address
    }

    override fun toString(): String {
        return "Customer(id=$id, name='$name', number='$number', address='$address')"
    }
}