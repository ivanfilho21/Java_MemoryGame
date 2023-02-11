package settings.enums;

public enum AnimationSpeed {
    NONE("0"), SLOW("1"), NORMAL("2"), FAST("3");

    private final String value;

    AnimationSpeed(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
