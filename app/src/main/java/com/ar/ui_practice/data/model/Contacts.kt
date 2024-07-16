package com.ar.ui_practice.data.model

data class Contacts(val name: String, val number: String){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contacts) return false

        if (name != other.name) return false
        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + number.hashCode()
        return result
    }
}
