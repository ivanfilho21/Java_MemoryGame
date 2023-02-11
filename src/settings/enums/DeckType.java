package settings.enums;

public enum DeckType {
    YUGI("0"), KAIBA("1"), JOEY("2"), PEGASUS("3"), STAPLE("4");
    private final String value;

    DeckType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
