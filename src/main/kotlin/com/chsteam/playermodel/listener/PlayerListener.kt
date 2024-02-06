package com.chsteam.playermodel.listener

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.animation.*
import com.chsteam.playermodel.api.ApplyModel
import com.chsteam.playermodel.api.PlayerModelAPI
import com.chsteam.playermodel.sequence.SequencePlay
import com.chsteam.playermodel.util.ChangeItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*
import org.bukkit.event.vehicle.VehicleEnterEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.info
import taboolib.platform.event.PlayerJumpEvent

object PlayerListener {

    @SubscribeEvent
    fun e(e: PlayerAnimationEvent) {
        if(e.animationType == PlayerAnimationType.ARM_SWING) {
            SequencePlay.play(e.player)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        if(PlayerModel.conf.getBoolean("Settings.join-apply")) {
            ApplyModel.apply(e.player)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        ApplyModel.remove(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerItemHeldEvent) {
        ChangeItem.changeItem(e.player,e.newSlot)
    }

    @SubscribeEvent
    fun e(e: PlayerSwapHandItemsEvent) {
        ChangeItem.exChangeItem(e.player, e.mainHandItem, e.offHandItem)
    }

    @SubscribeEvent
    fun e(e: PlayerToggleFlightEvent) {
        if(e.isFlying) {
            State.fly(e.player)
        } else {
            State.walk(e.player)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerToggleSprintEvent) {
        if(e.isSprinting) {
            if(!e.player.isFlying) {
                State.run(e.player)
            }
            if(e.player.isInWater) {
                State.swim(e.player)
            }
        } else {
            if(!e.player.isFlying) {
                State.walk(e.player)
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerToggleSneakEvent) {
        if(e.isSneaking) {
            if(!e.player.isFlying) {
                State.sneak(e.player)
            }
        } else {
            if(!e.player.isFlying) {
                State.walk(e.player)
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerJumpEvent) {
        PlayerModelAPI.moveLock[e.player]?.let {
            if(it) {
                e.isCancelled = true
                return
            }
        }
        Jump.play(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        Drop.play(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerRespawnEvent) {
        ApplyModel.respawn(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerDeathEvent) {
        Death.play(e.entity)
        ApplyModel.deathRemove(e.entity)
    }

    @SubscribeEvent
    fun e(e: PlayerBedEnterEvent) {
        EnterBed.play(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerBedLeaveEvent) {
        LeaveBed.play(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerBucketEmptyEvent) {
        BucketEmpty.play(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerBucketEntityEvent) {
        BucketEntity.play(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerBucketFillEvent) {
        BucketFill.play(e.player)
    }

    @SubscribeEvent
    fun e(e: VehicleEnterEvent) {
        if(e.entered is Player) {
            State.enterVehicle(e.entered as Player)
        }
    }

    @SubscribeEvent
    fun e(e: VehicleExitEvent) {
        if(e.exited is Player) {
            State.leaveVehicle(e.exited as Player)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        if(e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            e.item?.let {
                if(it.type == Material.FISHING_ROD) {
                    GoFishing.play(e.player)
                }
            }
        }
    }
}