package com.example.musicgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.theme.MusicGameColors

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                TestScreen()
            }
        }
    }
}

@Composable
fun TestScreen() {
    val questions = remember {
        listOf(
            "¿Cuál es la primera nota musical?",
            "¿Cuántos tiempos tiene una negra?",
            "¿Qué instrumento de viento es de madera?",
            "¿Qué significa 'Andante' en música?",
            "¿Cuántas líneas tiene un pentagrama?",
            "¿Qué es un sostenido?",
            "¿Qué figura musical dura medio tiempo?",
            "¿Cuál es el símbolo del silencio de redonda?",
            "¿Qué es un arpegio?",
            "¿Qué escala no tiene semitonos?",
            "¿Quién compuso la 'Novena Sinfonía'?"
        )
    }

    val options = remember {
        listOf(
            listOf("Do", "Re", "Mi", "Fa"),
            listOf("Uno", "Dos", "Medio", "Cuatro"),
            listOf("Trompeta", "Clarinete", "Trombón", "Tuba"),
            listOf("Lento", "Rápido", "Moderado", "Muy lento"),
            listOf("Tres", "Cuatro", "Cinco", "Seis"),
            listOf("Baja un semitono", "Sube un tono", "Sube un semitono", "Baja un tono"),
            listOf("Corchea", "Blanca", "Negra", "Semicorchea"),
            listOf("Silencio de negra", "Silencio de corchea", "Silencio de blanca", "Silencio de redonda"),
            listOf("Conjunto de notas simultáneas", "Sucesión de notas de un acorde", "Notas al azar", "Ritmo complejo"),
            listOf("Mayor", "Menor", "Pentatónica", "Cromática"),
            listOf("Bach", "Mozart", "Beethoven", "Chopin")
        )
    }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val selectedAnswers = remember { mutableStateMapOf<Int, String?>() }
    val allQuestionsAnswered = remember {
        derivedStateOf {
            selectedAnswers.size == questions.size && selectedAnswers.values.all { it != null }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MusicGameColors.Blue,
                        MusicGameColors.Purple,
                        MusicGameColors.Red
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MusicGameColors.White.copy(alpha = 0.95f)
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎼 Test de Conocimientos Musicales",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MusicGameColors.Purple,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Responde todas las preguntas para determinar tu nivel",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MusicGameColors.Gray800,
                        textAlign = TextAlign.Center
                    )

                    // Indicador de progreso
                    LinearProgressIndicator(
                        progress = selectedAnswers.size.toFloat() / questions.size,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        color = MusicGameColors.Green,
                        trackColor = MusicGameColors.Gray200
                    )
                    Text(
                        text = "${selectedAnswers.size}/${questions.size} preguntas respondidas",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MusicGameColors.Gray800,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Preguntas
            questions.forEachIndexed { qIndex, question ->
                QuestionCard(
                    questionNumber = qIndex + 1,
                    questionText = question,
                    options = options[qIndex],
                    selectedOption = selectedAnswers[qIndex],
                    onOptionSelected = { selectedOptionText ->
                        selectedAnswers[qIndex] = selectedOptionText
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Botón de finalizar
            Button(
                onClick = {
                    if (allQuestionsAnswered.value) {
                        val intent = Intent(context, DashboardActivity::class.java)
                        context.startActivity(intent)
                        (context as? ComponentActivity)?.finish()
                    } else {
                        Toast.makeText(context, "Por favor, responde todas las preguntas antes de finalizar.", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = allQuestionsAnswered.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (allQuestionsAnswered.value) MusicGameColors.Green else MusicGameColors.Gray200,
                    contentColor = if (allQuestionsAnswered.value) MusicGameColors.Black else MusicGameColors.Gray800
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = if (allQuestionsAnswered.value) "✅ Finalizar Test" else "Completa todas las preguntas",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun QuestionCard(
    questionNumber: Int,
    questionText: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MusicGameColors.White.copy(alpha = 0.9f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Número de pregunta
            Surface(
                modifier = Modifier.padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                color = MusicGameColors.Orange
            ) {
                Text(
                    text = "Pregunta $questionNumber",
                    style = MaterialTheme.typography.labelLarge,
                    color = MusicGameColors.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // Texto de la pregunta
            Text(
                text = questionText,
                style = MaterialTheme.typography.headlineMedium,
                color = MusicGameColors.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Opciones
            options.forEach { option ->
                val isSelected = (option == selectedOption)

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onOptionSelected(option) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) MusicGameColors.Blue.copy(alpha = 0.2f) else MusicGameColors.Gray100,
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) MusicGameColors.Blue else MusicGameColors.Gray200
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onOptionSelected(option) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MusicGameColors.Blue,
                                unselectedColor = MusicGameColors.Gray800
                            )
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isSelected) MusicGameColors.Blue else MusicGameColors.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestScreen() {
    MusicGameTheme {
        TestScreen()
    }
}