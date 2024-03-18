// This example assumes you have added the required dependency
// implementation("androidx.compose.foundation:foundation:1.6.0-alpha04")

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.ActionEnum
import model.ContentIntend
import org.codewithyou365.ConfigGit
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import repeat.composeapp.generated.resources.Res
import repeat.composeapp.generated.resources.btn_add
import repeat.composeapp.generated.resources.btn_back
import repeat.composeapp.generated.resources.btn_cancel
import repeat.composeapp.generated.resources.btn_confirm
import repeat.composeapp.generated.resources.btn_copy
import repeat.composeapp.generated.resources.btn_delete
import repeat.composeapp.generated.resources.btn_download
import repeat.composeapp.generated.resources.content_copy
import repeat.composeapp.generated.resources.download
import repeat.composeapp.generated.resources.label_add_git_repository
import repeat.composeapp.generated.resources.label_branch
import repeat.composeapp.generated.resources.label_edit_git_repository
import repeat.composeapp.generated.resources.label_url
import repeat.composeapp.generated.resources.msg_confirm_delete_item
import repeat.composeapp.generated.resources.msg_under_development
import viewmodel.ContentViewModel
import viewmodel.rememberContentViewModel
import widget.swipe.drawer.Drawer
import widget.toasts.ToastsState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Content(
    toast: ToastsState,
    viewModel: ContentViewModel = rememberContentViewModel(),
) {
    val texts by viewModel.state.collectAsState()
    val intend = remember { mutableStateOf(ContentIntend()) }

    AnimatedVisibility(
        visible = viewModel.animationShow.value,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    ) {
        if (viewModel.show.value) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        title = {
                            Text(
                                text = "CONTENT",
                                modifier = Modifier.fillMaxWidth(),
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { viewModel.hide() }) {
                                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = stringResource(Res.string.btn_back))
                            }
                        },
                    )
                },
                bottomBar = {

                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        if (texts.isNotEmpty()) {
                            LazyColumn {
                                itemsIndexed(texts) { index, _ ->
                                    Drawer(
                                        layer = {
                                            IconButton(
                                                onClick = {
                                                    intend.value = ContentIntend(ActionEnum.ContentUpdate, ConfigGit(texts[index].url, texts[index].branch), Res.string.label_edit_git_repository)
                                                }) {
                                                Icon(Icons.Filled.Edit, contentDescription = stringResource(Res.string.btn_delete))
                                            }
                                            IconButton(
                                                onClick = {
                                                    intend.value = ContentIntend(ActionEnum.ContentDelete, ConfigGit(texts[index].url, texts[index].branch), Res.string.msg_confirm_delete_item)
                                                }) {
                                                Icon(Icons.Filled.Delete, contentDescription = stringResource(Res.string.btn_delete))
                                            }
                                            IconButton(
                                                onClick = {
                                                    intend.value = ContentIntend(ActionEnum.ContentInsert, ConfigGit(texts[index].url, texts[index].branch), Res.string.label_add_git_repository)
                                                }) {
                                                Icon(vectorResource(Res.drawable.content_copy), contentDescription = stringResource(Res.string.btn_copy))
                                            }
                                            IconButton(
                                                onClick = {
                                                    toast.show(Res.string.msg_under_development)
                                                }) {
                                                Icon(vectorResource(Res.drawable.download), contentDescription = stringResource(Res.string.btn_download))
                                            }
                                        },
                                        layerWidth = 200.dp,
                                        openLayerWidth = 30.dp,
                                        closeLayerWidth = 30.dp
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                            ) {
                                                Text(text = texts[index].url)
                                                Text(text = texts[index].branch, textAlign = TextAlign.Right, modifier = Modifier.fillMaxWidth())
                                            }
                                        }
                                        Divider()
                                    }
                                }
                            }

                        } else {
                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = {
                                    intend.value = ContentIntend(ActionEnum.ContentInsert, Res.string.label_add_git_repository)
                                },
                            ) {
                                Column(
                                    Modifier.fillMaxWidth().padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(stringResource(Res.string.btn_add))
                                }
                            }
                        }
                    }
                }
            )
            when (intend.value.action) {
                ActionEnum.ContentDelete ->
                    ShowConfirmDialog({ intend.value = ContentIntend() }, {
                        viewModel.delete(intend.value.configGit.url, intend.value.configGit.branch)
                    }, intend.value.text)

                ActionEnum.ContentInsert ->
                    ShowGitInfoEditDialog(intend.value.configGit, { intend.value = ContentIntend() }, {
                        viewModel.insert(it.url, it.branch)
                    }, intend.value.text)

                ActionEnum.ContentUpdate ->
                    ShowGitInfoEditDialog(intend.value.configGit, { intend.value = ContentIntend() }, {
                        viewModel.update(it.url, it.branch, intend.value.configGit.url, intend.value.configGit.branch)
                    }, intend.value.text)

                ActionEnum.None -> {}

            }

            AnimatedVisibility(
                visible = intend.value.action.dialog,
                enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessHigh))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowConfirmDialog(hide: () -> Unit, onConfirm: () -> Unit, message: StringResource) {
    AlertDialog(
        onDismissRequest = hide,
        text = { Text(stringResource(message)) },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                hide()
            }) {
                Text(stringResource(Res.string.btn_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = hide) {
                Text(stringResource(Res.string.btn_cancel))
            }
        }
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShowGitInfoEditDialog(info: ConfigGit, hide: () -> Unit, confirm: (ConfigGit) -> Unit, title: StringResource) {
    val url = remember { mutableStateOf(info.url) }
    val branch = remember { mutableStateOf(info.branch) }
    AlertDialog(
        onDismissRequest = hide,
        title = { Text(stringResource(title)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                //.background(Color.Gray)
            ) {
                Text("")
                OutlinedTextField(
                    value = url.value,
                    label = { Text(stringResource(Res.string.label_url)) },
                    onValueChange = { text ->
                        url.value = text
                    }
                )
                OutlinedTextField(
                    value = branch.value,
                    label = { Text(stringResource(Res.string.label_branch)) },
                    onValueChange = { text ->
                        branch.value = text
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                confirm(ConfigGit(url.value, branch.value))
                hide()
            }) {
                Text(stringResource(Res.string.btn_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = hide) {
                Text(stringResource(Res.string.btn_cancel))
            }
        }
    )

}
