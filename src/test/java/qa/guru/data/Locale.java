package qa.guru.data;

public enum Locale {
    DE("de"),
    EN("en");
    private final String desc;
    Locale(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return desc;
    }
}
