package com.example.tictactoe

import androidx.lifecycle.ViewModel

class TicViewModel : ViewModel() {

    private var _gameStatus = GameStatus.PlayerOTurn
    val gameStatus: GameStatus
        get() = _gameStatus

    private var _gameBoard = GameBoard(mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9"))
    val gameBoard: GameBoard
        get() = _gameBoard

    private var numTurn: Int = 0
    private var gameEnd: Boolean = false

    fun clickCell(cellNumber: Int) {
        updateBoard(cellNumber)
        isWinner()
        updateStatus()
    }

    private fun updateStatus() {
        if (_gameStatus == GameStatus.PlayerOTurn && !gameEnd) {
            if (numTurn == 9) {
                gameEnd = true
                _gameStatus = GameStatus.Draw
            } else {
                _gameStatus = GameStatus.PlayerXTurn
            }
        } else if (_gameStatus == GameStatus.PlayerXTurn && !gameEnd) {
            if (numTurn == 9) {
                gameEnd = true
                _gameStatus = GameStatus.Draw
            } else {
                _gameStatus = GameStatus.PlayerOTurn
            }
        } else if (_gameStatus == GameStatus.PlayerOTurn && gameEnd) {
            _gameStatus = GameStatus.PlayerOWin
        } else if (_gameStatus == GameStatus.PlayerXTurn && gameEnd) {
            _gameStatus = GameStatus.PlayerXWin
        }
    }

    private fun updateBoard(cellNumber: Int) {
        if (_gameStatus == GameStatus.PlayerOTurn) {
            _gameBoard.cells[cellNumber - 1] = "O"
        }
        if (_gameStatus == GameStatus.PlayerXTurn) {
            _gameBoard.cells[cellNumber - 1] = "X"
        }
        numTurn++
    }

    private fun isWinner() {
        if (_gameBoard.cells.get(0) == _gameBoard.cells.get(1) && _gameBoard.cells.get(1) == _gameBoard.cells.get(
                2
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(3) == _gameBoard?.cells?.get(4) && _gameBoard?.cells?.get(4) == _gameBoard?.cells?.get(
                5
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(6) == _gameBoard?.cells?.get(7) && _gameBoard?.cells?.get(7) == _gameBoard?.cells?.get(
                8
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(0) == _gameBoard?.cells?.get(3) && _gameBoard?.cells?.get(3) == _gameBoard?.cells?.get(
                6
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(1) == _gameBoard?.cells?.get(4) && _gameBoard?.cells?.get(4) == _gameBoard?.cells?.get(
                7
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(2) == _gameBoard?.cells?.get(5) && _gameBoard?.cells?.get(5) == _gameBoard?.cells?.get(
                8
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(0) == _gameBoard?.cells?.get(4) && _gameBoard?.cells?.get(4) == _gameBoard?.cells?.get(
                8
            )
        ) {
            gameEnd = true
        }
        if (_gameBoard?.cells?.get(2) == _gameBoard?.cells?.get(4) && _gameBoard?.cells?.get(4) == _gameBoard?.cells?.get(
                6
            )
        ) {
            gameEnd = true
        }
    }

    fun resetGameBoard() {
        _gameStatus = GameStatus.PlayerOTurn
        _gameBoard = GameBoard(mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9"))
        numTurn = 0
        gameEnd = false
    }

    init {
        resetGameBoard()
    }
}