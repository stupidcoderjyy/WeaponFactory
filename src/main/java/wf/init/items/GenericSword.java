package wf.init.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import wf.init.IWeapon;

public class GenericSword extends SwordItem implements IWeapon {
    private final float attackDistance;
    private final float criticalRate;
    private final int weight;

    public GenericSword(ToolMaterial toolMaterial, Settings settings, int attackDamage, float attackSpeed,
                        float attackDistance, float criticalRate, int weight) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.attackDistance = attackDistance;
        this.criticalRate = criticalRate;
        this.weight = weight;
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
}
