package com.onestep.util;

import org.thymeleaf.util.StringUtils;


public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_FAIL = 500;

    public static Result generateSuccessResult() {
        Result result = new Result();
        result.setCode(CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static Result generateSuccessResult(String message) {
        Result result = new Result();
        result.setCode(CODE_SUCCESS);
        if (StringUtils.isEmpty(message)){
            result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        }
        result.setMessage(message);
        return result;
    }

    public static Result generateSuccessResult(Object data) {
        Result result = new Result();
        result.setCode(CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result generateFailResult(String message) {
        Result result = new Result();
        result.setCode(CODE_FAIL);
        if (StringUtils.isEmpty(message)) {
            result.setMessage(DEFAULT_FAIL_MESSAGE);
        } else {
            result.setMessage(message);
        }
        return result;
    }

    public static Result generateErrorResult(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
