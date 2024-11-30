package wf.init;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public enum EnumParser {
    ATTACK_DISTANCE((builder, key, json) -> {
        ensurePrimitive(json, key);
        builder.setAttackDistance(json.getAsFloat());
    }),
    ATTACK_SPEED((builder, key, json) -> {
        ensurePrimitive(json, key);
        builder.setAttackSpeed(json.getAsFloat());
    }),
    ATTACK_DAMAGE((builder, key, json) -> {
        ensurePrimitive(json, key);
        builder.setAttackDamage(json.getAsInt());
    }),
    CRITICAL_RATE((builder, key, json) -> {
        ensurePrimitive(json, key);
        builder.setCriticalRate(json.getAsFloat());
    }),
    WEIGHT((builder, key, json) -> {
        ensurePrimitive(json, key);
        builder.setWeight(json.getAsInt());
    }),
    DURABILITY((builder, key, json) -> {
        ensurePrimitive(json, key);
        builder.setDurability(json.getAsInt());
    })
    ;
    final String key;
    final IParser parser;

    EnumParser(IParser parser) {
        this.key = name().toLowerCase();
        this.parser = parser;
    }

    public void parse(IWeaponBuilder<?> builder, JsonElement json) {
        parser.parse(builder, key, json);
    }

    private static void ensurePrimitive(JsonElement json, String key) {
        if (!json.isJsonPrimitive()) {
            invalidDataTypeError(key);
        }
    }

    private static void invalidDataTypeError(String key) {
        throw new JsonParseException("field '" + key + "' should be number");
    }

    @FunctionalInterface
    private interface IParser {
        void parse(IWeaponBuilder<?> builder, String key, JsonElement json);
    }
}
