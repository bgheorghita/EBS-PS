package uaic.fii.models;

public class PublicationField {
    private String key;
    private String value;

    public PublicationField(){
        key = "";
        value = "";
    }

    public PublicationField(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + "," + value + ")";
    }
}
