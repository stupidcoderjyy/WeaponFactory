package wf.init;

import java.util.Collection;

public interface IWeapon {
    float getAttackDistance();
    float getCriticalRate();
    int getWeight();
    Collection<IAbility> getAbilities();
}
