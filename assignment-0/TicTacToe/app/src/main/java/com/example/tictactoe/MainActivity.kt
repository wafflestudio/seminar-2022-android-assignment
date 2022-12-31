package com.example.tictactoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cell0.setOnClickListener { onTurn(0) }
        binding.cell1.setOnClickListener { onTurn(1) }
        binding.cell2.setOnClickListener { onTurn(2) }
        binding.cell3.setOnClickListener { onTurn(3) }
        binding.cell4.setOnClickListener { onTurn(4) }
        binding.cell5.setOnClickListener { onTurn(5) }
        binding.cell6.setOnClickListener { onTurn(6) }
        binding.cell7.setOnClickListener { onTurn(7) }
        binding.cell8.setOnClickListener { onTurn(8) }

        binding.restart.setOnClickListener { restartGame() }
    }

    private fun onTurn(cellNumber: Int) {
        viewModel.clickCell(cellNumber)
        updateButtonText()
        updateGameStatusText()
    }

    private fun updateButtonText() {
        binding.cell0.text = viewModel.gameBoard[0].toString()
        binding.cell1.text = viewModel.gameBoard[1].toString()
        binding.cell2.text = viewModel.gameBoard[2].toString()
        binding.cell3.text = viewModel.gameBoard[3].toString()
        binding.cell4.text = viewModel.gameBoard[4].toString()
        binding.cell5.text = viewModel.gameBoard[5].toString()
        binding.cell6.text = viewModel.gameBoard[6].toString()
        binding.cell7.text = viewModel.gameBoard[7].toString()
        binding.cell8.text = viewModel.gameBoard[8].toString()
    }


    private fun updateGameStatusText() {
        if (viewModel.gameStatus == GameStatus.PlayerOTurn) {
            binding.state.text = getString(R.string.game_status_monitor_player_o_turn)
        }
        if (viewModel.gameStatus == GameStatus.PlayerXTurn) {
            binding.state.text = getString(R.string.game_status_monitor_player_x_turn)
        }
        if (viewModel.gameStatus == GameStatus.PlayerOWin) {
            binding.state.text = getString(R.string.game_status_monitor_player_o_win)
        }
        if (viewModel.gameStatus == GameStatus.PlayerXWin) {
            binding.state.text = getString(R.string.game_status_monitor_player_x_win)
        }
        if (viewModel.gameStatus == GameStatus.Draw) {
            binding.state.text = getString(R.string.game_status_monitor_draw)
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
