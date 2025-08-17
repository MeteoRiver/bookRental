package common;

public enum Tag {
    General("000","총류"),
    PHILOSOPHY("100", "철학"),
    RELIGION("200", "종교"),
    SOCIAL_SCIENCE("300", "사회과학"),
    NATURAL_SCIENCE("400", "자연과학"),
    TECHNOLOGY("500", "기술과학"),
    ART("600", "예술"),
    LANGUAGE("700", "언어"),
    LITERATURE("800", "문학"),
    Histroy("900", "역사");

    private final String code;
    private final String tag;

    Tag(String code, String tag) {
        this.code = code;
        this.tag = tag;
    }
    public String getCode() {
        return code;
    }
    public String getTag() {
        return tag;
    }
    public String getAll(){
        return code + " " + tag;
    }
}