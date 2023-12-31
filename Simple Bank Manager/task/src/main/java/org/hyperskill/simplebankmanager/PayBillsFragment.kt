package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PayBillsFragment : Fragment() {

    fun alertPay(title : String, message : String, error : Boolean,
                 amount : Double? = 0.0, billName : String? = ""){

        val dialogPositive = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Confirm") { _, _ ->

                if(callback?.confirmFundsFromMainToPayBills(amount!!)==true){
                    Toast.makeText(context, "Payment for bill $billName, was successful",
                        Toast.LENGTH_SHORT).show()
                }else{
                    alertPay("Error", "Not enough funds",true)
                }
            }
            .setCancelable(false)
            .setNegativeButton(android.R.string.cancel, null)
            .create()

        val dialogNegative = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
            }
            .setCancelable(false)
            .create()

        if(error){
            dialogPositive.dismiss()
            dialogNegative.show()
        }else{
            dialogNegative.dismiss()
            dialogPositive.show()
        }

    }

    fun formatMessage(billInfo : Map<String, Triple<String, String, Double>>, code : String) : String{
        return String.format("Name: %s\nBillCode: %s\nAmount: \$%.2f", billInfo.get(code)?.first,
            billInfo.get(code)?.second, billInfo.get(code)?.third)
    }

    private var payBillsCodeInputEditText : EditText? = null
    private var payBillsShowBillInfoButton : Button? = null

    private var callback: PayBillsFragment.PassingInfoFromMainToPayBills? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as PayBillsFragment.PassingInfoFromMainToPayBills
    }
    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        payBillsCodeInputEditText = view.findViewById(R.id.payBillsCodeInputEditText)
        payBillsShowBillInfoButton = view.findViewById(R.id.payBillsShowBillInfoButton)
        payBillsShowBillInfoButton!!.setOnClickListener {

            val mapRetriever = callback?.passingInfoFromMainToPayBills()?:ViewBalance.defaultBillInfoMap

            if(mapRetriever.containsKey(payBillsCodeInputEditText!!.text.toString())){
                alertPay("Bill info", formatMessage(mapRetriever,
                    payBillsCodeInputEditText!!.text.toString()),false,
                    mapRetriever.get(payBillsCodeInputEditText!!.text.toString())?.third,
                    mapRetriever.get(payBillsCodeInputEditText!!.text.toString())?.first)
            }else{
                alertPay("Error", "Wrong code",true)
            }



        }
    }

    override fun onDestroy() {
        super.onDestroy()
        payBillsCodeInputEditText = null
        payBillsShowBillInfoButton = null
    }

    interface PassingInfoFromMainToPayBills{
        fun passingInfoFromMainToPayBills() : Map<String, Triple<String, String, Double>>?
        fun confirmFundsFromMainToPayBills(amount : Double) : Boolean
    }


}