package com.chsteam.playermodel.data

import org.bukkit.entity.Player
import taboolib.library.configuration.ConfigurationSection

abstract class Database {

    abstract fun pull(player: Player): ConfigurationSection

    abstract fun push(player: Player)

    abstract fun release(player: Player)

}