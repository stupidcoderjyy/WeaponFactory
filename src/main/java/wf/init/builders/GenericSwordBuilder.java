package wf.init.builders;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.MathHelper;
import wf.init.IWeaponBuilder;
import wf.init.items.GenericSword;

public class GenericSwordBuilder implements IWeaponBuilder<GenericSword> {
    private static final ToolMaterial EMPTY = new EmptyMaterial();
    private float attackSpeed = -2.4f;
    private int attackDamage = 1;
    private float attackDistance = 4.5f;
    private float criticalRate = 0.2f;
    private int weight = 1;

    @Override
    public void setAttackDistance(float distance) {
        this.attackDistance = MathHelper.clamp(distance, 2, 10);
    }

    @Override
    public void setAttackSpeed(float speed) {
        this.attackSpeed = speed;
    }

    @Override
    public void setAttackDamage(int damage) {
        this.attackDamage = damage - 1;
    }

    @Override
    public void setCriticalRate(float rate) {
        this.criticalRate = MathHelper.clamp(rate, 0, 1.0f);
    }

    @Override
    public void setWeight(int weight) {
        this.weight = MathHelper.clamp(weight, -10, 10);
    }

    @Override
    public GenericSword build() {
        return new GenericSword(EMPTY, attackDamage, attackSpeed, attackDistance, criticalRate, weight);
    }

    private static class EmptyMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 0;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 0;
        }

        @Override
        public float getAttackDamage() {
            return 0;
        }

        @Override
        public int getMiningLevel() {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.empty();
        }
    }
}
