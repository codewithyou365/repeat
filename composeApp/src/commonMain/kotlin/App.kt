import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repeat.composeapp.generated.resources.Res
import repeat.composeapp.generated.resources.msg_under_development
import viewmodel.rememberContentViewModel
import widget.toasts.Toasts
import widget.toasts.rememberToastsState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val toast = rememberToastsState()
    val content = rememberContentViewModel()
    MaterialTheme {
        Scaffold(
            topBar = {
                Row(
                    Modifier
                        .padding(18.dp)
                ) {
                    Text(
                        text = "SETTINGS",
                        modifier = Modifier.clickable {
                            toast.show(Res.string.msg_under_development)
                        })
                }
            },
            bottomBar = {
                Column() {
                    Column(
                        Modifier.fillMaxWidth()
                            .padding(top = 0.dp, bottom = 80.dp, start = 16.dp, end = 16.dp),
                    ) {
                        Button(
                            onClick = { toast.show(Res.string.msg_under_development) },
                        ) {
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("REPEAT")
                                Text("")
                                Text(
                                    text = "16",
                                    color = Color.Red,
                                )
                            }
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                    RowWithOverlappingBorders(listOf("CONTENT", "REVIEW", "STATS")) { name ->
                        when (name) {
                            "CONTENT" -> {
                                content.show()
                            }

                            "REVIEW" -> {
                                toast.show(Res.string.msg_under_development)
                            }

                            "STATS" -> {
                                toast.show(Res.string.msg_under_development)
                            }
                        }

                    }
                }
            },
            content = { innerPadding ->
            }
        )

        Content(toast, content)
        Toasts(toast)
    }
}

@Composable
fun RowWithOverlappingBorders(titles: List<String>, onToggle: (name: String) -> Unit) {
    val height = 55.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        titles.forEach { text ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(height),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    modifier = Modifier.clickable {
                        onToggle(text)
                    }
                )
            }
        }
    }
}
