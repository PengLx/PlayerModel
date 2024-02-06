package com.chsteam.playermodel

import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin


object PlayerModel : Plugin() {

    @Config
    lateinit var conf: Configuration
        private set

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    fun reload() {
        conf.reload()
    }
}