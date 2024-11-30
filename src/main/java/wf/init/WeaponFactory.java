package wf.init;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WeaponFactory {
    private final Map<String, EnumParser> parsers = new HashMap<>();
    private final Map<String, Supplier<IWeaponBuilder<?>>> itemBuilders = new HashMap<>();
    private final Map<Identifier, Item> items = new HashMap<>();

    static void init(Path path) {
        var wf = new WeaponFactory();
        wf.initBuilders();
        wf.parse(path.resolve("data").resolve(Mod.MOD_ID).resolve("weapons"));
        wf.register();
    }

    private void parse(Path path) {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.filter(p -> p.toString().endsWith(".json") && Files.isRegularFile(p)).forEach(this::parseSingle);
        } catch (Exception e) {
            LogManager.getLogger().error("failed to read weapon files: {}", e.getMessage());
        }
    }

    private void parseSingle(Path p) {
        JsonObject json;
        try {
            json = JsonParser.parseReader(new FileReader(p.toFile())).getAsJsonObject();
        } catch (Exception e) {
            LogManager.getLogger().error("failed to read weapon file: {} | reason: {}", p.getFileName(), e);
            return;
        }
        Identifier id;
        try {
            if (!json.has("id")) {
                throw new JsonParseException("missing entry 'id'");
            }
            try {
                id = new Identifier(json.get("id").getAsString());
            } catch (Exception e) {
                throw new JsonParseException("invalid id: " + json.get("id"));
            }
        } catch (Exception e) {
            LogManager.getLogger().error("failed to parse weapon type: {}", e.getMessage());
            return;
        }
        try {
            var item = buildItem(id, json);
            items.put(id, item);
        } catch (Exception e) {
            LogManager.getLogger().error("failed to parse weapon: {} | reason: {}", id, e.getMessage());
        }
    }

    private Item buildItem(Identifier loc, JsonObject json) {
        var e = json.get("type");
        if (e == null) {
            throw new JsonParseException("missing entry 'type'");
        }
        if (items.containsKey(loc)) {
            throw new JsonParseException("id '" + loc + "' already exists");
        }
        var type = e.getAsString();
        var builder = Optional.ofNullable(itemBuilders.get(type))
                .orElseThrow(() -> new JsonParseException("unknown weapon category '" + type + "'"))
                .get();
        json.asMap().forEach((k, child) ->
                Optional.ofNullable(parsers.get(k)).ifPresent(p -> p.parse(builder, child)));
        return builder.build();
    }

    private void initBuilders() {
        Arrays.stream(EnumParser.values()).forEach(p -> parsers.put(p.key, p));
        Arrays.stream(EnumWeaponBuilder.values()).forEach(p -> itemBuilders.put(p.key, p.builder));
    }

    private void register() {
        items.forEach((loc, i) -> Registry.register(Registries.ITEM, loc, i));
    }
}
