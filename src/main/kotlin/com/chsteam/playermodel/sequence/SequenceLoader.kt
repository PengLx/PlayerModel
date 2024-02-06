package com.chsteam.playermodel.sequence

import com.chsteam.playermodel.api.PlayerModelAPI
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.releaseResourceFile
import taboolib.common.platform.function.warning
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Configuration.Companion.loadFromFile
import java.io.File

object SequenceLoader {

    @Awake(LifeCycle.ENABLE)
    fun load() {
        val file = File(getDataFolder(),"sequence")
        if(!file.exists()) {
            releaseResourceFile("sequence/example.yml", true)
        }
        val sequences = load(file)
        PlayerModelAPI.sequence.clear()
        PlayerModelAPI.sequence.putAll(sequences.map {it.id to it})

        sequences.groupBy { it.id }.forEach { (id, c) ->
            if (c.size > 1) {
                warning("${c.size} sequences use duplicate id: $id")
            }
        }
    }

    fun load(file: File): List<Sequence> {
        return when {
            file.isDirectory -> file.listFiles()?.flatMap { load(it) }?.toList() ?: emptyList()
            file.name.endsWith(".yml") -> load(loadFromFile(file))
            else -> emptyList()
        }
    }

    fun load(file: Configuration): List<Sequence> {
        return file.getKeys(false).map {
            load(null, file.getConfigurationSection(it)!!)
        }
    }

    private fun load(file: File?,  root: ConfigurationSection): Sequence {
        return Sequence(root.name, file, root)
    }
}