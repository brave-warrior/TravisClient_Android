package com.khmelenko.lab.varis.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khmelenko.lab.varis.R
import kotlinx.android.synthetic.main.fragment_security_code.auth_confirm_btn
import kotlinx.android.synthetic.main.fragment_security_code.auth_security_code

/**
 * Security code fragment
 *
 * @author Dmytro Khmelenko
 */
class SecurityCodeFragment : Fragment() {

    private var listener: OnSecurityCodeAction? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_security_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth_confirm_btn.setOnClickListener {
            startConfirmation()
        }
    }

    private fun startConfirmation() {
        if (TextUtils.isEmpty(auth_security_code.text)) {
            auth_security_code.error = getString(R.string.auth_invalid_security_code)
        } else {
            listener?.onSecurityCodeInput(auth_security_code.text.toString())
        }
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)
        try {
            listener = activity as? OnSecurityCodeAction
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnFragmentInteractionListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Listener on security code
     */
    interface OnSecurityCodeAction {

        /**
         * Calles on inputted security code
         *
         * @param securityCode Security code
         */
        fun onSecurityCodeInput(securityCode: String)
    }

    companion object {

        /**
         * Creates new instance of the fragment
         *
         * @return Fragment instance
         */
        fun newInstance(): SecurityCodeFragment {
            return SecurityCodeFragment()
        }
    }

}
