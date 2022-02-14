package com.example.suitgameapp.ui.appintro.inputname

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.suitgameapp.R
import com.example.suitgameapp.databinding.FragmentInputNameBinding
import com.example.suitgameapp.preference.UserPreference
import com.example.suitgameapp.ui.menu.MenuGameActivity
import com.google.android.material.snackbar.Snackbar

class InputNameFragment : Fragment() {

    private lateinit var binding: FragmentInputNameBinding
    private val userPreference: UserPreference? by lazy {
        context?.let { UserPreference(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInputNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPrefilledData()
    }

    private fun setPrefilledData(){
        userPreference?.let {
            binding.etInputName.setText(it.name.orEmpty())
        }
    }

    fun navigateToMenuGame(){
        if (isNameFilled()){
            userPreference?.name = binding.etInputName.text.toString().trim()
            val intent = Intent(context, MenuGameActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(context, userPreference?.name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNameFilled(): Boolean {
        val name = binding.etInputName.text.toString().trim()
        var isFormValid = true

        if (name.isEmpty()){
            isFormValid = false
            Snackbar.make(binding.root,getString(R.string.txt_name_filled_empty), Snackbar.LENGTH_SHORT).show()
        }
        return isFormValid
    }
}