package wf.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import wf.init.Mod;
import wf.server.profile.PlayerProfileManager;

import java.nio.file.Path;

public record ServerData(MinecraftServer server, PlayerProfileManager manager) {

    static ServerData create(MinecraftServer server) throws Exception {
        Path dbPath = server.getSavePath(WorldSavePath.ROOT)
                .resolve("data")
                .resolve(Mod.MOD_ID)
                .resolve("profiles")
                .resolve("proficiency.db");
        PlayerProfileManager ppm = new PlayerProfileManager(dbPath);
        return new ServerData(server, ppm);
    }

    public static ServerData fromServer(MinecraftServer server) {
        return ((IModServer) server).get_server_data();
    }
}
