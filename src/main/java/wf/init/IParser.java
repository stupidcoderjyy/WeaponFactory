package wf.init;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@FunctionalInterface
public interface IParser {
    void parse(IWeaponBuilder<?> builder, JsonElement json);

    IParser ATTACK_DISTANCE = (builder, json) -> {
        ensurePrimitive(json, EnumParser.ATTACK_DISTANCE);
        builder.setAttackDistance(json.getAsFloat());
    };

    IParser ATTACK_SPEED = (builder, json) -> {
        ensurePrimitive(json, EnumParser.ATTACK_SPEED);
        builder.setAttackSpeed(json.getAsFloat());
    };

    IParser ATTACK_DAMAGE = (builder, json) -> {
        ensurePrimitive(json, EnumParser.ATTACK_DAMAGE);
        builder.setAttackDamage(json.getAsInt());
    };

    IParser CRITICAL_RATE = (builder, json) -> {
        ensurePrimitive(json, EnumParser.CRITICAL_RATE);
        builder.setCriticalRate(json.getAsFloat());
    };

    IParser WEIGHT = (builder, json) -> {
        ensurePrimitive(json, EnumParser.WEIGHT);
        builder.setWeight(json.getAsInt());
    };

    static void ensurePrimitive(JsonElement json, EnumParser parser) {
        if (!json.isJsonPrimitive()) {
            invalidDataTypeError(parser);
        }
    }

    static void invalidDataTypeError(EnumParser parser) {
        throw new JsonParseException("field '" + parser.key + "' should be number");
    }
}
