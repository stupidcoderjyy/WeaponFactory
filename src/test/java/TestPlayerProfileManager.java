import org.junit.Test;
import wf.server.profile.PlayerProfile;
import wf.server.profile.PlayerProfileManager;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class TestPlayerProfileManager {
    private static final Path DB_FILES = new File("C:\\Users\\JYY\\Desktop\\Minecraft\\commercial\\projects\\WeaponFactory").toPath();

    @Test
    public void testInitDb() throws Exception {
        PlayerProfileManager.initDb(DB_FILES.resolve("test.db"));
    }

    @Test
    public void testLoadPlayerProfile() throws Exception {
        var ppm = new PlayerProfileManager(DB_FILES.resolve("test.db"));
        var uid = UUID.randomUUID();
        ppm.saveProfile(new PlayerProfile(uid, 12));
        ppm.loadProfile(uid);
    }
}
