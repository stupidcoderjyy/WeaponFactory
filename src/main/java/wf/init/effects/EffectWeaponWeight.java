package wf.init.effects;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EffectWeaponWeight extends StatusEffect {
    private final Map<UUID, Multimap<EntityAttribute, EntityAttributeModifier>> modifiers = new HashMap<>();

    public EffectWeaponWeight(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient) {
            Optional.ofNullable(modifiers.remove(entity.getUuid())).ifPresent(attributes::removeModifiers);
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient) {
            double multiplier = (amplifier - 11) * 0.05;
            var modifier = new EntityAttributeModifier("daw", multiplier, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
            var set = ImmutableMultimap.of(EntityAttributes.GENERIC_MOVEMENT_SPEED, modifier);
            modifiers.put(entity.getUuid(), set);
            attributes.addTemporaryModifiers(set);
        }
    }
}
