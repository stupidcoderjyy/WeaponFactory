package wf.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wf.init.IWeapon;
import wf.init.Mod;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    @Shadow public abstract boolean isPlayer();

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack", at = @At(value = "STORE", ordinal = 3), name = "f")
    private float hookApplyCriticalHit(float f) {
        if (getMainHandStack().getItem() instanceof IWeapon w && random.nextFloat() < w.getCriticalRate()) {
            return MathHelper.ceil(f * 1.5f);
        }
        return f;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void hookHandleWeaponEffect(CallbackInfo ci) {
        if (getWorld().isClient) {
            return;
        }
        var player = (PlayerEntity) (Object) this;
        var eff = player.getStatusEffect(Mod.EFFECT_WEAPON_WEIGHT);
        if (player.getMainHandStack().getItem() instanceof IWeapon w) {
            int amp = 11 + w.getWeight();
            if (eff == null || eff.getAmplifier() != amp) {
                player.removeStatusEffect(Mod.EFFECT_WEAPON_WEIGHT);
                player.addStatusEffect(new StatusEffectInstance(Mod.EFFECT_WEAPON_WEIGHT, -1, amp, false, false, false));
            }
        } else if (eff != null) {
            player.removeStatusEffect(Mod.EFFECT_WEAPON_WEIGHT);
        }
    }
}
