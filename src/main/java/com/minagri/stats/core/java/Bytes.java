package com.minagri.stats.core.java;

import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;

public interface Bytes {

    static String toString(byte[] value) {
        if (value == null) {
            return null;
        }
        return new String(value, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    static Object toObject(byte[] objectBytes) {
        if (objectBytes == null) {
            return null;
        }
        
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        }
    }
}
