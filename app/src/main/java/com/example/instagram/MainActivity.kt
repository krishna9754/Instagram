package com.example.instagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.instagram.mode.AppTheme
import com.example.instagram.mode.ThemeSetting
import com.example.instagram.ui.theme.InstagramTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeSetting: ThemeSetting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColor = when (theme.value) {
                AppTheme.MODE_AUTO -> isSystemInDarkTheme()
                AppTheme.MODE_DAY -> false
                AppTheme.MODE_NIGHT -> true
            }
            InstagramTheme(darkTheme = useDarkColor) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ComposeThemeScreen(
                        onItemSelection = { theme -> themeSetting.theme = theme }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeThemeScreen(
    onItemSelection: (AppTheme) -> Unit,
) {

    val menuExpand = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Compose Theme")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { menuExpand.value = true }
                    ) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "")
                    }

                    Column(
                        modifier = Modifier.wrapContentSize(Alignment.TopStart)
                    ) {
                        DropdownMenu(
                            expanded = menuExpand.value,
                            onDismissRequest = { menuExpand.value = false },
                            modifier = Modifier
                                .width(200.dp)
                                .wrapContentSize(Alignment.TopStart)
                        ) {
                            DropdownMenuItem(
                                text = { "Auto" },
                                onClick = {
                                    onItemSelection(AppTheme.fromOrdinal(AppTheme.MODE_AUTO.ordinal))
                                    menuExpand.value = false
                                }
                            )
                            DropdownMenuItem(
                                text = { "Light Mode" },
                                onClick = {
                                    onItemSelection(AppTheme.fromOrdinal(AppTheme.MODE_NIGHT.ordinal))
                                    menuExpand.value = false
                                }
                            )
                            DropdownMenuItem(
                                text = { "Dark" },
                                onClick = {
                                    onItemSelection(AppTheme.fromOrdinal(AppTheme.MODE_DAY.ordinal))
                                    menuExpand.value = false
                                }
                            )
                        }
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "How to Implementation of Dark and Light Theme in Jetpack Compose | Android | Kotlin | Make it Easy")
            }
        }
    )
}