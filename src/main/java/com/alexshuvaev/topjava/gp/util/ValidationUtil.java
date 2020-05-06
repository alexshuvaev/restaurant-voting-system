package com.alexshuvaev.topjava.gp.util;

import com.alexshuvaev.topjava.gp.util.exception.InvalidDataFieldException;
import com.alexshuvaev.topjava.gp.util.exception.NotFoundException;
import org.springframework.validation.BindingResult;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found domain with " + msg);
        }
    }

    public static void fieldValidation(BindingResult r){
        if (r.hasErrors()){
            StringBuilder sb = new StringBuilder();
            r.getFieldErrors().forEach(fe -> sb.append(fe.getField().toUpperCase()).append(" ").append(fe.getDefaultMessage()).append(". "));
            throw new InvalidDataFieldException(sb.toString());
        }
    }
}