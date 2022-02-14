package com.example.suitgameapp.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.suitgameapp.databinding.FragmentResultGameDialogBinding


object DialogUtils {
    fun showWinnerDialog(
        context: Context,
        winner: String,
        onButtonClick: (AlertDialog?, String) -> Unit
    ) {
        var dialog: AlertDialog? = null

        val dialogBinding =
            FragmentResultGameDialogBinding.inflate((context as AppCompatActivity).layoutInflater).apply {
                tvWinner.text = winner
                btnPlayAgain.setOnClickListener {
                    onButtonClick(dialog,"play_again")
                }
                btnMenu.setOnClickListener {
                    onButtonClick(dialog,"menu")
                }
            }

        dialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

    }
}