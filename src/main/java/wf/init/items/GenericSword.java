package wf.init.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import wf.init.IAbility;
import wf.init.IWeapon;

import java.util.Collection;

public class GenericSword extends SwordItem implements IWeapon {
    private final float attackDistance;
    private final float criticalRate;
    private final int weight;
    private final Collection<IAbility> abilities;

    public GenericSword(ToolMaterial toolMaterial, Settings settings, Collection<IAbility> abilities,
                        int attackDamage, float attackSpeed, float attackDistance,
                        float criticalRate, int weight) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.attackDistance = attackDistance;
        this.criticalRate = criticalRate;
        this.weight = weight;
        this.abilities = abilities;
    }

    @Override
    public float getAttackDistance() {
        return attackDistance;
    }

    @Override
    public float getCriticalRate() {
        return criticalRate;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public Collection<IAbility> getAbilities() {
        return abilities;
    }
}
