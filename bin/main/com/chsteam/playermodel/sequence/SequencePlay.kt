package com.chsteam.playermodel.sequence

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.api.PlayAnimation
import com.chsteam.playermodel.api.PlayerModelAPI
import com.ticxo.modelengine.api.ModelEngineAPI
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import com.chsteam.playermodel.api.Kether
import taboolib.module.nms.getItemTag
import taboolib.platform.util.isAir
object SequencePlay {
    fun play(player: Player) {

        var key = PlayerModel.conf.getString("Settings.arm")

        /**
         * 非空手状态检测物品识别词
         */
        if(!player.inventory.itemInMainHand.isAir()) {
            val item = player.inventory.itemInMainHand
            val itemMode = PlayerModel.conf.getString("Settings.item")?.uppercase()

            if(itemMode == "NBT") {
                key = item.getItemTag()["PLAYER_MODEL"]?.asString()
            }
            else if(itemMode == "MMOITEMS") {
                key = item.getItemTag()["MMOITEMS_ITEM_TYPE"]?.asString()
            }
        }

        PlayerModelAPI.sequence.forEach { (id, sequence) ->
            if(key != sequence.root.getString("distinguish")) return@forEach
            realPlay(player,sequence)
        }
    }

    fun playSequence(player: Player, key: String) {

        PlayerModelAPI.sequence.forEach { (id, sequence) ->
            if(key != id) return@forEach
            realPlay(player,sequence)
        }
    }

    private fun realPlay(player: Player, sequence: Sequence) {
        val cooldown = sequence.root.getDouble("cooldown")
        val stateSequence = sequence.root.getMapList("animation")

        /**
         * 如果没有攻击动画进程就初始化为0
         */
        var animationInt = PlayerModelAPI.progress.getOrDefault(player,0)

        /**
         * 如果找不到当前动画就调攻击动画进程为0
         */
        if(animationInt+1 > stateSequence.size) {
            PlayerModelAPI.progress[player] = 0
            animationInt = 0
        }

        /**
         * 如果有攻击时间的记录
         * 判断记录的时间与本次时间的差距
         * 大于cooldown就调攻击动画进程为0
         */
        PlayerModelAPI.time[player]?.let {
            if((System.currentTimeMillis() - it)/1000 > cooldown) {
                PlayerModelAPI.progress[player] = 0
                animationInt = 0
            }
        }

        val animation = stateSequence[animationInt]

        val state = animation["key"].toString()
        var coolTime = animation["cooldown"].toString().toDouble()
        var speed = false
        var moveLock = false

        /**
         * 一些动画播放标准
         */
        var lerpin = 0.0
        var lerpout = 1.0
        var ingorelerp = false
        var remove = false
        lateinit var kether : String
        var ketherBool = false

        animation["move-lock"]?.let {
            moveLock = it.toString().toBoolean()
        }

        animation["lerpin"]?.let {
            lerpin = it.toString().toDouble()
        }

        animation["lerpout"]?.let {
            lerpout = it.toString().toDouble()
        }

        animation["ingorelerp"]?.let {
            ingorelerp = it.toString().toBoolean()
        }

        animation["remove"]?.let {
            remove = it.toString().toBoolean()
        }

        sequence.root.getBoolean("speed")?.let{
            speed = it
        }

        animation["kether"]?.let {
            kether = it.toString()
            ketherBool = true
        }

        var playSpeed = 1.0

        if(speed && animation["power"]!=null) {
            playSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)!!.value * animation["power"].toString().toDouble()

            coolTime *= player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)!!.value * animation["power"].toString().toDouble()
        }

        /**
         * 如果攻击状态为null则初始化攻击状态
         */
        if(PlayerModelAPI.cooldownStatus[player] == null) {
            PlayerModelAPI.cooldownStatus[player] = true
        }

        /**
         * 如果攻击状态为可以攻击则执行以下内容
         */
        if(PlayerModelAPI.cooldownStatus[player] == true) {
            val entity = ModelEngineAPI.getModeledEntity(player.uniqueId) ?: return
            PlayAnimation.playAnimation(entity, state,remove, playSpeed, lerpin, lerpout, ingorelerp)
            PlayerModelAPI.cooldownStatus[player] = false

            animationInt.let {
                PlayerModelAPI.progress[player] = animationInt+1
            }

            val walkSpeed = player.walkSpeed

            if(moveLock) {
                player.walkSpeed = 0.0F
                PlayerModelAPI.moveLock[player] = true
            }
            if(ketherBool) {
                Kether.run(player, kether)
            }
            object: BukkitRunnable() {
                override fun run() {
                    PlayerModelAPI.cooldownStatus[player] = true
                    player.walkSpeed = walkSpeed
                    PlayerModelAPI.moveLock[player] = false
                }
            }.runTaskLater(PlayerModel.plugin, coolTime.toLong()*20)
            PlayerModelAPI.time[player] = System.currentTimeMillis()
            return
        } else return
    }
}