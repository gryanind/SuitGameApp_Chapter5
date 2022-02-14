package com.example.suitgameapp.usecase

import com.example.suitgameapp.enum.SuitCharacter
import com.example.suitgameapp.enum.SuitResult

interface SuitUseCaseInterface {
    fun getWinner(player: SuitCharacter, computer: SuitCharacter): SuitResult
    fun randomPickComputer(): SuitCharacter
}