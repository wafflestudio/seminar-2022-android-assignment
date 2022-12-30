package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    fun subscribeUIUpdate() {
        lifecycleScope.launch {
            launch {
                viewModel.boardStatus.collect {
                    Log.d("VIEW", "boardStatus collect")
                    binding.cell0.text = when (it.board[0]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell1.text = when (it.board[1]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell2.text = when (it.board[2]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell3.text = when (it.board[3]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell4.text = when (it.board[4]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell5.text = when (it.board[5]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell6.text = when (it.board[6]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell7.text = when (it.board[7]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                    binding.cell8.text = when (it.board[8]) {
                        CellStatus.Empty -> ""
                        CellStatus.PlayerO -> "O"
                        CellStatus.PlayerX -> "X"
                    }
                }
            }
            launch{
                viewModel.gameStatus.collect {
                    Log.d("VIEW", "gameStatus collect")
                    binding.gameStatusTextView.text = getString(when(it) {
                        GameStatus.PlayerOTurn-> R.string.game_status_monitor_player_o_turn
                        GameStatus.PlayerXTurn->R.string.game_status_monitor_player_x_turn
                        GameStatus.PlayerOWin->R.string.game_status_monitor_player_o_win
                        GameStatus.PlayerXWin->R.string.game_status_monitor_player_x_win
                        GameStatus.Draw->R.string.game_status_monitor_draw
                    })
                }
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeUIUpdate()
        binding.cell0.setOnClickListener{viewModel.clickCell(0)}
        binding.cell1.setOnClickListener{viewModel.clickCell(1)}
        binding.cell2.setOnClickListener{viewModel.clickCell(2)}
        binding.cell3.setOnClickListener{viewModel.clickCell(3)}
        binding.cell4.setOnClickListener{viewModel.clickCell(4)}
        binding.cell5.setOnClickListener{viewModel.clickCell(5)}
        binding.cell6.setOnClickListener{viewModel.clickCell(6)}
        binding.cell7.setOnClickListener{viewModel.clickCell(7)}
        binding.cell8.setOnClickListener{viewModel.clickCell(8)}
        binding.restartButton.setOnClickListener{viewModel.clickRestart()}
    }
}

enum class GameStatus {
    PlayerOTurn, PlayerXTurn, PlayerOWin, PlayerXWin, Draw
}

enum class CellStatus {
    PlayerO, PlayerX, Empty
}

data class GameBoard(
    val board: Array<CellStatus> = Array(9, {CellStatus.Empty})
) {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return board.contentHashCode()
    }
}
