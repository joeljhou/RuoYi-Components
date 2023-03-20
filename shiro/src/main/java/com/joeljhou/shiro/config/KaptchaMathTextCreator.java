package com.joeljhou.shiro.config;

import com.google.code.kaptcha.text.TextProducer;

import java.util.Random;

/**
 * 算数验证码文本生成器
 */
public class KaptchaMathTextCreator implements TextProducer {

    //加减乘除
    private final static Character[] OPERATORS = {'+', '-', '*', '/'};
    private final static String CODE_FORMAT = "%d %c %d = ?@%d";

    @Override
    public String getText() {
        // 自定义生成验证码文本的逻辑
        Integer result = 0;
        Random random = new Random();
        int x = random.nextInt(10); // 生成0~9之间的随机数
        int y = random.nextInt(10);
        Character operator = OPERATORS[random.nextInt(OPERATORS.length)];
        switch (operator) {
            case '+':
                result = x + y;
                break;
            case '-':
                if (x >= y) {
                    result = x - y;
                } else {
                    result = y - x;
                }
                break;
            case '*':
                result = x * y;
                break;
            case '/':
                // 除数不能为0
                while (y == 0) {
                    y = random.nextInt(10);
                }
                // 不能有余数
                if (x % y != 0) {
                    while (x % y != 0) {
                        x = random.nextInt(10);
                    }
                }
                result = x / y;
                break;
        }
        return String.format(CODE_FORMAT, x, operator, y, result);
    }


}
