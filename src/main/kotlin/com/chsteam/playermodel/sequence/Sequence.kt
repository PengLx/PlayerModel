package com.chsteam.playermodel.sequence

import taboolib.library.configuration.ConfigurationSection
import java.io.File

data class Sequence(
    val id: String,
    val file: File?,
    val root: ConfigurationSection,
)