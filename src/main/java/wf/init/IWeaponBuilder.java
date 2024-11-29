package wf.init;

import net.minecraft.item.Item;

public interface IWeaponBuilder<I extends Item> {
    void setAttackDistance(float distance);
    void setAttackSpeed(float speed);
    void setAttackDamage(int damage);
    void setCriticalRate(float rate);
    void setWeight(int weight);
    I build();
}
