package widget.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Toast(state: ToastState) {
    if (state.show.value) {
        Row(
            modifier = Modifier.fillMaxHeight().offset(y = (-16).dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AnimatedVisibility(
                    visible = state.count.value > 0,
                    enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)),
                    exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessLow))
                ) {
                    Text(
                        modifier = Modifier
                            .background(MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                        text = stringResource(state.content.value),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

        }
    }
}
