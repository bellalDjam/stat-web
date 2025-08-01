package com.minagri.stats.core.java;

import lombok.SneakyThrows;

import java.sql.Clob;

public interface Clobs {

    @SneakyThrows
    static String toString(Clob clob) {
        if (clob == null) {
            return null;
        }
        return clob.getSubString(1, (int) clob.length());
    }
}
