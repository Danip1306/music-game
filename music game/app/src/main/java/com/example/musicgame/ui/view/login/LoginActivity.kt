package com.example.musicgame.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.musicgame.R
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.theme.Gray
import com.example.musicgame.ui.theme.DarkGray
// import com.example.musicgame.ui.theme.LoginBackgroundPurple // Ya no es necesario si el fondo usa MaterialTheme.colorScheme.background
import com.example.musicgame.ui.view.dashboard.DashboardActivity
import com.example.musicgame.ui.view.register.RegisterActivity
import com.example.musicgame.ui.viewmodel.AuthResult
import com.example.musicgame.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicGameTheme {
                LoginScreen(authViewModel = authViewModel)
            }
        }

        lifecycleScope.launch {
            authViewModel.loginResult.collect { result ->
                when (result) {
                    is AuthResult.Success -> {
                        Toast.makeText(this@LoginActivity, getString(R.string.login_success_message), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                        authViewModel.resetLoginState()
                    }
                    is AuthResult.Error -> {
                        Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT).show()
                        authViewModel.resetLoginState()
                    }
                    AuthResult.Loading -> { /* Puedes añadir un indicador de carga aquí */ }
                    AuthResult.Idle -> { /* Estado inicial */ }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Fondo usa el color de fondo del MaterialTheme (EDE7F6 en LightTheme, CustomDark en DarkTheme)
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
                // Círculo con Logo y Nombre de la App
                Surface(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.surface, // Blanco para LightTheme, DarkSurface para DarkTheme
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_tonika_logo), // Asegúrate de tener este SVG
                            contentDescription = stringResource(R.string.app_name),
                            tint = MaterialTheme.colorScheme.primary, // Usará CustomPrimary
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.app_name),
                            fontSize = 18.sp,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.onSurface // Usará Black para LightTheme
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Título principal "Aprende música jugando"
                Text(
                    text = stringResource(R.string.login_subtitle),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground, // Usará CustomDark para LightTheme
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de Email o Usuario
                OutlinedTextField(
                    value = usernameOrEmail,
                    onValueChange = { usernameOrEmail = it },
                    label = { Text(stringResource(R.string.login_email_or_username_hint)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                // Campo de Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.login_password_hint)) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón Iniciar Sesión
                Button(
                    onClick = {
                        authViewModel.onLoginClicked(usernameOrEmail, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(text = stringResource(R.string.login_button_text), style = MaterialTheme.typography.titleMedium)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Enlace ¿No tienes cuenta? Registrarse (Texto gris)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.login_no_account_prefix),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stringResource(R.string.login_register_link),
                        color = if (isDarkTheme) DarkGray else Gray, // Usar Gray para el texto del enlace
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = MaterialTheme.typography.bodyMedium.fontWeight),
                        modifier = Modifier.clickable {
                            val intent = Intent(context, RegisterActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MusicGameTheme {
        // En previews, puedes mockear el ViewModel o pasar uno básico
        LoginScreen(authViewModel = AuthViewModel())
    }
}