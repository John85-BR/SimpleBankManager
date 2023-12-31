package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class TransferFundsFragment : Fragment() {

    private var transferButton: Button? = null
    private var accountEditText: EditText? = null
    private var amountEditText: EditText? = null

    private var callback: TransferFundsFragment.PassingInfoMainFromTransfer? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as TransferFundsFragment.PassingInfoMainFromTransfer
    }
    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfer_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountEditText = view.findViewById(R.id.transferFundsAccountEditText)
        amountEditText = view.findViewById(R.id.transferFundsAmountEditText)
        transferButton = view.findViewById(R.id.transferFundsButton)
        transferButton!!.setOnClickListener {

            var amountValid = false
            var accountValid = false
            val regex1 = "\\b(sa)\\d{4}".toRegex()
            val regex2 = "\\b(ca)\\d{4}".toRegex()

            if(regex1.matches(accountEditText!!.text) || regex2.matches(accountEditText!!.text)){
                accountValid = true
            }else{
                accountEditText!!.error = ViewBalance.accountErrorMessage
            }

            if(amountEditText!!.text.isEmpty() ||
                (amountEditText!!.text.isNotEmpty() && amountEditText!!.text.toString().toDouble()<=0.0)){
                amountEditText!!.error = ViewBalance.amountErrorMessage
            }else{
                amountValid = true
            }

            if(accountValid && amountValid){
                if(callback?.passingInfoFromMainToTransfer(amountEditText!!.text.toString().toDouble())==true){
                    Toast.makeText(context,String.format("Transferred $%.2f to account %s",
                        amountEditText!!.text.toString().toDouble(),accountEditText!!.text),Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_transferFundsFragment_to_userMenuFragment)
                }else{
                    Toast.makeText(context,String.format("Not enough funds to transfer $%.2f",
                        amountEditText!!.text.toString().toDouble()),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        transferButton = null
        accountEditText = null
        amountEditText = null
    }

    interface PassingInfoMainFromTransfer {
        fun passingInfoFromMainToTransfer(amount : Double) : Boolean
    }
}