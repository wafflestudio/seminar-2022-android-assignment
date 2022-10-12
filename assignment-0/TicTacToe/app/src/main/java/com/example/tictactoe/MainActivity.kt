package com.example.tictactoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val gameStatus: GameStatus? = null
    private val boardStatus: GameBoard? = null
    private val viewModel: TicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button1.setOnClickListener { onClickCell(1) }
        binding.button2.setOnClickListener { onClickCell(2) }
        binding.button3.setOnClickListener { onClickCell(3) }
        binding.button4.setOnClickListener { onClickCell(4) }
        binding.button5.setOnClickListener { onClickCell(5) }
        binding.button6.setOnClickListener { onClickCell(6) }
        binding.button7.setOnClickListener { onClickCell(7) }
        binding.button8.setOnClickListener { onClickCell(8) }
        binding.button9.setOnClickListener { onClickCell(9) }
        binding.restart.setOnClickListener { restartGame() }

    }

    private fun onClickCell(cellNumber:Int) {
        viewModel.clickCell(cellNumber)
        updateButtonText()
        updateGameStatusText()
    }

    private fun updateButtonText() {
        binding.button1.text = when (viewModel.gameBoard.cells[0]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button2.text = when (viewModel.gameBoard.cells[1]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button3.text = when (viewModel.gameBoard.cells[2]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button4.text = when (viewModel.gameBoard.cells[3]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button5.text = when (viewModel.gameBoard.cells[4]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button6.text = when (viewModel.gameBoard.cells[5]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button7.text = when (viewModel.gameBoard.cells[6]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button8.text = when (viewModel.gameBoard.cells[7]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
        binding.button9.text = when (viewModel.gameBoard.cells[8]) {
            "O" -> getString(R.string.O)
            "X" -> getString(R.string.X)
            else -> null
        }
    }

    private fun updateGameStatusText() {
        if (viewModel.gameStatus == GameStatus.PlayerOTurn) {
            binding.textView.text = getString(R.string.game_status_monitor_player_o_turn)
        }
        if (viewModel.gameStatus == GameStatus.PlayerXTurn) {
            binding.textView.text = getString(R.string.game_status_monitor_player_x_turn)
        }
        if (viewModel.gameStatus == GameStatus.PlayerOWin) {
            binding.textView.text = getString(R.string.game_status_monitor_player_o_win)
        }
        if (viewModel.gameStatus == GameStatus.PlayerXWin) {
            binding.textView.text = getString(R.string.game_status_monitor_player_x_win)
        }
        if (viewModel.gameStatus == GameStatus.Draw) {
            binding.textView.text = getString(R.string.game_status_monitor_draw)
        }
    }

    private fun restartGame() {
        viewModel.resetGameBoard()
        updateButtonText()
        updateGameStatusText()
    }


}

// 게임의 상태, 보드의 상태를 정의하는 class
// 마음대로 수정하셔도 상관 없습니다.
enum class GameStatus {
    PlayerOTurn, PlayerXTurn, PlayerOWin, PlayerXWin, Draw

}

data class GameBoard(
    val board: MutableList<String>
) {
    val cells: MutableList<String> = board}

