package com.example.suitgameapp.ui.game

import androidx.lifecycle.ViewModel
import com.example.suitgameapp.enum.SuitCharacter
import com.example.suitgameapp.enum.SuitResult
import com.example.suitgameapp.usecase.SuitUseCase

class GameViewModel: ViewModel() {

    private val suitCase = SuitUseCase()

    fun getWinner(player: SuitCharacter, computer: SuitCharacter): SuitResult {
        return suitCase.getWinner(player, computer)
    }

    fun randomPickComputer(): SuitCharacter {
        return suitCase.randomPickComputer()
    }
}