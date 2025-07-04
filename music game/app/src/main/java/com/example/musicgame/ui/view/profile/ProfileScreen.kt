package com.example.musicgame.ui.view.profile

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicgame.ui.theme.MusicGameTheme
import com.example.musicgame.ui.viewmodel.ProfileViewModel
import com.example.musicgame.ui.view.login.LoginActivity
import androidx.compose.ui.res.stringResource // <-- Asegúrate de que esta importación esté
import com.example.musicgame.R // <-- Asegúrate de que esta importación esté

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel()
) {
    val currentUser by profileViewModel.currentUser.collectAsState()
    val username by profileViewModel.username.collectAsState()
    val creationDate by profileViewModel.creationDate.collectAsState()
    val isLoggedOut by profileViewModel.isLoggedOut.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
            profileViewModel.resetLogoutState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.profile_title)) }, // Usando stringResource
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = stringResource(R.string.profile_content_description_photo), // Usando stringResource
                    modifier = Modifier.fillMaxSize(0.6f),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (currentUser != null) {
                Text(
                    text = username ?: stringResource(R.string.profile_default_username), // Usando stringResource
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = currentUser?.email ?: stringResource(R.string.profile_email_not_available), // Usando stringResource
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                creationDate?.let {
                    Text(
                        text = stringResource(R.string.profile_member_since_prefix, it), // Usando stringResource con formato
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.profile_no_user_logged_in), // Usando stringResource
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.profile_stats_title), // Usando stringResource
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(stringResource(R.string.profile_levels_completed), style = MaterialTheme.typography.bodyLarge) // Usando stringResource
                Text(stringResource(R.string.profile_total_score), style = MaterialTheme.typography.bodyLarge) // Usando stringResource
                Text(stringResource(R.string.profile_achievements_unlocked), style = MaterialTheme.typography.bodyLarge) // Usando stringResource
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { profileViewModel.logout() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Filled.ExitToApp, contentDescription = stringResource(R.string.profile_logout_button)) // Usando stringResource (mismo ID para texto y contentDescription)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.profile_logout_button), color = MaterialTheme.colorScheme.error) // Usando stringResource
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    MusicGameTheme {
        ProfileScreen()
    }
}