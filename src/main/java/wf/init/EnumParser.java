package wf.init;

public enum EnumParser {
    ATTACK_DISTANCE(IParser.ATTACK_DISTANCE),
    ATTACK_SPEED(IParser.ATTACK_SPEED),
    ATTACK_DAMAGE(IParser.ATTACK_DAMAGE),
    CRITICAL_RATE(IParser.CRITICAL_RATE),
    WEIGHT(IParser.WEIGHT)
    ;
    final String key;
    final IParser parser;

    EnumParser(IParser parser) {
        this.key = name().toLowerCase();
        this.parser = parser;
    }
}
