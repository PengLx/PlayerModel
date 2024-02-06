package com.chsteam.playermodel.animation

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.api.Animation
import com.chsteam.playermodel.api.PlayAnimation
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.animation.state.ModelState
import org.bukkit.entity.Player

object Jump: Animation {

    lateinit var jumpState: String

    override fun play(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false

        if(modelEntity.state == ModelState.WALK) {
            jumpState = PlayerModel.conf.getString("Settings.jump.walk") ?: return false
        }
        else if(player.isSprinting) {
            jumpState = PlayerModel.conf.getString("Settings.jump.run") ?: return false
        } else {
            jumpState = PlayerModel.conf.getString("Settings.jump.idle") ?: return false
        }
        PlayAnimation.playAnimation(modelEntity, jumpState, false, 1.0, 0.0, 1.0, false)
        return true
    }
}