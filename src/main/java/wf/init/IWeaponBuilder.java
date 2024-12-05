package wf.init;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface IWeaponBuilder<I extends Item> {
    void setAttackDistance(float distance);
    void setAttackSpeed(float speed);
    void setAttackDamage(int damage);
    void setCriticalRate(float rate);
    void setWeight(int weight);
    void setDurability(int durability);
    void addAbility(Identifier id, IAbility ability);
    I build();
}
