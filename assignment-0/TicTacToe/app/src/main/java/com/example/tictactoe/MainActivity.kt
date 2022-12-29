package com.example.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var gameStatus: GameStatus? = GameStatus.PlayerOTurn
    private val boardStatus: GameBoard = GameBoard(mutableListOf(0,0,0,0,0,0,0,0,0))
    private var count=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener{ if(boardStatus.pan[0]==0) setButton1(); }
        binding.button2.setOnClickListener{ if(boardStatus.pan[1]==0) setButton2(); }
        binding.button3.setOnClickListener{ if(boardStatus.pan[2]==0) setButton3(); }
        binding.button4.setOnClickListener{ if(boardStatus.pan[3]==0) setButton4(); }
        binding.button5.setOnClickListener{ if(boardStatus.pan[4]==0) setButton5(); }
        binding.button6.setOnClickListener{ if(boardStatus.pan[5]==0) setButton6(); }
        binding.button7.setOnClickListener{ if(boardStatus.pan[6]==0) setButton7(); }
        binding.button8.setOnClickListener{ if(boardStatus.pan[7]==0) setButton8(); }
        binding.button9.setOnClickListener{ if(boardStatus.pan[8]==0) setButton9(); }
        binding.restart.setOnClickListener{ restart(); }

    }

    fun restart() {
        for(i:Int in 0..8) {
            boardStatus.pan[i]=0
        }
        binding.button1.text=""
        binding.button2.text=""
        binding.button3.text=""
        binding.button4.text=""
        binding.button5.text=""
        binding.button6.text=""
        binding.button7.text=""
        binding.button8.text=""
        binding.button9.text=""
        count=0
        binding.textView.text=getString(R.string.game_status_monitor_player_o_turn)
        gameStatus=GameStatus.PlayerOTurn

    }

    fun setButton1() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button1.text=getString(R.string.O)
            //Toast.makeText(this, "set 0!", Toast.LENGTH_SHORT).show();
            boardStatus.pan[0]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button1.text=getString(R.string.X)
            boardStatus.pan[0]=2
            updateStatus()
        }
    }

    fun setButton2() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button2.text=getString(R.string.O)
            //Toast.makeText(this, "set 1!", Toast.LENGTH_SHORT).show();
            boardStatus.pan[1]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button2.text=getString(R.string.X)
            boardStatus.pan[1]=2
            updateStatus()
        }
    }


    fun setButton3() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button3.text=getString(R.string.O)
            //Toast.makeText(this, "set 2!", Toast.LENGTH_SHORT).show();
            boardStatus.pan[2]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button3.text=getString(R.string.X)
            boardStatus.pan[2]=2
            updateStatus()
        }
    }

    fun setButton4() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button4.text=getString(R.string.O)
            boardStatus.pan[3]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button4.text=getString(R.string.X)
            boardStatus.pan[3]=2
            updateStatus()
        }
    }

    fun setButton5() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button5.text=getString(R.string.O)
            //Toast.makeText(this, "set 4!", Toast.LENGTH_SHORT).show();
            boardStatus.pan[4]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button5.text=getString(R.string.X)
            boardStatus.pan[4]=2
            updateStatus()
        }
    }

    fun setButton6() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button6.text=getString(R.string.O)
            boardStatus.pan[5]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button6.text=getString(R.string.X)
            boardStatus.pan[5]=2
            updateStatus()
        }
    }

    fun setButton7() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button7.text=getString(R.string.O)
            boardStatus.pan[6]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button7.text=getString(R.string.X)
            boardStatus.pan[6]=2
            updateStatus()
        }
    }

    fun setButton8() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button8.text=getString(R.string.O)
            //Toast.makeText(this, "set 7!", Toast.LENGTH_SHORT).show();
            boardStatus.pan[7]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button8.text=getString(R.string.X)
            boardStatus.pan[7]=2
            updateStatus()
        }
    }

    fun setButton9() {
        if(gameStatus==GameStatus.PlayerOTurn) {
            binding.button9.text=getString(R.string.O)
            boardStatus.pan[8]=1
            updateStatus()
        }
        else if(gameStatus==GameStatus.PlayerXTurn) {
            binding.button9.text=getString(R.string.X)
            boardStatus.pan[8]=2
            updateStatus()
        }
    }


    fun updateStatus() {
        if(gameStatus==GameStatus.PlayerOWin || gameStatus==GameStatus.PlayerXWin || gameStatus == GameStatus.Draw) {

        }
        else if(gameStatus==GameStatus.PlayerOTurn) {
            count++;
            if(win()) {
                gameStatus=GameStatus.PlayerOWin
                binding.textView.text=getString(R.string.game_status_monitor_player_o_win)
            }
            else if(full()) {
                gameStatus=GameStatus.Draw
                binding.textView.text=getString(R.string.game_status_monitor_draw)
            }
            else {
                gameStatus=GameStatus.PlayerXTurn
                binding.textView.text=getString(R.string.game_status_monitor_player_x_turn)
            }
        }
        else {
            count++;
            if(win()) {
                gameStatus=GameStatus.PlayerXWin
                binding.textView.text=getString(R.string.game_status_monitor_player_x_win)
            }
            else if(full()) {
                gameStatus=GameStatus.Draw
                binding.textView.text=getString(R.string.game_status_monitor_draw)
            }
            else {
                gameStatus=GameStatus.PlayerOTurn
                binding.textView.text=getString(R.string.game_status_monitor_player_o_turn)
            }
        }

    }

    fun win():Boolean {
        if(boardStatus.pan[0]>0 && boardStatus.pan[0]==boardStatus.pan[1] && boardStatus.pan[1]==boardStatus.pan[2]) return true
        else if(boardStatus.pan[3]>0 && boardStatus.pan[3]==boardStatus.pan[4] && boardStatus.pan[4]==boardStatus.pan[5]) return true
        else if(boardStatus.pan[6]>0 && boardStatus.pan[6]==boardStatus.pan[7] && boardStatus.pan[7]==boardStatus.pan[8]) return true
        else if(boardStatus.pan[0]>0 && boardStatus.pan[0]==boardStatus.pan[3] && boardStatus.pan[3]==boardStatus.pan[6]) return true
        else if(boardStatus.pan[1]>0 && boardStatus.pan[1]==boardStatus.pan[4] && boardStatus.pan[4]==boardStatus.pan[7]) return true
        else if(boardStatus.pan[2]>0 && boardStatus.pan[2]==boardStatus.pan[5] && boardStatus.pan[5]==boardStatus.pan[8]) return true
        else if(boardStatus.pan[0]>0 && boardStatus.pan[0]==boardStatus.pan[4] && boardStatus.pan[4]==boardStatus.pan[8]) return true
        else if(boardStatus.pan[2]>0 && boardStatus.pan[2]==boardStatus.pan[4] && boardStatus.pan[4]==boardStatus.pan[6]) return true
        else return false
    }

    fun full():Boolean {
        if(count==9) return true

        return false
    }

}

// 게임의 상태, 보드의 상태를 정의하는 class
// 마음대로 수정하셔도 상관 없습니다.
enum class GameStatus {
    PlayerOTurn, PlayerXTurn, PlayerOWin, PlayerXWin, Draw
}

data class GameBoard(
    val board: MutableList<Int>
) {
    val pan = board
}
