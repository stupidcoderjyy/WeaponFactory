package wf.init;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wf.init.effects.EffectWeaponWeight;

public class Mod implements ModInitializer {
    public static final String MOD_ID = "wf";
    public static final StatusEffect EFFECT_WEAPON_WEIGHT = new EffectWeaponWeight(StatusEffectCategory.NEUTRAL, 0);

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(
                mod -> WeaponFactory.init(mod.getRootPaths().get(0)));
        Registry.register(Registries.STATUS_EFFECT, Mod.modLoc("weapon_weight"), EFFECT_WEAPON_WEIGHT);
    }

    public static Identifier modLoc(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static Logger getLogger() {
        return LogManager.getLogger(MOD_ID);
    }
}
