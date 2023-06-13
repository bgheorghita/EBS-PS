package uaic.fii.models;

public enum Operator {
    EQUAL("="),
    LESS("<"),
    GREATER(">"),
    LESS_OR_EQUAL("<="),
    GREATER_OR_EQUAL(">="),
    DIFFERENT("!=");

    private final String operator;

    Operator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return operator;
    }

}
