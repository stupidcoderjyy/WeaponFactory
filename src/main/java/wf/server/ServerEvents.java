package wf.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import wf.init.Mod;

public final class ServerEvents {
    public static void onServerAboutToStart(MinecraftServer server) {
        try {
            ((IModServer) server).set_server_data(ServerData.create(server));
        } catch (Exception e) {
            Mod.getLogger().fatal("failed to create mod server data", e);
            System.exit(0);
        }
    }

    public static void onPlayConnect(ServerPlayerEntity player) {
        ServerData d = ServerData.fromServer(player.getServer());
        try {
            d.manager().loadProfile(player.getUuid());
        } catch (Exception e) {
            Mod.getLogger().error("failed to load profile of player '{}': {}", player.getName(), e);
        }
        player.removeStatusEffect(Mod.EFFECT_WEAPON_WEIGHT);
    }
}
