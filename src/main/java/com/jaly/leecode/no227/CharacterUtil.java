package com.jaly.leecode.no227;

import java.util.regex.Pattern;

public class CharacterUtil {

    /**
     * 检查表达式的合法性
     * @param expression 用户输入的字符串表达式
     * @return
     */
    public static boolean validate(String expression) {
        String REGEX = "^(\\d+)([-+\\*/]\\d+)+";
        Pattern pattern = Pattern.compile(REGEX);
        return pattern.matcher(expression).matches();
    }

    /**
     * 判断是否是数字
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        String REGEX = "^\\d+";
        Pattern p = Pattern.compile(REGEX);
        return p.matcher(s).matches();
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String expression = "3+5/2+2-9*3/2";
        System.out.println(CharacterUtil.validate(expression));
        System.out.println(CharacterUtil.isNumber("3"));
    }
}
