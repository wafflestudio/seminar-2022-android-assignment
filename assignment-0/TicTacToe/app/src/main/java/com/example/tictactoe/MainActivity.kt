package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var gameStatus: GameStatus? = null
    private var boardStatus: GameBoard? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //status init
        gameStatus = GameStatus.PlayerOTurn
        boardStatus = GameBoard(listOf(0,0,0,0,0,0,0,0,0))

        //button bindings
        binding.button0.setOnClickListener{updateGame(0)}
        binding.button1.setOnClickListener{updateGame(1)}
        binding.button2.setOnClickListener{updateGame(2)}
        binding.button3.setOnClickListener{updateGame(3)}
        binding.button4.setOnClickListener{updateGame(4)}
        binding.button5.setOnClickListener{updateGame(5)}
        binding.button6.setOnClickListener{updateGame(6)}
        binding.button7.setOnClickListener{updateGame(7)}
        binding.button8.setOnClickListener{updateGame(8)}
        binding.restart.setOnClickListener{restartGame()}

        //display init
        setDisplay()
    }

    private fun setDisplay(){
        //status text
        when(gameStatus){
            GameStatus.PlayerOTurn -> binding.gameStatus.setText(R.string.game_status_monitor_player_o_turn)
            GameStatus.PlayerXTurn -> binding.gameStatus.setText(R.string.game_status_monitor_player_x_turn)
            GameStatus.PlayerOWin -> binding.gameStatus.setText(R.string.game_status_monitor_player_o_win)
            GameStatus.PlayerXWin -> binding.gameStatus.setText(R.string.game_status_monitor_player_x_win)
            else -> binding.gameStatus.setText(R.string.game_status_monitor_draw)
        }
        //tictactoe buttons text
        binding.button0.setText(when(boardStatus!!.board.elementAt(0)){1->"O";2->"X";else->""})
        binding.button1.setText(when(boardStatus!!.board.elementAt(1)){1->"O";2->"X";else->""})
        binding.button2.setText(when(boardStatus!!.board.elementAt(2)){1->"O";2->"X";else->""})
        binding.button3.setText(when(boardStatus!!.board.elementAt(3)){1->"O";2->"X";else->""})
        binding.button4.setText(when(boardStatus!!.board.elementAt(4)){1->"O";2->"X";else->""})
        binding.button5.setText(when(boardStatus!!.board.elementAt(5)){1->"O";2->"X";else->""})
        binding.button6.setText(when(boardStatus!!.board.elementAt(6)){1->"O";2->"X";else->""})
        binding.button7.setText(when(boardStatus!!.board.elementAt(7)){1->"O";2->"X";else->""})
        binding.button8.setText(when(boardStatus!!.board.elementAt(8)){1->"O";2->"X";else->""})
    }

    private fun updateGame(pressedButtonIdx: Int){
        // invalid input
        if (boardStatus?.board?.elementAt(pressedButtonIdx) != 0 || (gameStatus != GameStatus.PlayerXTurn && gameStatus != GameStatus.PlayerOTurn))  return
        // board status update
        val tmpBoard = boardStatus!!.board.toMutableList()
        tmpBoard[pressedButtonIdx] = when(gameStatus){
            GameStatus.PlayerOTurn -> 1
            else -> 2
        }
        boardStatus = GameBoard(tmpBoard.toList())
        updateGameState()
        // apply changes to screen
        setDisplay()
    }

    private fun updateGameState(){
        //O turn -> 1 , X turn -> 2
        val addedNum : Int = when(gameStatus){
            GameStatus.PlayerOTurn -> 1
            else -> 2
        }
        val winStatus = when(gameStatus){
            GameStatus.PlayerOTurn -> GameStatus.PlayerOWin
            else -> GameStatus.PlayerXWin
        }
        fun checkRow(rowIdx: Int): Boolean{
            if(boardStatus!!.board.elementAt(rowIdx*3) == addedNum
                && boardStatus!!.board.elementAt(rowIdx*3 + 1) == addedNum
                && boardStatus!!.board.elementAt(rowIdx*3 + 2) == addedNum)
                    return true
            return false
        }
        fun checkCol(colIdx: Int): Boolean{
            if(boardStatus!!.board.elementAt(colIdx) == addedNum
                && boardStatus!!.board.elementAt(colIdx + 3) == addedNum
                && boardStatus!!.board.elementAt(colIdx + 6) == addedNum)
                return true
            return false
        }
        fun checkDiag(): Boolean{
            if(boardStatus!!.board.elementAt(0) == addedNum
                && boardStatus!!.board.elementAt(4) == addedNum
                && boardStatus!!.board.elementAt(8) == addedNum)
                return true
            if(boardStatus!!.board.elementAt(2) == addedNum
                && boardStatus!!.board.elementAt(4) == addedNum
                && boardStatus!!.board.elementAt(6) == addedNum)
                return true
            return false
        }
        //row/col winning
        for(lineIdx in 0..2){
            if(checkRow(lineIdx) || checkCol(lineIdx)){
                gameStatus = winStatus
                return
            }
        }
        //diag winning
        if(checkDiag()){
            gameStatus = winStatus
            return
        }
        // no more space -> draw
        for(i in boardStatus!!.board){
            if(i == 0){
                gameStatus = when(gameStatus){
                    GameStatus.PlayerOTurn -> GameStatus.PlayerXTurn
                    else -> GameStatus.PlayerOTurn
                }
                return
            }
        }
        gameStatus = GameStatus.Draw
    }

    private fun restartGame(){
        gameStatus = GameStatus.PlayerOTurn
        boardStatus = GameBoard(listOf(0,0,0,0,0,0,0,0,0))
        setDisplay()
    }
}

// 게임의 상태, 보드의 상태를 정의하는 class
// 마음대로 수정하셔도 상관 없습니다.
enum class GameStatus {
    PlayerOTurn, PlayerXTurn, PlayerOWin, PlayerXWin, Draw
}

data class GameBoard(
    val board: List<Int>
)
