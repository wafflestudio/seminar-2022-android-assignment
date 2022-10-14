package com.example.tictactoe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val gameStatus: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.PlayerOTurn)
    val boardStatus: MutableStateFlow<GameBoard> = MutableStateFlow(GameBoard())

    fun clickCell(cellNumber: Int) {
        val curBoard = boardStatus.value.copy()
        if(curBoard.board[cellNumber]==CellStatus.Empty && (gameStatus.value == GameStatus.PlayerOTurn || gameStatus.value == GameStatus.PlayerXTurn)) {
            if(gameStatus.value == GameStatus.PlayerOTurn) {
                curBoard.board[cellNumber] = CellStatus.PlayerO
                if(checkFinish(cellNumber)) {
                    gameStatus.value = GameStatus.PlayerOWin
                } else if(checkDraw()) {
                    gameStatus.value = GameStatus.Draw
                } else {
                    gameStatus.value = GameStatus.PlayerXTurn
                }
            }
            else if(gameStatus.value == GameStatus.PlayerXTurn) {
                curBoard.board[cellNumber] = CellStatus.PlayerX
                if(checkFinish(cellNumber)) {
                    gameStatus.value = GameStatus.PlayerXWin
                } else if(checkDraw()) {
                    gameStatus.value = GameStatus.Draw
                } else {
                    gameStatus.value = GameStatus.PlayerOTurn
                }
            }
            boardStatus.value = curBoard
            Log.d("VIEWMODEL", "board status change")
        }
    }

    fun checkFinish(cellNumber: Int): Boolean {
        val board = boardStatus.value.board
        val cur = board[cellNumber]
        return (cur == board[3*(cellNumber/3)] && cur == board[3*(cellNumber/3) + 1] && cur == board[3*(cellNumber/3) + 2]) ||
                (cur == board[cellNumber%3] && cur == board[cellNumber%3+3] && cur == board[cellNumber%3+6]) ||
                (cur == board[0] && cur == board[4] && cur == board[8]) || (cur == board[2] && cur == board[4] && cur == board[6])
    }

    fun checkDraw(): Boolean {
        val board = boardStatus.value.board
        for(cell in board) {
            if(cell == CellStatus.Empty)
                return false
        }
        return true
    }

    fun clickRestart() {
        gameStatus.value = GameStatus.PlayerOTurn
        boardStatus.value = GameBoard()
    }
}