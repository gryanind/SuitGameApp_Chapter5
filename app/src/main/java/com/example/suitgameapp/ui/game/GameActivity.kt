package com.example.suitgameapp.ui.game

import android.content.Context
import android.content.Intent
import android.graphics.Color.blue
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.suitgameapp.R
import com.example.suitgameapp.databinding.ActivityGameBinding
import com.example.suitgameapp.enum.PlayerSuit
import com.example.suitgameapp.enum.SuitCharacter
import com.example.suitgameapp.enum.SuitResult
import com.example.suitgameapp.preference.UserPreference
import com.example.suitgameapp.ui.dialog.DialogUtils
import com.example.suitgameapp.ui.dialog.ResultGameDialogFragment
import com.example.suitgameapp.ui.menu.MenuGameActivity
import com.example.suitgameapp.usecase.SuitUseCase

class GameActivity : AppCompatActivity() {
    private val TAG = GameActivity::class.simpleName

    private lateinit var binding: ActivityGameBinding
    private lateinit var suitUseCase: SuitUseCase
    private lateinit var player1: SuitCharacter
    private lateinit var player2: SuitCharacter
    private lateinit var computer: SuitCharacter
    private lateinit var setResultWinner: String
    private lateinit var viewModel: GameViewModel

    private var playerName: String? = null
    private var gameplayMode: Int = GAMEPLAY_MODE_VS_COMPUTER
    private var playTurn: PlayerSuit = PlayerSuit.PLAYER1
    private val userPreference: UserPreference? by lazy {
        applicationContext?.let { UserPreference(it) }
    }

    companion object {
        private const val EXTRAS_GAME_MODE = "EXRAS_GAME_MODE"
        const val GAMEPLAY_MODE_VS_COMPUTER = 0
        const val GAMEPLAY_MODE_VD_PLAYER = 1

        @JvmStatic
        fun startActivity(context: Context, gameplayMode: Int) {
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(EXTRAS_GAME_MODE, gameplayMode)
            context.startActivity(intent)
        }
    }

    private fun getIntentData(){
        gameplayMode = intent.extras?.getInt(EXTRAS_GAME_MODE, 0) ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        bindViews()
        getIntentData()
        setClickListeners()
        viewModel = GameViewModel()
    }

