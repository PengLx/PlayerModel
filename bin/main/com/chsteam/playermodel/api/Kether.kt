package com.chsteam.playermodel.api

import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptCommandSender
import taboolib.common.platform.function.console
import taboolib.module.kether.ScriptContext
import taboolib.module.kether.printKetherErrorMessage
import taboolib.module.lang.sendLang

object Kether {
    val workspace = PlayerModelAPI.workspace
    fun run(player: Player?, file: String) {
        val script = workspace.scripts[file]
        if(script!=null) {
            val context = ScriptContext.create(script)
            player?.let {
                context.sender = adaptCommandSender(player)
            }
            try {
                workspace.runScript(file, context)
            } catch (t: Throwable) {
                t.printKetherErrorMessage()
            }
        }
        else {
            console().sendLang("command-script-not-found")
        }
    }
}