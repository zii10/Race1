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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

        val imageBitmaps = listOf(
            ImageBitmap.imageResource(R.drawable.horse0),
            ImageBitmap.imageResource(R.drawable.horse1),
            ImageBitmap.imageResource(R.drawable.horse2),
            ImageBitmap.imageResource(R.drawable.horse3)
        )

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
            for(i in 0..2) {
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].number],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].horseX,
                        gameViewModel.horses[i].horseY
                    ),
                    dstSize = IntSize(200, 200)
                )
            }
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
            if (gameViewModel.winner != 0) {
            Text(
                text = "第 ${gameViewModel.winner} 馬獲勝！",
                color = Color.Red
            )
        }
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
            )
            {
                Text("遊戲開始")
            }
        }
    }
}