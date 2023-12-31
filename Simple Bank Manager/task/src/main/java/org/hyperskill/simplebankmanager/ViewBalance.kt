package org.hyperskill.simplebankmanager

class ViewBalance() : BankManager {

    companion object{
        val defaultBalance = 100.0
        val amountErrorMessage = "Invalid amount"
        val accountErrorMessage = "Invalid account number"

        val defaultMap = mapOf(
            "EUR" to mapOf(
                "GBP" to 0.5,
                "USD" to 2.0
            ),
            "GBP" to mapOf(
                "EUR" to 2.0,
                "USD" to 4.0
            ),
            "USD" to mapOf(
                "EUR" to 0.5,
                "GBP" to 0.25
            )
        )

        val defaultBillInfoMap = mapOf(
            "ELEC" to Triple("Electricity", "ELEC", 45.0),
            "GAS" to Triple("Gas", "GAS", 20.0),
            "WTR" to Triple("Water", "WTR", 25.5)
        )
    }

    override var balance : Double = defaultBalance
    override fun transferFunds(amount : Double) : Boolean {

        return if(amount<=balance){
            balance -= amount
            true
        } else{
            false
        }
    }
}