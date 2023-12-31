package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class CalculateExchangeFragment : Fragment() {

    private var calculateExchangeLabelFromTextView: TextView? = null
    private var calculateExchangeFromSpinner: Spinner? = null
    private var calculateExchangeLabelToTextView: TextView? = null
    private var calculateExchangeToSpinner: Spinner? = null
    private var calculateExchangeDisplayTextView: TextView? = null
    private var calculateExchangeAmountEditText: EditText? = null
    private var calculateExchangeButton: Button? = null
    private val listCountries = listOf("EUR", "GBP", "USD")
    private val mapCurrency = mapOf("EUR" to "€", "GBP" to "£", "USD" to "$")

    private var callback: CalculateExchangeFragment.PassingInfoFromMainToCalculateEx? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as CalculateExchangeFragment.PassingInfoFromMainToCalculateEx
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
        return inflater.inflate(R.layout.fragment_calculate_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculateExchangeLabelFromTextView =
            view.findViewById(R.id.calculateExchangeLabelFromTextView)
        calculateExchangeFromSpinner = view.findViewById(R.id.calculateExchangeFromSpinner)
        calculateExchangeLabelToTextView = view.findViewById(R.id.calculateExchangeLabelToTextView)
        calculateExchangeToSpinner = view.findViewById(R.id.calculateExchangeToSpinner)
        calculateExchangeDisplayTextView = view.findViewById(R.id.calculateExchangeDisplayTextView)
        calculateExchangeAmountEditText = view.findViewById(R.id.calculateExchangeAmountEditText)
        calculateExchangeButton = view.findViewById(R.id.calculateExchangeButton)
        ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            listCountries
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                calculateExchangeFromSpinner!!.adapter = adapter
                calculateExchangeToSpinner!!.adapter = adapter
            }

        calculateExchangeButton!!.setOnClickListener {
            if(calculateExchangeAmountEditText!!.text.isEmpty() ||
                (calculateExchangeAmountEditText!!.text.isNotEmpty() && calculateExchangeAmountEditText!!.text.toString().toDouble()<=0.0)){
                Toast.makeText(context,"Enter amount",Toast.LENGTH_SHORT).show()
            }else{
                val mapExchange = callback?.passingInfoFromMainToCalculateEx()?:ViewBalance.defaultMap
                val amount = calculateExchangeAmountEditText!!.text.toString().toDouble()

                val result =  mapExchange.get(calculateExchangeFromSpinner!!.selectedItem)
                    ?.get(calculateExchangeToSpinner!!.selectedItem)!! * amount
                calculateExchangeDisplayTextView!!.text =
                    String.format("${mapCurrency.get(calculateExchangeFromSpinner!!.selectedItem)}%.2f" +
                            " = ${mapCurrency.get(calculateExchangeToSpinner!!.selectedItem)}%.2f",amount,result)



            }
        }

        calculateExchangeFromSpinner!!.setSelection(1)

        val calculateExchangeToSpinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // We retrieve the selected item from the Spinner via its position in list
                val selectedItem = parent.getItemAtPosition(pos)
                if (selectedItem == calculateExchangeFromSpinner!!.selectedItem) {
                    Toast.makeText(
                        context,
                        "Cannot convert to same currency",
                        Toast.LENGTH_SHORT
                    ).show()
                    calculateExchangeFromSpinner!!.setSelection(if(pos==listCountries.size-1) 0 else pos+1)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        calculateExchangeToSpinner!!.onItemSelectedListener = calculateExchangeToSpinnerListener

        val calculateExchangeFromSpinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // We retrieve the selected item from the Spinner via its position in list
                val selectedItem = parent.getItemAtPosition(pos)
                if (selectedItem == calculateExchangeToSpinner!!.selectedItem) {
                    Toast.makeText(
                        context,
                        "Cannot convert to same currency",
                        Toast.LENGTH_SHORT
                    ).show()
                    calculateExchangeToSpinner!!.setSelection(if(pos==listCountries.size-1) 0 else pos+1)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        calculateExchangeFromSpinner!!.onItemSelectedListener = calculateExchangeFromSpinnerListener
    }

    override fun onDestroy() {
        super.onDestroy()
        calculateExchangeLabelFromTextView = null
        calculateExchangeFromSpinner = null
        calculateExchangeLabelToTextView = null
        calculateExchangeToSpinner = null
        calculateExchangeDisplayTextView = null
        calculateExchangeAmountEditText = null
        calculateExchangeButton = null
    }

    interface PassingInfoFromMainToCalculateEx{
        fun passingInfoFromMainToCalculateEx() : Map<String,Map<String,Double>>?
    }
}