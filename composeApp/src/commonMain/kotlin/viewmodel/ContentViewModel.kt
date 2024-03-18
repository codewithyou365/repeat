package viewmodel

import CoroutineViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dao.ConfigGitDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.codewithyou365.ConfigGit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Composable
fun rememberContentViewModel(): ContentViewModel {
    return remember { ContentViewModel() }
}

@Stable
class ContentViewModel : CoroutineViewModel(), KoinComponent {
    private val configGitDao: ConfigGitDao by inject()

    private val _state = MutableStateFlow<List<ConfigGit>>(emptyList())
    val state: StateFlow<List<ConfigGit>> = _state

    val show = mutableStateOf(false)
    val animationShow = mutableStateOf(false)
    private var timer: Job? = null

    init {
        selectAll()
    }

    fun insert(url: String, branch: String) {
        configGitDao.insert(url, branch)
        _state.value = configGitDao.selectAll()
    }

    fun selectAll() {
        _state.value = configGitDao.selectAll()
    }

    fun update(newUrl: String, newBranch: String, oldUrl: String, oldBranch: String) {
        configGitDao.update(newUrl, newBranch, oldUrl, oldBranch)
        _state.value = configGitDao.selectAll()
    }

    fun delete(url: String, branch: String) {
        configGitDao.delete(url, branch)
        _state.value = configGitDao.selectAll()
    }

    fun show() {
        show.value = true
        animationShow.value = true
        timer?.cancel()
    }

    fun hide() {
        animationShow.value = false
        timer = coroutineScope.launch {
            delay(8000)
            show.value = false
            timer = null
        }
    }
}
