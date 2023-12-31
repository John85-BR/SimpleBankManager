package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private var loginButton: Button? = null
    private var loginName: EditText? = null
    private var loginPassword: EditText? = null

    private var callback: PassingInfoMainFromLogin? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as PassingInfoMainFromLogin
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginName = view.findViewById(R.id.loginUsername)
        loginPassword = view.findViewById(R.id.loginPassword)
        loginButton = view.findViewById(R.id.loginButton)
        loginButton!!.setOnClickListener {

            if(callback?.passingInfoFromLoginFragment(loginName!!.text.toString(), loginPassword!!.text.toString())==true){
                val bundle = bundleOf("username" to loginName!!.text.toString())
                findNavController().navigate(R.id.action_loginFragment_to_userMenuFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginName = null
        loginPassword = null
        loginButton = null
    }

    interface PassingInfoMainFromLogin {
        fun passingInfoFromLoginFragment(user: String, pass : String) : Boolean
    }
}