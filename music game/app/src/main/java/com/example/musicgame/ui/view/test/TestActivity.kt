package com.example.musicgame.ui.view.test

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicgame.R
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.viewmodel.TestViewModel
import com.example.musicgame.data.model.Question
import com.example.musicgame.data.repository.QuestionRepository
import com.example.musicgame.ui.view.result.ResultActivity

class TestActivity : ComponentActivity() {

    private var currentLevelId: String = "default_level_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentLevelId = intent.getStringExtra("levelId") ?: "default_level_id"

        setContent {
            MusicGameTheme {
                val testViewModel: TestViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            TestViewModel(questionRepository = QuestionRepository())
                        }
                    }
                )

                LaunchedEffect(currentLevelId) {
                    if (currentLevelId != "default_level_id") {
                        testViewModel.loadQuestionsForLevel(currentLevelId)
                    } else {
                        Toast.makeText(this@TestActivity, "Error: Nivel no especificado para el test.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }

                TestScreen(testViewModel, currentLevelId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(testViewModel: TestViewModel = viewModel(), levelId: String) {
    val questions by testViewModel.questions.collectAsState()
    val currentQuestionIndex by testViewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by testViewModel.selectedAnswers.collectAsState()
    val allQuestionsAnswered by testViewModel.allQuestionsAnswered.collectAsState()
    val correctAnswersCount by testViewModel.correctAnswersCount.collectAsState()

    val context = LocalContext.current

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(currentQuestionIndex) {
        showContent = false
        showContent = true
    }

    val currentQuestion = questions.getOrNull(currentQuestionIndex)
    val totalQuestions = questions.size

    LaunchedEffect(allQuestionsAnswered, currentQuestionIndex, totalQuestions) {
        if (totalQuestions > 0 && allQuestionsAnswered && currentQuestionIndex == totalQuestions - 1) {
            val intent = Intent(context, ResultActivity::class.java).apply {
                putExtra("CORRECT_ANSWERS_COUNT", correctAnswersCount)
                putExtra("TOTAL_QUESTIONS_COUNT", totalQuestions)
                putExtra("LEVEL_ID", levelId)
            }
            context.startActivity(intent)
            (context as? ComponentActivity)?.finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(animationSpec = tween(durationMillis = 800)) +
                    slideInVertically(initialOffsetY = { it / 4 }, animationSpec = tween(durationMillis = 800)),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.test_title),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = MaterialTheme.typography.displaySmall.fontWeight
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = stringResource(R.string.test_subtitle),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                if (currentQuestion != null) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(24.dp),
                        shadowElevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = currentQuestion.text,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            currentQuestion.options.forEach { optionText ->
                                val isSelected = selectedAnswers[currentQuestionIndex] == optionText
                                Button(
                                    onClick = {
                                        testViewModel.onOptionSelected(currentQuestionIndex, optionText)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                                        contentColor = if (isSelected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onPrimary
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                                ) {
                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            LinearProgressIndicator(
                                progress = (currentQuestionIndex + 1).toFloat() / totalQuestions,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.outline
                            )

                            Text(
                                text = stringResource(R.string.test_progress_format, currentQuestionIndex + 1, totalQuestions),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            if (selectedAnswers[currentQuestionIndex] != null) {
                                if (currentQuestionIndex < totalQuestions - 1) {
                                    testViewModel.goToNextQuestion()
                                } else {
                                    testViewModel.submitTest()
                                }
                            } else {
                                Toast.makeText(context, context.getString(R.string.test_incomplete_toast), Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = selectedAnswers[currentQuestionIndex] != null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = if (currentQuestionIndex < totalQuestions - 1) stringResource(R.string.test_next_button)
                            else stringResource(R.string.test_finish_button_enabled),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Cargando preguntas...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestScreen() {
    MusicGameTheme {
        TestScreen(testViewModel = TestViewModel(questionRepository = QuestionRepository()), levelId = "preview_level_id")
    }
}