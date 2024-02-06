package com.chsteam.playermodel.util

import com.chsteam.playermodel.api.PlayerModelAPI
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.nms.entity.wrapper.RangeManager
import org.bukkit.entity.Player
import taboolib.common.platform.function.console
import taboolib.common.platform.function.warning
import taboolib.module.lang.asLangText

object Model {

    fun createPlayerModel(player: Player) {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId)

        if(modelEntity != null) {
            modelEntity.destroy()
        } else {
            ModelEngineAPI.createModeledEntity(player)
        }

        val modelName = PlayerModelAPI.database.pull(player).getString("model") ?: return

        addModel(player, modelName)
    }

    fun removePlayerModel(player: Player) {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return warning(console().asLangText("player-error-model"))

        modelEntity.destroy()

        player.isInvisible = false
    }

    fun timelyPlayerModel(player: Player, model: String) {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId)

        if(modelEntity != null) {
            modelEntity.destroy()
        } else {
            ModelEngineAPI.createModeledEntity(player)
        }


        addModel(player, model)
    }

    fun invisibleBodyModel(player: Player, body: String, visibility: Boolean) {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return warning(console().asLangText("player-error-model"))

        val active = modelEntity.models.values.iterator()

        while (active.hasNext()) {
            val activeModel = active.next()

            activeModel.getBone(body)?.let {
                if(visibility) it.activeModel.showToPlayer(player)
                else it.activeModel.hideFromPlayer(player)
            }
        }
    }

    fun invisible(player: Player, visibility: Boolean) {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return warning(console().asLangText("player-error-model"))

        val active = modelEntity.models.values.iterator()

        while (active.hasNext()) {
            val activeModel = active.next()
            activeModel.let {
                if(visibility) it.showToPlayer(player)
                else it.hideFromPlayer(player)
            }
        }
    }

    private fun addModel(player: Player, model: String) {

        if(ModelEngineAPI.api.modelRegistry.getBlueprint(model) == null) {
            warning("模型未找到")
            return
        }
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId)

        val activeModel = ModelEngineAPI.createActiveModel(model)

        modelEntity.destroy()

        (modelEntity.rangeManager as RangeManager.Disguise).isIncludeSelf = true

        player.isInvisible = true
    }
}