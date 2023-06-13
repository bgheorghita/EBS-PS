package uaic.fii.generators;

import uaic.fii.models.Operator;

import java.util.HashMap;
import java.util.Map;

public class OperatorGenerator {
    private static Map<Integer, String> operators;

    static {
        initOperators();
    }

    private static void initOperators(){
        operators = new HashMap<>();
        operators.put(1, Operator.LESS.getOperator());
        operators.put(2, Operator.GREATER.getOperator());
        operators.put(3, Operator.EQUAL.getOperator());
        operators.put(4, Operator.LESS_OR_EQUAL.getOperator());
        operators.put(5, Operator.GREATER_OR_EQUAL.getOperator());
        operators.put(6, Operator.DIFFERENT.getOperator());
    }

    public static String getRandomOperator(){
        int operatorMapId = NumberGenerator.getRandomInt(1, operators.size());
        return operators.get(operatorMapId);
    }

    public static String getRandomBetweenEqualAndDifferentOperator(){
        int random = NumberGenerator.getRandomInt(0, 1);
        return random == 0 ? Operator.EQUAL.getOperator() : Operator.DIFFERENT.getOperator();
    }
}
