package com.example.tictactoe

import androidx.lifecycle.ViewModel
import org.junit.Assert.*
import org.junit.Test


class TicViewModelTest {

    @Test
    fun updateBoard1() {
        val viewModel = TicViewModel()
        viewModel.gameBoard.value?.cells?.set(0, "true")

    }
}