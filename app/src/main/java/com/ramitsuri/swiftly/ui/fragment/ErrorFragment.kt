package com.ramitsuri.swiftly.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramitsuri.swiftly.R


class ErrorFragment private constructor() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(MESSAGE)?.let { text ->
            view.findViewById<TextView>(R.id.textMessage).text = text
        }
    }

    companion object {
        val TAG: String = ErrorFragment::class.java.simpleName
        const val MESSAGE: String = "message"

        fun newInstance(): ErrorFragment {
            return ErrorFragment()
        }
    }
}