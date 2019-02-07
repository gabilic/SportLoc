package hr.foi.air.sportloc.view.util;

public enum SearchSettingsEnum {
    FILTER_SEARCH("hr.foi.air.filtersearch.FilterSearchImpl", "Filter Search"),
    TEXT_SEARCH("hr.foi.air.textsearch.TextSearchImpl", "Text Search"),
    FEELING_LUCKY_SEARCH("hr.foi.air.feelingluckysearch.FeelingLuckySearchImpl", "Feeling Lucky Search");

    private final String path;
    private final String name;

    SearchSettingsEnum(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public static SearchSettingsEnum getByName(String name) {
        for (SearchSettingsEnum en : SearchSettingsEnum.values()) {
            if (en.name.equals(name)) {
                return en;
            }
        }
        return FILTER_SEARCH;
    }

    @Override
    public String toString() {
        return name;
    }
}
