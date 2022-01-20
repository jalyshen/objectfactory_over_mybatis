package com.jaly.leecode.no227;

import java.util.Map;

/**
 * 定义运算符
 */
public enum Operator {

    // 加 减 乘 除
    ADD("+", 1),
    SUBTRACT("-", 1),
    MULTIPLY("*", 2),
    DIVIDE("/", 2);

    private String alias;
    private int priority;

    private static Map<String, Operator> innalMap = Map.of("+", ADD,
                                                           "-", SUBTRACT,
                                                           "*", MULTIPLY,
                                                           "/", DIVIDE);

    private Operator(String alias, int priority) {
        this.alias = alias;
        this.priority = priority;
    }

    /**
     * 字符串换取操作符
     * @param alias
     * @return
     */
    public static Operator of(String alias) {
        return innalMap.get(alias) == null ? null: innalMap.get(alias);
    }

    /**
     * 判断一个符号是否是操作符
     * @param symbol
     * @return
     */
    public static boolean isOperator(String symbol) {
        return innalMap.get(symbol) != null;
    }
}
