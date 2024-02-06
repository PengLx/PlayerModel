package com.chsteam.playermodel.api

import com.chsteam.playermodel.data.Data
import com.chsteam.playermodel.util.Model
import org.bukkit.entity.Player

object ApplyModel {

    fun apply(player: Player) {
        PlayerModelAPI.modelPlayer[player] = true

        Data().create(player)
        Model.createPlayerModel(player)
        HidePlayer.hidePlayer(player)
    }

    fun respawn(player: Player) {

        if(PlayerModelAPI.modelPlayer[player] == true) {
            Model.createPlayerModel(player)
            HidePlayer.hidePlayer(player)
        }
    }

    fun remove(player: Player) {
        PlayerModelAPI.modelPlayer[player] = false

        Model.removePlayerModel(player)
        HidePlayer.showPlayer(player)
    }

    fun deathRemove(player: Player) {
        Model.removePlayerModel(player)
        HidePlayer.showPlayer(player)
    }
}