    private fun bindViews() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setClickListeners() {
        if (gameplayMode == GAMEPLAY_MODE_VD_PLAYER) {
            Toast.makeText(applicationContext, "Player 1 Turn", Toast.LENGTH_SHORT).show()
            showSuitCharacter(PlayerSuit.PLAYER1, true)
            showSuitCharacter(PlayerSuit.PLAYER2, false)
            binding.btnSelectP1.isVisible = true
            binding.btnSelectP2.isVisible = false

            binding.flChoice1.setOnClickListener {
                binding.flChoice1.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                binding.flChoice2.setBackgroundColor(0)
                binding.flChoice3.setBackgroundColor(0)
                Toast.makeText(applicationContext,"Player 1 Choose Paper", Toast.LENGTH_SHORT).show()
                player1 = SuitCharacter.PAPER
            }
            binding.flChoice2.setOnClickListener {
                binding.flChoice1.setBackgroundColor(0)
                binding.flChoice2.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                binding.flChoice3.setBackgroundColor(0)
                Toast.makeText(applicationContext, "Player 1 Choose Rock", Toast.LENGTH_SHORT).show()
                player1 = SuitCharacter.ROCK
            }
            binding.flChoice3.setOnClickListener {
                binding.flChoice1.setBackgroundColor(0)
                binding.flChoice2.setBackgroundColor(0)
                binding.flChoice3.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                Toast.makeText(applicationContext, "Player 1 Choose Scissors", Toast.LENGTH_SHORT).show()
                player1 = SuitCharacter.SCISSOR
            }
        } else {
            binding.btnSelectP1.isVisible = false
            binding.btnSelectP2.isVisible = false
            binding.flChoice1.setOnClickListener {
                binding.flChoice1.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                binding.flChoice2.setBackgroundColor(0)
                binding.flChoice3.setBackgroundColor(0)
                Toast.makeText(applicationContext, "Player 1 Choose Paper", Toast.LENGTH_SHORT).show()
                gameProcess(SuitCharacter.PAPER, viewModel.randomPickComputer())
            }
            binding.flChoice2.setOnClickListener {
                binding.flChoice1.setBackgroundColor(0)
                binding.flChoice2.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                binding.flChoice3.setBackgroundColor(0)
                Toast.makeText(applicationContext, "Player 1 Choose Rock", Toast.LENGTH_SHORT).show()
                gameProcess(SuitCharacter.ROCK, viewModel.randomPickComputer())
            }
            binding.flChoice3.setOnClickListener {
                binding.flChoice1.setBackgroundColor(0)
                binding.flChoice2.setBackgroundColor(0)
                binding.flChoice3.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                Toast.makeText(applicationContext, "Player 1 Choose Scissors", Toast.LENGTH_SHORT).show()
                gameProcess(SuitCharacter.SCISSOR, viewModel.randomPickComputer())
            }
        }
        binding.btnSelectP1.setOnClickListener {
            if (playTurn == PlayerSuit.PLAYER1){
                Toast.makeText(applicationContext, "Player 2 Turn", Toast.LENGTH_SHORT).show()
                playTurn = PlayerSuit.PLAYER2
                showSuitCharacter(PlayerSuit.PLAYER1, false)
                showSuitCharacter(PlayerSuit.PLAYER2, true)
                binding.btnSelectP1.isVisible = false
                binding.btnSelectP2.isVisible = true

                binding.flChoice4.setOnClickListener {
                    binding.flChoice4.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                    binding.flChoice5.setBackgroundColor(0)
                    binding.flChoice6.setBackgroundColor(0)
                    Toast.makeText(applicationContext, "Player 2 Choose Paper", Toast.LENGTH_SHORT).show()
                    player2 = SuitCharacter.PAPER

                }
                binding.flChoice5.setOnClickListener {
                    binding.flChoice4.setBackgroundColor(0)
                    binding.flChoice5.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                    binding.flChoice6.setBackgroundColor(0)
                    Toast.makeText(applicationContext, "Player 2 Choose Rock", Toast.LENGTH_SHORT).show()
                    player2 = SuitCharacter.ROCK

                }
                binding.flChoice6.setOnClickListener {
                    binding.flChoice4.setBackgroundColor(0)
                    binding.flChoice5.setBackgroundColor(0)
                    binding.flChoice6.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                    Toast.makeText(applicationContext, "Player 2 Choose Scissors", Toast.LENGTH_SHORT).show()
                    player2 = SuitCharacter.SCISSOR

                }
            }
        }

        binding.btnSelectP2.setOnClickListener {
            gameProcess(player1, player2)
            binding.btnSelectP2.isVisible = false
            showDialogFragment(setResultWinner)
        }

        binding.flChoiceRefresh.setOnClickListener {
            resetGame()
        }

        binding.flChoiceHome.setOnClickListener {
            backToMenu()
        }

    }

    private fun gameProcess(player: SuitCharacter, enemy: SuitCharacter){
        if(gameplayMode == GAMEPLAY_MODE_VD_PLAYER) {
            showResultGame(viewModel.getWinner(player, enemy))
        }
        else {
            computerChooseCharacter(enemy)
            showResultGame(viewModel.getWinner(player, enemy))
        }
    }

