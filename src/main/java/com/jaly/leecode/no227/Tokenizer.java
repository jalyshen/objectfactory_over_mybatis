package com.jaly.leecode.no227;

import java.util.Stack;


/**
 * 字符串解析
 */
public class Tokenizer {

    /**
     * 分析表达式
     *
     * @param expression 用户输入的字符串表达式
     */
    public Stack<String> analysisExpression(String expression) {
        Stack<String> stack = new Stack();
        // 去掉所有空格
        expression = expression.replaceAll("\\s*", "");

        if (!CharacterUtil.validate(expression)) {
            throw new Error("您输入的算式表达式不符合题目要求。请重试～");
        }
        int length = expression.length();
        for (int index = 0; index < length; index++) {
            var c = String.valueOf(expression.charAt(index));
            if (index == 0 || !CharacterUtil.isNumber(c)) {
                stack.push(c);
            } else {
                // 如果是数字, 判断上一个是否是数字。如果是，合并，然后入栈
                var topInStack = stack.peek();
                if (topInStack != null && CharacterUtil.isNumber(topInStack)) {
                    topInStack = stack.pop();
                    var tmp = topInStack + c; // 字符串串连
                    stack.push(tmp);
                } else {
                    stack.push(c);
                }
            }
        }
        return stack;
    }

}
