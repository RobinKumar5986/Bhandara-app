package com.kgJr.bhandara.ui.layouts.Splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun FlappyBirdGame() {
    // Game state variables.
    var birdY by remember { mutableFloatStateOf(300f) }
    var birdVelocity by remember { mutableFloatStateOf(0f) }
    var gameOver by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var screenHeight by remember { mutableFloatStateOf(800f) }
    var screenWidth by remember { mutableFloatStateOf(800f) }

    // Constants.
    val gravity = 0.3f  // Reduced gravity for slower falling.
    val jumpVelocity = -10f
    val obstacleSpeed = 4f
    val obstacleWidth = 100f
    val gapHeight = 400f // Further increased gap between the pipes.
    val birdX = 100f
    val birdRadius = 20f

    // Data class representing an obstacle (pipe pair).
    data class Obstacle(var x: Float, var gapY: Float, var hasScored: Boolean = false)

    // List of obstacles.
    val obstacles = remember { mutableStateListOf<Obstacle>() }

    // Function to generate a new obstacle.
    fun createObstacle(): Obstacle {
        return Obstacle(
            x = screenWidth,
            gapY = Random.nextFloat() * (screenHeight - gapHeight - 100f) + 50f
        )
    }

    // Ensure there is at least one obstacle on start.
    if (obstacles.isEmpty()) {
        obstacles.add(createObstacle())
    }

    // Main game loop with a time-based physics update for smoother animation.
    LaunchedEffect(gameOver, screenWidth, screenHeight) {
        if (!gameOver) {
            var lastFrameTime = withFrameNanos { it }
            while (!gameOver) {
                val currentTime = withFrameNanos { it }
                val deltaTime = (currentTime - lastFrameTime) / 1_000_000_000f
                lastFrameTime = currentTime

                // Update bird physics.
                birdVelocity += gravity * deltaTime * 60f
                birdY += birdVelocity * deltaTime * 60f

                // Update all obstacles.
                obstacles.forEach { obstacle ->
                    obstacle.x -= obstacleSpeed * deltaTime * 60f
                }
                // Remove obstacles that have fully moved off the left side.
                obstacles.removeAll { it.x < -obstacleWidth }

                // Spawn a new obstacle if there are fewer than 2 and the rightmost pipe is far enough left.
                if (obstacles.size < 2) {
                    val rightmostX = obstacles.maxByOrNull { it.x }?.x ?: screenWidth
                    if (rightmostX < screenWidth * 0.3f) {
                        obstacles.add(createObstacle())
                    }
                }

                // Check for scoring and collisions.
                obstacles.forEach { obstacle ->
                    // Increase score if the bird passes the obstacle.
                    if (obstacle.x + obstacleWidth < birdX && !obstacle.hasScored) {
                        score++
                        obstacle.hasScored = true
                    }
                    // Check collision with the pipes.
                    if (obstacle.x < birdX + birdRadius && obstacle.x + obstacleWidth > birdX - birdRadius) {
                        if (birdY - birdRadius < obstacle.gapY || birdY + birdRadius > obstacle.gapY + gapHeight) {
                            gameOver = true
                        }
                    }
                }
                // Check collision with top and bottom boundaries.
                if (birdY - birdRadius < 0 || birdY + birdRadius > screenHeight) {
                    gameOver = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0C4DE)) // Subtle light steel blue background.
            .pointerInput(gameOver) {
                detectTapGestures(
                    onTap = {
                        if (gameOver) {
                            // Reset game state.
                            birdY = 300f
                            birdVelocity = 0f
                            score = 0
                            obstacles.clear()
                            obstacles.add(createObstacle())
                            gameOver = false
                        } else {
                            // Make the bird jump.
                            birdVelocity = jumpVelocity
                        }
                    }
                )
            }
            .onSizeChanged { size ->
                screenHeight = size.height.toFloat()
                screenWidth = size.width.toFloat()
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds() // Ensure drawing is confined within the Box.
        ) {
            // Draw the bird.
            drawCircle(
                color = Color(0xFFFFEBCD),
                radius = birdRadius,
                center = Offset(birdX, birdY)
            )
            // Draw each obstacle (pipe pair).
            obstacles.forEach { obstacle ->
                // Top pipe.
                drawRect(
                    color = Color(0xFF556B2F),
                    topLeft = Offset(obstacle.x, 0f),
                    size = Size(obstacleWidth, obstacle.gapY)
                )
                // Bottom pipe.
                drawRect(
                    color = Color(0xFF556B2F),
                    topLeft = Offset(obstacle.x, obstacle.gapY + gapHeight),
                    size = Size(obstacleWidth, size.height - (obstacle.gapY + gapHeight))
                )
            }
        }

        // Display the current score.
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Score: $score",
                color = Color.DarkGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Display game over overlay.
        if (gameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)) // Semi-transparent overlay.
            ) {
                Text(
                    text = "Game Over! Tap to Restart",
                    color = Color.DarkGray,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
