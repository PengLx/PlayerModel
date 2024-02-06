package com.chsteam.playermodel.animation

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.api.Animation
import com.chsteam.playermodel.api.PlayAnimation
import com.ticxo.modelengine.api.ModelEngineAPI
import org.bukkit.entity.Player

object Death : Animation {
    override fun play(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val state = PlayerModel.conf.getString("Settings.death") ?: return false
        PlayAnimation.playAnimation(modelEntity, state,false,1.0,0.0,1.0, false)
        return true
    }
}