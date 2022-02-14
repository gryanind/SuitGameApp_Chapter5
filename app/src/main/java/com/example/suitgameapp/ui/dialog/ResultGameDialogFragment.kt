package com.example.suitgameapp.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.example.suitgameapp.R
import com.example.suitgameapp.databinding.FragmentInputNameBinding
import com.example.suitgameapp.databinding.FragmentResultGameDialogBinding

class ResultGameDialogFragment(
    private var setResultWinner: String,
    private var onButtonClick: (ResultGameDialogFragment) -> Unit

): DialogFragment() {

    private lateinit var binding: FragmentResultGameDialogBinding

    override fun onResume() {
        super.onResume()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // request a window without the title
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultGameDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvWinner.text = setResultWinner
            btnPlayAgain.setOnClickListener {
                onButtonClick(this@ResultGameDialogFragment)
            }
            btnMenu.setOnClickListener {
                onButtonClick(this@ResultGameDialogFragment)
            }
        }
    }


}