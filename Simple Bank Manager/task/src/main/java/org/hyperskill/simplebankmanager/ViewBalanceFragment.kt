package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ViewBalanceFragment : Fragment() {

    private var viewBalanceLabelTextView: TextView? = null
    private var viewBalanceAmountTextView: TextView? = null

    private var callback: ViewBalanceFragment.PassingInfoMainFromViewBalance? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as ViewBalanceFragment.PassingInfoMainFromViewBalance
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
        return inflater.inflate(R.layout.fragment_view_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBalanceLabelTextView = view.findViewById(R.id.viewBalanceLabelTextView)
        viewBalanceAmountTextView = view.findViewById(R.id.viewBalanceAmountTextView)
        viewBalanceAmountTextView?.text = String.format("$%.2f",callback?.passingInfoFromViewBalance())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBalanceLabelTextView = null
        viewBalanceAmountTextView = null
    }

    interface PassingInfoMainFromViewBalance {
        fun passingInfoFromViewBalance() : Double
    }
}