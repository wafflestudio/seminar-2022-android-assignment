# Wafflestudio Android Seminar Assignment1

## 과제의 목표
- 틱택토 게임을 위한 앱 UI 를직접 xml 파일을 수정하며 구성해 보고
- UI와 인터랙션(터치) 시 동작을 개발하여, 실제 실행 가능한 틱택토 게임을 개발한다.
- 과정에서 필요하다면 레포지토리에 포함된 스켈레톤 코드를 사용해도 된다.

## 과제 상세
- [TicTacToe](https://ko.wikipedia.org/wiki/%ED%8B%B1%ED%83%9D%ED%86%A0) 게임을 Android Application 으로 구현한다.

## 최소 스펙
1. 게임은 `PlayerOTurn`, `PlayerXTurn`, `PlayerOWin`, `PlayerXWin`, `Draw` 에 대응하는 게임 상태(턴)를 가진다.
2. 현재 게임의 상태를 UI 로 확인할 수 있어야 한다.
3. 게임 진행을 위한 3x3 칸 모양의 틱택토 보드를 UI 로 보여주어야 한다. (각 칸은 `O`, `X` 또는 공백을 보여줄 수 있다.)
4. 사용자가 `PlayerOTurn` 에 비어있는 틱택토 칸을 클릭하면 칸을 `O` 로 보여주고 (`X` 에 대해 동일) 다음 플레이어의 턴으로 게임 상태를 설정한다.
5. 만약 게임이 종료되는 조건이 만족되는 경우 (무승부, 또는 특정 플레이어 승리) 대응하는 게임 상태를 설정한다.
6. 재시도 버튼 UI 를 항상 보여주고 있어야 하며, 클릭 시 게임을 `PlayerOTurn` 으로 다시시작할 수 있어야 한다.

## 앱 데모
![Demo](demo.gif)
  
## 과제 제출 방법
1. assignement 레포지토리를 fork 한다.
2. fork 한 소스코드를 clone 하여 `assignment-0/TicTacToe` 파일 위에서 개발을 진행한다.
3. 개발 완료시 개발한 내용을 commit 하고 fork 한 레포지토리에 push 한다.
4. push 한 내용을 assignment 레포지토리에 pull reqest 로 날린다.

## 심화과정

> 각 심화과정은 세미나 도중에 배우지 않았던 개념들을 사용합니다.
> 심화과정의 경우 필수 사항은 아니지만, 함께 공부해 보면 좋은 개념들 입니다.

### 목적
- [kotlin Flow API](https://developer.android.com/kotlin/flow?hl=ko) 를 사용하여 Reactive Programming 에 대해 이해한다.
- 앞서 배운 Flow API 를 활용하여 UI 와 로직의 책임 분리 및 단방향 데이터 흐름을 위한 [MVVM 아키텍쳐](https://developer.android.com/topic/architecture?gclid=Cj0KCQjwjvaYBhDlARIsAO8PkE2rwPmn8V2KFzAdCJu4YXO8B9EpCs9YXoXLYEqpW09kkHqB4gQJRIYaAoCREALw_wcB&gclsrc=aw.ds) 를 배우고 사용해본다.


1. [kotlin Flow API](https://developer.android.com/kotlin/flow?hl=ko) 를 사용하여 현재 게임의 상태 및 틱택토 보드의 상태를 관리한다.
```kotlin
// 예시용 pseudo code
class MainActivity: AppCompatActivity() {

    private val gameStatus: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.PlayerOTurn)
    private val boardStatus: MutableStateFlow<GameBoard> =
        MutableStateFlow(GameBoard(listOf(null, null, null, null, null, null, null, null, null)))

    fun onClickRetry() {
        gameStatus.value = GameStatus.PlayerOTurn
        boardStatus.value = GameBoard(listOf(null, null, null, null, null, null, null, null, null))
    }

    fun subscribeUIUpdate() {
        lifecycleScope.launch {
            gameStatus.collect {
                val text = getString(
                    when (it) {
                        GameStatus.PlayerOTurn -> R.string.game_status_monitor_player_o_turn
                        GameStatus.PlayerXTurn -> R.string.game_status_monitor_player_x_turn
                        GameStatus.PlayerOWin -> R.string.game_status_monitor_player_o_win
                        GameStatus.PlayerXWin -> R.string.game_status_monitor_player_x_win
                        GameStatus.Draw -> R.string.game_status_monitor_draw
                    }
                )
                binding.gameStatusMonitor.text = text
            }
        }
    }
    // ...
```

2. [android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ko) 을 사용하여 TicTacToe 게임을 위해 필요한 로직들을 ViewModel 로 분리한다.


```kotlin
// 예시용 pseudo code
class MainActivity: AppCompatActivity {
    private val viewModel: MainViewModel by viewModels()

    fun subscribeUIUpdate() {
        lifecycleScope.launch {
            viewModel.gameStatus.collect {
            // ...
            }
        }
    }

    fun clickCell() {
        viewModel.clickCell(/* */)
    }
}

class MainViewModel : ViewModel() {
    val gameStatus: MutableStateFlow<GameStatus> = MutableStateFlow(GameStatus.PlayerOTurn)
    val boardStatus: MutableStateFlow<GameBoard> =
        MutableStateFlow(GameBoard(listOf(null, null, null, null, null, null, null, null, null)))

    fun clickCell(cellNumber: Int, isPlayerO: Boolean) {
        TODO()
    }

    fun clickReturn() {
        TODO()
    }
}

```

