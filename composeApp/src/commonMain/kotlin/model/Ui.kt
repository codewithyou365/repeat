package model

import org.codewithyou365.ConfigGit
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import repeat.composeapp.generated.resources.Res
import repeat.composeapp.generated.resources.none

enum class ActionEnum(val dialog: Boolean) {
    None(false),
    ContentInsert(true),
    ContentUpdate(true),
    ContentDelete(true),
}

@OptIn(ExperimentalResourceApi::class)
data class ContentIntend(
    val action: ActionEnum,
    val configGit: ConfigGit,
    val text: StringResource,
) {
    constructor() : this(ActionEnum.None, ConfigGit("", ""), Res.string.none) {}
    constructor(intend: ActionEnum) : this(intend, ConfigGit("", ""), Res.string.none) {}
    constructor(intend: ActionEnum, configGit: ConfigGit) : this(intend, configGit, Res.string.none) {}
    constructor(intend: ActionEnum, msg: StringResource) : this(intend, ConfigGit("", ""), msg) {}
}