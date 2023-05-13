package dev.josantonius.minecraft.unduping

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockCanBuildEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {

    override fun onEnable() {
        load()
    }

    @EventHandler
    fun onBlockCanBuild(event: BlockCanBuildEvent) {
        val placedBlock = event.block
        val blockBelow = placedBlock.getRelative(BlockFace.DOWN)
        if (isBed(event.material) && isCrop(blockBelow.type)) {
            event.isBuildable = false
            event.player?.sendMessage("§cYou can't do that!")
            sendAlertToOps(event.player?.name)
        }
    }

    fun load() {
        server.pluginManager.registerEvents(this, this)
    }

    private fun sendAlertToOps(playerName: String?) {
        if (playerName == null) return
        val msg = "§f$playerName §cmight be using the bed and crops bug"
        logger.info(msg)
        Bukkit.getOnlinePlayers().forEach { player ->
            if (player.isOp) {
                player.sendMessage(msg)
            }
        }
    }

    private fun isCrop(material: Material): Boolean {
        return material == Material.WHEAT ||
                material == Material.CARROTS ||
                material == Material.POTATOES ||
                material == Material.BEETROOTS ||
                material == Material.MELON_STEM ||
                material == Material.PUMPKIN_STEM ||
                material == Material.NETHER_WART ||
                material == Material.SWEET_BERRY_BUSH ||
                material == Material.COCOA ||
                material == Material.CAVE_VINES ||
                material == Material.GLOW_BERRIES
    }

    private fun isBed(material: Material): Boolean {
        return material == Material.WHITE_BED ||
                material == Material.ORANGE_BED ||
                material == Material.MAGENTA_BED ||
                material == Material.LIGHT_BLUE_BED ||
                material == Material.YELLOW_BED ||
                material == Material.LIME_BED ||
                material == Material.PINK_BED ||
                material == Material.GRAY_BED ||
                material == Material.LIGHT_GRAY_BED ||
                material == Material.CYAN_BED ||
                material == Material.PURPLE_BED ||
                material == Material.BLUE_BED ||
                material == Material.BROWN_BED ||
                material == Material.GREEN_BED ||
                material == Material.RED_BED ||
                material == Material.BLACK_BED
    }
}
