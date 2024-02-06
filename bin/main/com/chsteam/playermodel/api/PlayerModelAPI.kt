package com.chsteam.playermodel.api

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.data.DatabaseLocal
import com.chsteam.playermodel.sequence.Sequence
import org.bukkit.entity.Player
import taboolib.common.platform.function.getDataFolder
import taboolib.module.kether.KetherTransfer.namespace
import taboolib.module.kether.Workspace
import java.io.File

object PlayerModelAPI {

    val workspace = Workspace(File(getDataFolder(), "script"), namespace = namespace)

    val sequence = HashMap<String, Sequence>()

    val progress = HashMap<Player, Int>()

    val cooldownStatus = HashMap<Player, Boolean>()

    val time = HashMap<Player,Long>()

    val moveLock = HashMap<Player, Boolean>()

    val modelPlayer = HashMap<Player, Boolean>()

    internal val database by lazy {
        when (val db = PlayerModel.conf.getString("Database.method")!!.uppercase()) {
            "LOCAL" -> DatabaseLocal()
            else -> {
                error("\"${PlayerModel.conf.getString("Database.method")}\" not supported.")
            }
        }
    }

}