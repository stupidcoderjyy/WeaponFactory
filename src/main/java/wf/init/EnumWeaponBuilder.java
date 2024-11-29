package wf.init;

import wf.init.builders.GenericSwordBuilder;

import java.util.function.Supplier;

public enum EnumWeaponBuilder {
    SWORD(GenericSwordBuilder::new)
    ;
    final String key;
    final Supplier<IWeaponBuilder<?>> builder;

    EnumWeaponBuilder(Supplier<IWeaponBuilder<?>> builder) {
        this.key = name().toLowerCase();
        this.builder = builder;
    }
}
