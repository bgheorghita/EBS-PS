package uaic.fii.client.models;

public class MetaPublicationField {
    private final String key;
    private final String value;
    private final String operator;

    public MetaPublicationField(String key, String value, String operator) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "(" + key + "," + operator + "," + value + ')';
    }
}
