package com.jovo.ScienceCenter.util;

import com.jovo.ScienceCenter.exception.EmptyStringException;

public class StringUtil {

    public static void requireNonEmptyString(String str, String message) {
        if (str.trim().isEmpty()) {
            throw new EmptyStringException(message);
        }
    }
}
