package com.example.tictactoe.model
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel(){
    private val _gameStatus: MutableLiveData<GameStatus> = MutableLiveData(GameStatus.PlayerOTurn)
    val gameStatus: LiveData<GameStatus> = _gameStatus

    private val _boardStatus: MutableLiveData<GameBoard> =
        MutableLiveData(GameBoard(ObservableArrayList<String?>(),9))
    val boardStatus: LiveData<GameBoard> = _boardStatus

    private val _boardCharacter: PlayerCharacter = PlayerCharacter()

    init{
        for(i : Int in 1..9){
            _boardStatus.value!!.board.add(null)
        }
    }

    fun setButtonClick(position: Int){
        val nowPlayer : GameStatus = _gameStatus.value!!
        if(_boardStatus.value!!.board[position] == null && ((nowPlayer == GameStatus.PlayerOTurn) || (nowPlayer == GameStatus.PlayerXTurn))) {
            // put String into board that match with now turn
            _boardStatus.value!!.board[position] = getSign(nowPlayer)
            _boardStatus.value!!.spaceLeft -= 1
            Log.d("hello",_boardStatus.value!!.spaceLeft.toString())

            // check win and turn finish
            if (checkWin(nowPlayer)){
                _gameStatus.value = getWinState(nowPlayer)
            } else if(_boardStatus.value!!.spaceLeft == 0) {
                _gameStatus.value = GameStatus.Draw
            } else {
                // turn change
                _gameStatus.value = turnChange(nowPlayer)
            }
        }
    }

    fun clearBoard(){
        for(i : Int in 0..8){
            _boardStatus.value!!.board[i] = null
        }
        _boardStatus.value!!.spaceLeft = 9
        _gameStatus.value = GameStatus.PlayerOTurn
    }

    private fun checkWin(player : GameStatus) : Boolean{
        // 1. Line check
        val verify = {i:Int, p:GameStatus -> _boardStatus.value!!.board[i] == getSign(p)}
        for(i:Int in 0..2) {
            if(verify(0+i*3,player) && verify(1+i*3,player) && verify(2+i*3,player)) {
                return true
            }
            if(verify(0+i,player) && verify(3+i, player) && verify(6+i, player)) {
                return true
            }
        }

        // 2. cross Line check
        if(verify(0,player) && verify(4,player) && verify(8,player)) {
            return true
        }
        if(verify(2,player) && verify(4,player) && verify(6,player)) {
            return true
        }

        // 3. no place
        return false
    }

    private fun getSign(player : GameStatus) : String{
        return if(player == GameStatus.PlayerOTurn){
            _boardCharacter.PlayerO
        } else{
            _boardCharacter.PlayerX
        }
    }

    private fun getWinState(player : GameStatus) : GameStatus{
        return if(player == GameStatus.PlayerOTurn) {
            GameStatus.PlayerOWin
        } else{
            GameStatus.PlayerXWin
        }
    }

    private fun turnChange(player : GameStatus): GameStatus{
        return if(player == GameStatus.PlayerOTurn) {
            GameStatus.PlayerXTurn
        } else{
            GameStatus.PlayerOTurn
        }
    }
}

// 게임의 상태, 보드의 상태를 정의하는 class
// 마음대로 수정하셔도 상관 없습니다.
enum class GameStatus {
    PlayerOTurn, PlayerXTurn, PlayerOWin, PlayerXWin, Draw
}

data class GameBoard(
    val board: ObservableArrayList<String?>,
    var spaceLeft: Int
)

data class PlayerCharacter(
    val PlayerO : String = "O",
    val PlayerX : String = "X"
)