package com.example.tictactoe

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var gameStatus = GameStatus.PlayerOTurn
    var gameEnd: Boolean = false
    var gameBoard = mutableMapOf(
            0 to "",
            1 to "",
            2 to "",
            3 to "",
            4 to "",
            5 to "",
            6 to "",
            7 to "",
            8 to ""
        )

    fun clickCell(cellNumber: Int) {
        updateBoard(cellNumber)
        checkWinner()
        updateStatus()
        checkDraw()
    }

    private fun updateBoard(cellNumber: Int) {
        if (gameStatus == GameStatus.PlayerOTurn) {
            gameBoard[cellNumber] = 'O'.toString()
        }
        if (gameStatus == GameStatus.PlayerXTurn) {
            gameBoard[cellNumber] = 'X'.toString()
        }
    }
    private fun updateStatus() {
        if (gameEnd == false){
            if (gameStatus == GameStatus.PlayerOTurn) {
                gameStatus = GameStatus.PlayerXTurn
            }
            else {
                gameStatus = GameStatus.PlayerOTurn
            }
        }
        if (gameEnd == true) {
            if (gameStatus == GameStatus.PlayerOTurn){
                gameStatus = GameStatus.PlayerOWin
            }
            else {
                gameStatus = GameStatus.PlayerXWin
            }
        }
    }

    private fun isWinner(cella:Int,cellb:Int,cellc:Int) {
        if (gameBoard[cella] == gameBoard[cellb] &&  gameBoard[cellb] ==  gameBoard[cellc] && gameBoard[cella] != "") {
            gameEnd = true
        }
    }

    private fun checkWinner() {
        isWinner(0,1,2)
        isWinner(3,4,5)
        isWinner(6,7,8)
        isWinner(0,3,6)
        isWinner(1,4,7)
        isWinner(2,5,8)
        isWinner(0,4,8)
        isWinner(2,4,6)
        }
    
    private fun checkDraw() {
        val value = ""
        val exist = gameBoard.values.any { it == value }
        if (exist == false) {
            gameStatus = GameStatus.Draw
        }
    }

   fun resetGameBoard() {
        gameStatus = GameStatus.PlayerOTurn
        gameEnd = false
        gameBoard = mutableMapOf(
                0 to "",
                1 to "",
                2 to "",
                3 to "",
                4 to "",
                5 to "",
                6 to "",
                7 to "",
                8 to "",
            )

    }
}