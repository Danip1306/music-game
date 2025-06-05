package com.example.musicgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicgame.ui.theme.MusicGameTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

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
    // preguntas ejemplo, no son las finales
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

    // scroll
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        questions.forEachIndexed { qIndex, question ->
            QuestionItem(
                questionNumber = qIndex + 1,
                questionText = question,
                options = options[qIndex]
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Finalizar Test")
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun QuestionItem(questionNumber: Int, questionText: String, options: List<String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Pregunta $questionNumber:",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.White
        )
        Text(
            text = questionText,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp),
            color = Color.White
        )

        val selectedOption = remember { mutableStateOf<String?>(null) }

        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption.value),
                    onClick = { selectedOption.value = option },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = Color.White
                    )
                )
                Text(
                    text = option,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.White
                )
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