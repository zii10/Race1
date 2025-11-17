package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.edu.pu.csim.tcyang.race.ui.theme.Horse

class GameViewModel: ViewModel() {
    var winner by mutableStateOf(0)
        private set
    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf(0f)

    var score by mutableStateOf(0)
        private set

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }

    // val horse = Horse()
    val horses = mutableListOf<Horse>()

    fun MoveCircle(dx: Float, dy: Float) {
        circleX += dx
        circleY += dy
    }

    fun StartGame() {
        // 回到初使位置
        circleX = 100f
        circleY = screenHeightPx - 100f

        for (i in 0..2) {
            horses.add(Horse(i))
        }

        score = 0
        gameRunning = true

        viewModelScope.launch {
            while (gameRunning) { // 每 0.1 秒循環
                for (i in 0..2) {
                    horses[i].HorseRun()

                    if (horses[i].horseX >= screenWidthPx - 200) {
                        horses[i].horseX = 0
                    }

                    delay(100)
                    circleX += 10

                    if (circleX >= screenWidthPx - 100) {
                        score += 1
                        circleX = 100f
                    }
                }
            }
        }
    }
}
