package com.chsteam.playermodel.data

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.api.PlayerModelAPI
import com.chsteam.playermodel.util.Model
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Data {

    fun create(player: Player) {
        val file = PlayerModelAPI.database.pull(player)
        if(file["model"] == null) {
            file.createSection("model")
            file["model"] = PlayerModel.conf.getString("Settings.player-model")
        }
        PlayerModelAPI.database.push(player)
    }

    fun set(player: Player,model: String) {
        val file = PlayerModelAPI.database.pull(player)
        file["model"] = model
        PlayerModelAPI.database.push(player)

        Model.removePlayerModel(player)
        Model.createPlayerModel(player)
    }

    fun updateModel(oldModel: String, newModel: String) {
        val playerList = Bukkit.getOnlinePlayers()
        playerList.forEach {
            val file = PlayerModelAPI.database.pull(it)
            if(file["model"] == oldModel) {
                file["model"] = newModel
            }
            PlayerModelAPI.database.push(it)
        }
    }
}