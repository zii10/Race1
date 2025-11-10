package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        gameViewModel.MoveCircle(dragAmount.x, dragAmount.y)
                    }
                }
        ) {
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = message + "\n" +
                        "螢幕大小: ${gameViewModel.screenWidthPx.toInt()} * ${gameViewModel.screenHeightPx.toInt()}"
            )

            Text(
                text = "分數：${gameViewModel.score}",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    gameViewModel.gameRunning = true
                    gameViewModel.StartGame()
                }
            ) {
                Text("周子洋 遊戲開始")
            }
        }
    }
}

