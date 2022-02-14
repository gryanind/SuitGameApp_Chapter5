package com.example.suitgameapp.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.suitgameapp.R
import com.example.suitgameapp.databinding.ActivityMenuGameBinding
import com.example.suitgameapp.preference.UserPreference
import com.example.suitgameapp.ui.game.GameActivity

class MenuGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setNameOnMenu()
        setMenuClickListeners()
    }

    private fun setMenuClickListeners() {
        binding.ivMenuVsPlayer.setOnClickListener {
            GameActivity.startActivity(this, GameActivity.GAMEPLAY_MODE_VD_PLAYER)
        }
        binding.ivMenuVsComputer.setOnClickListener {
            GameActivity.startActivity(this, GameActivity.GAMEPLAY_MODE_VS_COMPUTER)
        }
    }

    private fun setNameOnMenu(){
        binding.tvMenuGame.text = getString(R.string.placeholder_name_menu_game,
            UserPreference(this).name)
    }
}