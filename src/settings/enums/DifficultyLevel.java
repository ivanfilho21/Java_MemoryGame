package settings.enums;

public enum DifficultyLevel {
    EASY("0"), MEDIUM("1"), HARD("2");

    private final String value;

    DifficultyLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
