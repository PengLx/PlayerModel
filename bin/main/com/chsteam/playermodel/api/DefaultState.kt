package com.chsteam.playermodel.api

import org.bukkit.entity.Player

interface DefaultState {

    fun walk(player: Player) : Boolean

    fun run(player: Player) : Boolean

    fun sneak(player: Player) : Boolean

    fun fly(player: Player) : Boolean

    fun enterVehicle(player: Player) : Boolean

    fun leaveVehicle(player: Player) : Boolean

    fun swim(player: Player) : Boolean
}