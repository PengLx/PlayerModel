package com.chsteam.playermodel.command

import com.chsteam.playermodel.PlayerModel
import com.chsteam.playermodel.api.ApplyModel
import com.chsteam.playermodel.api.PlayAnimation
import com.chsteam.playermodel.data.Data
import com.chsteam.playermodel.sequence.SequenceLoader
import com.chsteam.playermodel.sequence.SequencePlay
import com.chsteam.playermodel.util.Model
import com.ticxo.modelengine.api.ModelEngineAPI
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.nms.ItemTagData
import taboolib.module.nms.getItemTag
import taboolib.module.nms.setItemTag
import taboolib.platform.util.isAir


@CommandHeader(name = "playermodel", aliases = ["modeler"], permission = "playermodel.admin")
internal object ModelCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { _, _, _ ->
            PlayerModel.reload()
            SequenceLoader.load()
        }
    }

    @CommandBody
    val playAnimationSequence = subCommand {
        dynamic(comment = "sequence") {
            dynamic(comment = "player", optional = true) {
                suggestion<Player> { _, _ ->
                    Bukkit.getOnlinePlayers().map { it.name }
                }
                execute<CommandSender> { _,context,_ ->
                    Bukkit.getPlayer(context.argument(0))?.let {
                        SequencePlay.playSequence(it, context.argument(-1))
                    }
                }
            }
            execute<Player> { sender, context, _ ->
                SequencePlay.playSequence(sender, context.argument(0))
            }
        }
    }

    @CommandBody
    val applyModel = subCommand {
        dynamic(comment = "player", optional = true) {
            suggestion<Player> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            execute<CommandSender> { _,context,_ ->
                Bukkit.getPlayer(context.argument(0))?.let {
                    ApplyModel.apply(it)
                }
            }
        }
        execute<Player> { sender, _,_ ->
            ApplyModel.apply(sender)
        }
    }

    @CommandBody
    val removeModel = subCommand {
        dynamic(comment = "player", optional = true) {
            suggestion<Player> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            execute<CommandSender> { _,context,_ ->
                Bukkit.getPlayer(context.argument(0))?.let {
                    ApplyModel.remove(it)
                }
            }
        }
        execute<Player> { sender, _,_ ->
            ApplyModel.remove(sender)
        }
    }

    @CommandBody
    val invisible = subCommand {
        dynamic(comment = "player", optional = true) {
            suggestion<Player> { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            dynamic(comment = "visibility", optional = true) {
                suggestion<Player>(uncheck = true) { _, _ ->
                    listOf("false", "true")
                }
                execute<CommandSender> { _, context, _ ->
                    Bukkit.getPlayer(context.argument(-1))?.let {
                        Model.invisible(it,context.argument(0).toBoolean())
                    }
                }
            }
            execute<CommandSender> { _, context, _ ->
                Bukkit.getPlayer(context.argument(0))?.let {
                    Model.invisible(it,false)
                }
            }
        }
        execute<Player> { sender, _, _ ->
            Model.invisible(sender,false)
        }
    }

    @CommandBody
    val addNBT = subCommand {
        dynamic(comment = "NBT") {
            execute<Player> { sender, context, _ ->
                if(!sender.inventory.itemInMainHand.isAir()) {
                    val item = sender.inventory.itemInMainHand
                    val tag = item.getItemTag()
                    tag["PLAYER_MODEL"] = ItemTagData(context.argument(0))
                    sender.inventory.setItemInMainHand(item.setItemTag(tag))
                }
            }
        }
    }

    @CommandBody
    val invisibleBody = subCommand {
        dynamic(comment = "body") {
            dynamic(comment = "player", optional = true) {
                suggestion<Player>(uncheck = true) { _, _ ->
                    Bukkit.getOnlinePlayers().map { it.name }
                }
                dynamic(comment = "visibility", optional = true) {
                    suggestion<Player>(uncheck = true ) { _, _  ->
                        listOf("false","true")
                    }
                    execute<CommandSender> { _, context, _ ->
                        Bukkit.getPlayer(context.argument(-1))?.let {
                            Model.invisibleBodyModel(it,context.argument(-2),context.argument(0).toBoolean())
                        }
                    }
                }
                execute<CommandSender> { _, context, _ ->
                    Bukkit.getPlayer(context.argument(0))?.let {
                        Model.invisibleBodyModel(it,context.argument(-1),false)
                    }
                }
            }
            execute<Player> { sender, context, _ ->
                Model.invisibleBodyModel(sender,context.argument(0),false)
            }
        }
    }

    @CommandBody
    val timelyModel = subCommand {
        dynamic(comment = "model") {
            dynamic(comment = "player", optional = true) {
                suggestion<Player>(uncheck = true) { _, _ ->
                    Bukkit.getOnlinePlayers().map { it.name }
                }
                execute<CommandSender> { _, context, _ ->
                    Bukkit.getPlayer(context.argument(0))?.let {
                        Model.timelyPlayerModel(it,context.argument(-1))
                    }
                }
            }
            execute<Player> { sender, context, _ ->
                Model.timelyPlayerModel(sender,context.argument(0))
            }
        }
    }

    @CommandBody
    val refresh = subCommand {
        dynamic(comment = "old model") {
            dynamic(comment = "new model") {
                execute<CommandSender> { _, context, _ ->
                    Data().updateModel(context.argument(-1),context.argument(0))
                }
            }
        }
    }

    @CommandBody
    val set = subCommand {
        dynamic(comment = "model") {
            dynamic(comment = "player", optional = true) {
                suggestion<Player>(uncheck = true) { _, _ ->
                    Bukkit.getOnlinePlayers().map { it.name }
                }
                execute<Player> { _, context,_ ->
                    Bukkit.getPlayer(context.argument(0))?.let { Data().set(it,context.argument(-1)) }
                }
            }
            execute<Player> { sender, context, _ ->
                Data().set(sender, context.argument(0))
            }
        }
    }

    @CommandBody
    val play = subCommand {
        dynamic(comment = "animation") {
            dynamic(comment = "player", optional = true) {
                suggestion<Player>(uncheck = true) { _, _ ->
                    Bukkit.getOnlinePlayers().map { it.name }
                }
                dynamic(comment = "ignore", optional = true) {
                    suggestion<Player>(uncheck = true) { _, _ ->
                        listOf("True","False")
                    }
                    dynamic(comment = "speed", optional = true) {
                        suggestion<Player>(uncheck = true) { _,_ ->
                            listOf("1")
                        }
                        dynamic(comment = "lerpin", optional = true) {
                            suggestion<Player>(uncheck = true) { _,_ ->
                                listOf("0")
                            }
                            dynamic(comment = "lerpout" , optional = true) {
                                suggestion<Player>(uncheck = true) { _,_ ->
                                    listOf("1")
                                }
                                execute<CommandSender> { _, context, _ ->
                                    PlayAnimation.playAnimation(
                                        ModelEngineAPI.getModeledEntity(Bukkit.getPlayer(context.argument(-4))?.player?.uniqueId),
                                        context.argument(-5),
                                        remove = false,
                                        context.argument(-2).toDouble(),
                                        context.argument(-1).toDouble(),
                                        context.argument(0).toDouble(),
                                        context.argument(-3).toBoolean()
                                    )
                                }
                            }
                            execute<CommandSender> { _, context, _ ->
                                PlayAnimation.playAnimation(
                                    ModelEngineAPI.getModeledEntity(Bukkit.getPlayer(context.argument(-3))?.player?.uniqueId),
                                    context.argument(-4),
                                    remove = false,
                                    context.argument(-1).toDouble(),
                                    context.argument(0).toDouble(),
                                    1.0,
                                    context.argument(-2).toBoolean()
                                )
                            }
                        }
                        execute<CommandSender> { _, context, _ ->
                            PlayAnimation.playAnimation(
                                ModelEngineAPI.getModeledEntity(Bukkit.getPlayer(context.argument(-3))?.player?.uniqueId),
                                context.argument(-2),
                                remove = false,
                                context.argument(0).toDouble(),
                                0.0,
                                1.0,
                                context.argument(-1).toBoolean()
                            )
                        }
                    }
                    execute<CommandSender> { _, context, _ ->
                        PlayAnimation.playAnimation(
                            ModelEngineAPI.getModeledEntity(Bukkit.getPlayer(context.argument(-1))?.player?.uniqueId),
                            context.argument(-2),
                            remove = false,
                            1.0,
                            0.0,
                            1.0,
                            context.argument(0).toBoolean()
                        )
                    }
                }
                execute<CommandSender> { _, context, _ ->
                    PlayAnimation.playAnimation(
                        ModelEngineAPI.getModeledEntity(Bukkit.getPlayer(context.argument(0))?.player?.uniqueId),
                        context.argument(-1),
                        remove = false,
                        1.0,
                        0.0,
                        1.0,
                        false,
                    )
                }
            }
            execute<Player> { sender, context, _ ->
                PlayAnimation.playAnimation(
                    ModelEngineAPI.getModeledEntity(sender.uniqueId),
                    context.argument(0),
                    remove = false,
                    1.0,
                    0.0,
                    1.0,
                    false,
                )
            }
        }
    }
}