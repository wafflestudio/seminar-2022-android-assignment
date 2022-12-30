package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //for binding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //for binding
        setContentView(binding.root)

        findViewById<Button>(R.id.button_11).setOnClickListener { clickCell(0) }
        findViewById<Button>(R.id.button_12).setOnClickListener { clickCell(1) }
        findViewById<Button>(R.id.button_13).setOnClickListener { clickCell(2) }
        findViewById<Button>(R.id.button_21).setOnClickListener { clickCell(3) }
        findViewById<Button>(R.id.button_22).setOnClickListener { clickCell(4) }
        findViewById<Button>(R.id.button_23).setOnClickListener { clickCell(5) }
        findViewById<Button>(R.id.button_31).setOnClickListener { clickCell(6) }
        findViewById<Button>(R.id.button_32).setOnClickListener { clickCell(7) }
        findViewById<Button>(R.id.button_33).setOnClickListener { clickCell(8) }
        findViewById<Button>(R.id.restart_button).setOnClickListener { onClickRetry() }
    }

    private fun clickCell(clickedcell: Int) {
        viewModel.clickCell(clickedcell, viewModel.gameStatus.value == GameStatus.PlayerOTurn)
        subscribeUIUpdate(clickedcell)
    }

    fun onClickRetry() {
        viewModel.gameStatus.value = GameStatus.PlayerOTurn
        viewModel.boardStatus.value =
            GameBoard(mutableListOf(null, null, null, null, null, null, null, null, null))
    } //retry 시 원상 복귀.

    fun subscribeUIUpdate(clickedcell: Int) {
        lifecycleScope.launch {
            viewModel.gameStatus.collect {
                val text = getString(
                    when (it) {
                        GameStatus.PlayerOTurn -> R.string.game_status_monitor_player_o_turn
                        GameStatus.PlayerXTurn -> R.string.game_status_monitor_player_x_turn
                        GameStatus.PlayerOWin -> R.string.game_status_monitor_player_o_win
                        GameStatus.PlayerXWin -> R.string.game_status_monitor_player_x_win
                        GameStatus.Draw -> R.string.game_status_monitor_draw
                    }
                )
                binding.gameStatusTextview.text = text
            }
        }
        lifecycleScope.launch {
            viewModel.boardStatus.collect {
                val text = (
                        when (it.board[clickedcell]) {
                            true -> "O"
                            false -> "X"
                            null -> ""
                        }
                        )
                when (clickedcell) {
                    0 -> binding.button11.text = text
                    1 -> binding.button12.text = text
                    2 -> binding.button13.text = text
                    3 -> binding.button21.text = text
                    4 -> binding.button22.text = text
                    5 -> binding.button23.text = text
                    6 -> binding.button31.text = text
                    7 -> binding.button32.text = text
                    8 -> binding.button33.text = text

                }
            }
        }
    }
} //collect 한 값에 따라 text 업데이트.


enum class GameStatus {
    PlayerOTurn, PlayerXTurn, PlayerOWin, PlayerXWin, Draw
}

data class GameBoard(val board: MutableList<Boolean?>)

class MainViewModel : ViewModel() {
    val gameStatus: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.PlayerOTurn)
    val boardStatus: MutableStateFlow<GameBoard> =
        MutableStateFlow(
            GameBoard(
                mutableListOf(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
        )

    fun clickCell(cellNumber: Int, isPlayerO: Boolean) {
        if (isPlayerO) {
            gameStatus.value = GameStatus.PlayerXTurn
            boardStatus.value.board[cellNumber] = true
        } else {
            gameStatus.value = GameStatus.PlayerOTurn
            boardStatus.value.board[cellNumber] = false
        } //game 턴 넘기기
        clickReturn(isPlayerO)
    }

    fun clickReturn(b: Boolean) {
        if (boardStatus.value.board[0] == b && boardStatus.value.board[1] == b && boardStatus.value.board[2] == b ||
            boardStatus.value.board[3] == b && boardStatus.value.board[4] == b && boardStatus.value.board[5] == b ||
            boardStatus.value.board[6] == b && boardStatus.value.board[7] == b && boardStatus.value.board[8] == b ||
            boardStatus.value.board[0] == b && boardStatus.value.board[3] == b && boardStatus.value.board[6] == b ||
            boardStatus.value.board[1] == b && boardStatus.value.board[4] == b && boardStatus.value.board[7] == b ||
            boardStatus.value.board[2] == b && boardStatus.value.board[5] == b && boardStatus.value.board[8] == b ||
            boardStatus.value.board[0] == b && boardStatus.value.board[4] == b && boardStatus.value.board[8] == b ||
            boardStatus.value.board[2] == b && boardStatus.value.board[4] == b && boardStatus.value.board[6] == b
        ) {
            if (b) gameStatus.value = GameStatus.PlayerOWin
            else gameStatus.value = GameStatus.PlayerXWin
        }
        if (boardStatus.value.board[0] != null &&
            boardStatus.value.board[1] != null &&
            boardStatus.value.board[2] != null &&
            boardStatus.value.board[3] != null &&
            boardStatus.value.board[4] != null &&
            boardStatus.value.board[5] != null &&
            boardStatus.value.board[6] != null &&
            boardStatus.value.board[7] != null &&
            boardStatus.value.board[8] != null
        ) gameStatus.value=GameStatus.Draw

        return
    }

}
