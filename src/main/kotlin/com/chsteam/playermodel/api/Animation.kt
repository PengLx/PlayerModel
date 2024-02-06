package com.chsteam.playermodel.api

import org.bukkit.entity.Player

interface Animation {
    fun play(player: Player): Boolean
}