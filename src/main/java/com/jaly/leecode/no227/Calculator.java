package com.jaly.leecode.no227;

import java.util.Scanner;
import java.util.Stack;

/**
 * 力扣 227. 基本计算器 II
 * https://www.toutiao.com/a6945352509716267532/
 *
 * 题目描述
 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
 * 整数除法仅保留整数部分。
 *
 * 示例 1：
 * 输入：s = "3+2*2"
 * 输出：7
 *
 * 示例 2：
 * 输入：s = " 3/2 "
 * 输出：1
 *
 * 示例 3：
 * 输入：s = " 3+5 / 2 "
 * 输出：5
 *
 * 提示：
 *   1 <= s.length <= 3 * 10^5
 *   s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开
 *   s 表示一个 有效表达式
 *   表达式中的所有整数都是非负整数，且在范围 [0, 2^31 - 1] 内
 *   题目数据保证答案是一个 32-bit 整数
 */
public class Calculator {

    private Tokenizer tokenizer;
    private Stack<String> stack;

    public Calculator(){
        tokenizer = new Tokenizer();
    }

    /**
     * 计算器
     * @param expression
     */
    public int calculate(String expression) {
        int result = 0;
        stack = tokenizer.analysisExpression(expression);
        return analysisAndOperator();
    }

    /**
     * 此时乘除法已经算完了 栈里只有加减法和数字了
     */
    private int analysisAndOperator() {
        handleHigeOperators();
        return handleLowOperators();
    }

    private void handleHigeOperators(){
        Stack<String> newStack = new Stack<>();
        var firstEle = stack.pop();
        if (!CharacterUtil.isNumber(firstEle)) {
            throw new Error("The expression is error");
        }
        newStack.push(firstEle);
        while (!stack.isEmpty()) {
            var element = stack.pop();
            if (!CharacterUtil.isNumber(element)) {
                if (Operator.of(element) == Operator.DIVIDE ||
                    Operator.of(element) == Operator.MULTIPLY) {
                    var element2 = stack.pop();
                    newStack.push("" + doOperation(newStack.pop(), element2, Operator.of(element)));
                } else {
                    newStack.push(element);
                }
            } else {
                newStack.push(element);
            }
        }
        this.stack = newStack;
    }

    private int handleLowOperators() {
        while (stack.size() >= 3 && stack.peek() != null) {
            var last = stack.pop();
            if (CharacterUtil.isNumber(last)) {
                var operator = stack.pop();
                    var secondNumber = stack.pop();
                    stack.push(""+doOperation(last, secondNumber, Operator.of(operator)));
            } else {
                continue;
            }
        }
        if (stack.size() == 1) {
            return Integer.valueOf(stack.pop());
        } else {
            return 0;
        }
    }

    /**
     * 　真正运算
     * @param fstNum
     * @param secNum
     * @param operator
     * @return
     */
    private int doOperation(String fstNum, String secNum, Operator operator) {
        var iFirNum = Integer.valueOf(fstNum);
        var iSecNum = Integer.valueOf(secNum);
       switch (operator) {
       case ADD:
           return iFirNum + iSecNum;
       case SUBTRACT:
           return iFirNum - iSecNum;
       case MULTIPLY:
           return iFirNum * iSecNum;
       case DIVIDE:
           return iFirNum / iSecNum;
       default:
           return 0;
       }
    }


    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要计算的算式 (输入0，结束计算):");
        String expression = sc.nextLine();  //读取字符串型输入
        while (!expression.trim().equals("0")) {
            System.out.println("=" + calculator.calculate(expression));
            System.out.println("请输入要计算的算式 (输入0，结束计算) -> ");
            expression = sc.nextLine();  //读取字符串型输入
        }
    }

}
