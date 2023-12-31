package org.hyperskill.simplebankmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), LoginFragment.PassingInfoMainFromLogin,
    ViewBalanceFragment.PassingInfoMainFromViewBalance, TransferFundsFragment.PassingInfoMainFromTransfer,
CalculateExchangeFragment.PassingInfoFromMainToCalculateEx, PayBillsFragment.PassingInfoFromMainToPayBills{

        private var viewBalance: ViewBalance = ViewBalance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBalance.balance = intent.extras?.getDouble("balance")?:viewBalance.balance

    }

    override fun passingInfoFromLoginFragment(user: String, pass : String) : Boolean {

        val username = intent.extras?.getString("username")
        val password = intent.extras?.getString("password")
        val text = if (username==null && password==null){
            if(user == "Lara" && pass == "1234")"logged in" else "invalid credentials"
        } else{
            if(user == username && pass == password)"logged in" else "invalid credentials"
        }
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()

        return text =="logged in"
    }
    override fun passingInfoFromViewBalance(): Double = intent.extras?.getDouble("balance")?:viewBalance.balance
    override fun passingInfoFromMainToTransfer(amount: Double): Boolean = viewBalance.transferFunds(amount)
    override fun passingInfoFromMainToCalculateEx(): Map<String,Map<String,Double>>?
        = intent.extras?.getSerializable("exchangeMap") as Map<String,Map<String,Double>>?

    override fun passingInfoFromMainToPayBills(): Map<String, Triple<String, String, Double>>?
        = intent.extras?.getSerializable("billInfo") as Map<String, Triple<String, String, Double>>?

    override fun confirmFundsFromMainToPayBills(amount: Double): Boolean = viewBalance.transferFunds(amount)
}
