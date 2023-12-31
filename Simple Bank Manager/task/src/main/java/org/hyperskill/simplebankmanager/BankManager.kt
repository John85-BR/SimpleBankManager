package org.hyperskill.simplebankmanager

interface BankManager {

    var balance : Double

    fun transferFunds(amount : Double) : Boolean

}