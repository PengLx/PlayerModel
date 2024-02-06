package com.chsteam.playermodel.animation

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.api.DefaultState
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.animation.state.DefaultStateHandler
import com.ticxo.modelengine.api.animation.state.ModelState
import com.ticxo.modelengine.api.model.ActiveModel
import org.bukkit.entity.Player

object State: DefaultState {

    override fun fly(player: Player): Boolean {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val state = PlayerModel.conf.getString("Settings.fly") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()
            if(activeModel.defaultStateHandler.getProperty(ModelState.WALK).animation != state) {
                activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(state, 1.0, 0.0, 1.0))
            }
        }
        return true
    }

    override fun run(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val state = PlayerModel.conf.getString("Settings.run") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()

            if (activeModel.defaultStateHandler.getProperty(ModelState.WALK).animation != state) {
                activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(state, 1.0, 0.0, 1.0))
            }
        }
        return true
    }

    override fun swim(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val state = PlayerModel.conf.getString("Settings.swim") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()
            if(activeModel.defaultStateHandler.getProperty(ModelState.WALK).animation != state) {
                activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(state, 1.0, 0.0, 1.0))
            }
        }
        return true
    }

    override fun sneak(player: Player): Boolean {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val state = PlayerModel.conf.getString("Settings.sneak") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()
            if(activeModel.defaultStateHandler.getProperty(ModelState.WALK).animation != state) {
                activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(state, 1.0, 0.0, 1.0))
            }
        }
        return true
    }

    override fun walk(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val state = PlayerModel.conf.getString("Settings.walk") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()
            if(activeModel.defaultStateHandler.getProperty(ModelState.WALK).animation != state) {
                activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(state, 1.0, 0.0, 1.0))
            }
        }
        return true
    }

    override fun enterVehicle(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val state = PlayerModel.conf.getString("Settings.vehicle-IDLE") ?: return false
        val move = PlayerModel.conf.getString("Settings.vehicle-move") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()
            activeModel.defaultStateHandler.setProperty(ModelState.IDLE, DefaultStateHandler.Property(state, 1.0, 0.0, 1.0))
            activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(move, 1.0, 0.0, 1.0))
        }
        return true
    }

    override fun leaveVehicle(player: Player): Boolean {
        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return false
        val active = modelEntity.models.values.iterator()

        val move = PlayerModel.conf.getString("Settings.walk") ?: return false

        while (active.hasNext()) {
            val activeModel = active.next()
            activeModel.defaultStateHandler.setProperty(ModelState.IDLE, DefaultStateHandler.Property("idle", 1.0, 0.0, 1.0))
            activeModel.defaultStateHandler.setProperty(ModelState.WALK, DefaultStateHandler.Property(move, 1.0, 0.0, 1.0))
        }
        return true
    }
}