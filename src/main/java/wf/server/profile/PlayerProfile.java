package wf.server.profile;

import java.util.UUID;

public class PlayerProfile {
    private final UUID uuid;
    private int proficiency;

    public PlayerProfile(UUID uuid, int proficiency) {
        this.uuid = uuid;
        this.proficiency = proficiency;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "PlayerProfile{" +
                "uuid=" + uuid +
                ", proficiency=" + proficiency +
                '}';
    }
}