    private fun showResultGame(result: SuitResult) {
        if (gameplayMode == GAMEPLAY_MODE_VD_PLAYER) {
            when (result) {
                SuitResult.PLAYER -> {
                    Log.d(TAG, "Winner -> Player One")
                    binding.flChoice1.setOnClickListener(null)
                    binding.flChoice2.setOnClickListener(null)
                    binding.flChoice3.setOnClickListener(null)
                    binding.flChoice4.setOnClickListener(null)
                    binding.flChoice5.setOnClickListener(null)
                    binding.flChoice6.setOnClickListener(null)
                    setResultWinner = "${UserPreference(this).name.toString()} Win !"
                    showSuitCharacter(PlayerSuit.PLAYER1, true)
                    showDialogFragment(setResultWinner)
                }
                SuitResult.ENEMY -> {
                    Log.d(TAG, "Winner -> Player Two")
                    binding.flChoice1.setOnClickListener(null)
                    binding.flChoice2.setOnClickListener(null)
                    binding.flChoice3.setOnClickListener(null)
                    binding.flChoice4.setOnClickListener(null)
                    binding.flChoice5.setOnClickListener(null)
                    binding.flChoice6.setOnClickListener(null)
                    setResultWinner = "Player Two Win !"
                    showSuitCharacter(PlayerSuit.PLAYER1, true)
                    showDialogFragment(setResultWinner)
                }
                SuitResult.DRAW -> {
                    Log.d(TAG, "Draw ")
                    binding.flChoice1.setOnClickListener(null)
                    binding.flChoice2.setOnClickListener(null)
                    binding.flChoice3.setOnClickListener(null)
                    binding.flChoice4.setOnClickListener(null)
                    binding.flChoice5.setOnClickListener(null)
                    binding.flChoice6.setOnClickListener(null)
                    setResultWinner = "Draw"
                    showSuitCharacter(PlayerSuit.PLAYER1, true)
                    showDialogFragment(setResultWinner)
                }
            }
        } else {
            when (result) {
                SuitResult.PLAYER -> {
                    Log.d(TAG, "Winner -> Player")
                    binding.flChoice1.setOnClickListener(null)
                    binding.flChoice2.setOnClickListener(null)
                    binding.flChoice3.setOnClickListener(null)
                    binding.flChoice4.setOnClickListener(null)
                    binding.flChoice5.setOnClickListener(null)
                    binding.flChoice6.setOnClickListener(null)
                    setResultWinner = "${UserPreference(this).name.toString()} Win !"
                    showDialogFragment(setResultWinner)
                }
                SuitResult.ENEMY -> {
                    Log.d(TAG, "Winner -> Computer")
                    binding.flChoice1.setOnClickListener(null)
                    binding.flChoice2.setOnClickListener(null)
                    binding.flChoice3.setOnClickListener(null)
                    binding.flChoice4.setOnClickListener(null)
                    binding.flChoice5.setOnClickListener(null)
                    binding.flChoice6.setOnClickListener(null)
                    setResultWinner = "Computer Win !"
                    showDialogFragment(setResultWinner)
                }
                SuitResult.DRAW -> {
                    Log.d(TAG, "Draw")
                    binding.flChoice1.setOnClickListener(null)
                    binding.flChoice2.setOnClickListener(null)
                    binding.flChoice3.setOnClickListener(null)
                    binding.flChoice4.setOnClickListener(null)
                    binding.flChoice5.setOnClickListener(null)
                    binding.flChoice6.setOnClickListener(null)
                    setResultWinner = "Draw"
                    showDialogFragment(setResultWinner)
                }
            }
        }
    }

    private fun computerChooseCharacter(computer: SuitCharacter) {
        when (computer) {
            SuitCharacter.PAPER -> {
                binding.flChoice4.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                binding.flChoice5.setBackgroundColor(0)
                binding.flChoice6.setBackgroundColor(0)
                Toast.makeText(applicationContext, "Player 2 Choose Paper", Toast.LENGTH_SHORT).show()
            }
            SuitCharacter.ROCK -> {
                binding.flChoice4.setBackgroundColor(0)
                binding.flChoice5.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                binding.flChoice6.setBackgroundColor(0)
                Toast.makeText(applicationContext, "Player 2 Choose Rock", Toast.LENGTH_SHORT).show()
            }
            SuitCharacter.SCISSOR -> {
                binding.flChoice4.setBackgroundColor(0)
                binding.flChoice5.setBackgroundColor(0)
                binding.flChoice6.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                Toast.makeText(applicationContext, "Player 2 Choose Scissors", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun backToMenu() {
        val intent = Intent(this@GameActivity, MenuGameActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showDialogFragment(name: String){
        DialogUtils.showWinnerDialog(this, name) { dialog, value ->
            Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
    }

    private fun resetGame() {
        setClickListeners()
        playTurn = PlayerSuit.PLAYER1
        binding.flChoice1.setBackgroundColor(0)
        binding.flChoice2.setBackgroundColor(0)
        binding.flChoice3.setBackgroundColor(0)

    }

    private fun showSuitCharacter(player: PlayerSuit, isVisible: Boolean) {
        if (player == PlayerSuit.PLAYER1){
            binding.ll1.isVisible = isVisible
        }
        else {
            binding.ll2.isVisible = isVisible
        }

    }


}