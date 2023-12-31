package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class UserMenuFragment : Fragment() {

    private var userTextView: TextView? = null
    private var viewBalanceButton: Button? = null
    private var transferFundsButton: Button? = null
    private var exchangeCalculatorButton: Button? = null
    private var payBillsButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username")
        userTextView = view.findViewById(R.id.userMenuWelcomeTextView)
        userTextView!!.text = "Welcome $username"

        viewBalanceButton = view.findViewById(R.id.userMenuViewBalanceButton)
        viewBalanceButton!!.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
        }
        transferFundsButton = view.findViewById(R.id.userMenuTransferFundsButton)
        transferFundsButton!!.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment)
        }
        exchangeCalculatorButton = view.findViewById(R.id.userMenuExchangeCalculatorButton)
        exchangeCalculatorButton!!.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
        }
        payBillsButton = view.findViewById(R.id.userMenuPayBillsButton)
        payBillsButton!!.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userTextView = null
        viewBalanceButton = null
        transferFundsButton = null
        exchangeCalculatorButton = null
        payBillsButton = null
    }
}