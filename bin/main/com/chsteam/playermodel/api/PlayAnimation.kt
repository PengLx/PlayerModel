package com.chsteam.playermodel.api

import com.chsteam.playermodel.PlayerModel
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ModeledEntity
import org.bukkit.entity.Player

object PlayAnimation {

    fun playAnimation(entity: ModeledEntity, state: String)
    {
        val active = entity.models.values.iterator()
        while (active.hasNext()) {
            val activeModel = active.next()
            activeModel.animationHandler.playAnimation(state, 0.0, 1.0, 1.0, false)
        }
    }

    fun playAnimation(entity: ModeledEntity,
                      state: String,
                      remove: Boolean,
                      speed: Double,
                      lerpin: Double,
                      lerpout: Double,
                      ingorelerp: Boolean)
    {
        val active = entity.models.values.iterator()
        if (remove) {

            while (active.hasNext()) {
                val activeModel = active.next()
                activeModel.animationHandler.stopAnimation(state)
            }
        }
        else {
            while (active.hasNext()) {
                val activeModel = active.next()
                activeModel.animationHandler.playAnimation(state, lerpin, lerpout, speed, ingorelerp)
            }
        }
    }

    fun playAnimation(player: Player,
                      state: String,
                      remove: Boolean,
                      speed: Double,
                      lerpin: Double,
                      lerpout: Double,
                      ingorelerp: Boolean)
    {
        val entity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return
        playAnimation(entity,state,remove,speed,lerpin,lerpout,ingorelerp)
    }
}