package com.example.suitgameapp.usecase

import android.util.Log
import com.example.suitgameapp.enum.SuitCharacter
import com.example.suitgameapp.enum.SuitResult

class SuitUseCase: SuitUseCaseInterface {

    companion object {
        const val TAG = "SUIT CASE"

    }

    override fun getWinner(player: SuitCharacter, computer: SuitCharacter): SuitResult {
        return when {
            player == SuitCharacter.PAPER && computer == SuitCharacter.ROCK -> SuitResult.PLAYER
            player == SuitCharacter.ROCK && computer == SuitCharacter.SCISSOR -> SuitResult.PLAYER
            player == SuitCharacter.SCISSOR && computer == SuitCharacter.PAPER -> SuitResult.PLAYER
            player == SuitCharacter.PAPER && computer == SuitCharacter.SCISSOR -> SuitResult.ENEMY
            player == SuitCharacter.ROCK && computer == SuitCharacter.PAPER -> SuitResult.ENEMY
            player == SuitCharacter.SCISSOR && computer == SuitCharacter.ROCK -> SuitResult.ENEMY
            else -> {
                SuitResult.DRAW
            }
        }
    }

    override fun randomPickComputer(): SuitCharacter {
        val pickComputer = SuitCharacter.values().random()
        Log.d(TAG, "Result Pick $pickComputer")
        return when (pickComputer) {
            SuitCharacter.ROCK -> {
                SuitCharacter.ROCK
            }
            SuitCharacter.PAPER -> {
                SuitCharacter.PAPER
            }
            else -> {
                SuitCharacter.SCISSOR
            }
        }
    }
}