package com.exostar.userprofile.constant;

import java.util.Arrays;

public enum OperatorEnum {
    lt("<"),
    le("<="),
    eq("="),
    gt(">"),
    ge(">=");

    public String operator;

    OperatorEnum(String operator) {
        this.operator = operator;
    }

    public static OperatorEnum fromName(String name){
        return Arrays.stream(values()).filter(val-> val.name().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
