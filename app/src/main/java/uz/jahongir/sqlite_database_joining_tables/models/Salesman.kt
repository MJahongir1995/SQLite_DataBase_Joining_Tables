package uz.jahongir.sqlite_database_joining_tables.models

class Salesman {
    var id:Int = 0
    var name:String = ""
    var number:String =""

    constructor(id: Int, name: String, number: String) {
        this.id = id
        this.name = name
        this.number = number
    }

    constructor(name: String, number: String) {
        this.name = name
        this.number = number
    }

    override fun toString(): String {
        return "Salesman(id=$id, name='$name', number='$number')"
    }
}