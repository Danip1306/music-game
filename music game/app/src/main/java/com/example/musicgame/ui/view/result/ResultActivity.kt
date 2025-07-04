package com.example.musicgame.ui.view.result

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.R
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.view.dashboard.DashboardActivity

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS_COUNT", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS_COUNT", 0)

        setContent {
            MusicGameTheme {
                ResultScreen(correctAnswers = correctAnswers, totalQuestions = totalQuestions)
            }
        }
    }
}

@Composable
fun ResultScreen(correctAnswers: Int, totalQuestions: Int) {
    val context = LocalContext.current

    val recommendedLevel = when {
        correctAnswers == 0 -> "Necesitas practicar más"
        correctAnswers == 1 -> "Nivel 1: Principiante"
        correctAnswers == 2 -> "Nivel 2: Básico"
        correctAnswers == 3 -> "Nivel 3: Intermedio"
        correctAnswers == 4 -> "Nivel 4: Avanzado"
        correctAnswers >= 5 -> "Nivel 5: Experto"
        else -> "Nivel desconocido"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.results_title),
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center // ¡CORREGIDO AQUÍ!
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Obtuviste $correctAnswers de $totalQuestions preguntas correctas",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Nivel Recomendado:",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Center // ¡Y AQUÍ TAMBIÉN!
                )
                Text(
                    text = recommendedLevel,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, DashboardActivity::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    },
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
                        text = stringResource(R.string.results_back_to_dashboard_button),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultScreen() {
    MusicGameTheme {
        ResultScreen(correctAnswers = 3, totalQuestions = 5)
    }
}