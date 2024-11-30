package wf.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wf.init.Mod;
import wf.init.effects.EffectWeaponWeight;

import java.util.Collection;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow @Final private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "sendEffectToControllingPlayer", at = @At("HEAD"), cancellable = true)
    private void hookNoSyncEffectToClient(StatusEffectInstance effect, CallbackInfo ci) {
        if (effect.getEffectType() instanceof EffectWeaponWeight) {
            ci.cancel();
        }
    }


    @Inject(method = "getStatusEffects", at = @At("HEAD"))
    public void hookRemoveClientEffect(CallbackInfoReturnable<Collection<StatusEffectInstance>> cir) {
        if (getWorld().isClient) {
            activeStatusEffects.remove(Mod.EFFECT_WEAPON_WEIGHT);
        }
    }
}
