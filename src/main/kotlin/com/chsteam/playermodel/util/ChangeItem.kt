package com.chsteam.playermodel.util

import com.chsteam.playermodel.PlayerModel
import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.model.bone.HandBone
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.isAir

object ChangeItem {

    fun changeItem(player: Player,newSlot: Int) {

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId)

        modelEntity.models.values.forEach {
            val rightHandModel = it.getBone(PlayerModel.conf.getString("Settings.right-hand")) ?: return

            val mainHandItem = player.inventory.getItem(newSlot)

            if (!mainHandItem.isAir()) {
                (rightHandModel as HandBone).itemStack = ItemBuilder(Material.AIR).build()
            } else {
                (rightHandModel as HandBone).itemStack = mainHandItem
            }
        }
    }

    fun exChangeItem(player: Player,mainHandItem : ItemStack?, offHandItem: ItemStack?){

        val modelEntity = ModelEngineAPI.getModeledEntity(player.uniqueId)

        modelEntity.models.values.forEach {
            val rightHandModel = it.getBone(PlayerModel.conf.getString("Settings.right-hand")) ?: return
            val leftHandModel = it.getBone(PlayerModel.conf.getString("Settings.left-hand")) ?: return

            if(mainHandItem.isAir) {
                (rightHandModel as HandBone).itemStack = ItemBuilder(Material.AIR).build()
            } else {
                (rightHandModel as HandBone).itemStack = mainHandItem
            }

            if(offHandItem.isAir) {
                (leftHandModel as HandBone).itemStack = ItemBuilder(Material.AIR).build()
            } else {
                (leftHandModel as HandBone).itemStack = mainHandItem
            }
        }
    }
}