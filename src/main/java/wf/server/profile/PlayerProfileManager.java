package wf.server.profile;

import com.google.common.annotations.VisibleForTesting;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileManager {
    private static HikariDataSource dataSource;
    private final Map<UUID, PlayerProfile> profiles = new HashMap<>();

    public PlayerProfileManager(Path dbFile) throws Exception {
        initDb(dbFile);
    }

    @VisibleForTesting
    public static void initDb(Path db) throws Exception {
        Files.createDirectories(db.getParent());
        // 配置 HikariCP 数据源
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + db);
        config.setMaximumPoolSize(10); // 最大连接数
        config.setMinimumIdle(2); // 最小空闲连接
        config.setIdleTimeout(30000); // 空闲连接超时时间
        config.setMaxLifetime(1800000); // 连接最大存活时间
        dataSource = new HikariDataSource(config);
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS proficiency (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        uid TEXT NOT NULL UNIQUE,
                        test INT NOT NULL);
                    """;
            stmt.execute(createTableSQL);
        }
    }

    public void loadProfile(UUID playerUid) throws Exception {
        String query = "SELECT uid, test FROM proficiency WHERE uid = ?";
        // 从数据库加载玩家数据
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerUid.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int proficiency = rs.getInt("test");
                    PlayerProfile profile = new PlayerProfile(playerUid, proficiency);
                    profiles.put(playerUid, profile);
                } else {
                    // 玩家数据不存在，初始化数据并插入
                    PlayerProfile newProfile = new PlayerProfile(playerUid, 0);
                    profiles.put(playerUid, newProfile);
                    saveProfile(newProfile);
                }
            }
        }
    }

    @VisibleForTesting
    public void saveProfile(PlayerProfile profile) throws Exception {
        String insertSQL = "INSERT INTO proficiency (uid, test) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, profile.getUuid().toString());
            stmt.setInt(2, profile.getProficiency());
            stmt.executeUpdate();
        }
    }
}
