package com.chsteam.playermodel.api

import com.chsteam.playermodel.PlayerModel
import org.bukkit.entity.Player

object HidePlayer {
    fun hidePlayer(player: Player) {
        player.isInvisible = true
        if(PlayerModel.conf.getBoolean("Settings.armor")) {
            player.isInvisible = false
        }
    }

    fun showPlayer(player: Player) {
        player.isInvisible = false
    }
}