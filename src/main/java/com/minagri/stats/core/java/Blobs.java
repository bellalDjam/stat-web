package com.minagri.stats.core.java;

import lombok.SneakyThrows;

import java.sql.Blob;

public interface Blobs {

    static String toString(Blob blob) {
        if (blob == null) {
            return null;
        }
        
        byte[] bytes = toBytes(blob);
        return Bytes.toString(bytes);
    }

    @SneakyThrows
    static byte[] toBytes(Blob blob) {
        if (blob == null) {
            return null;
        }
        
        return blob.getBytes(1, (int) blob.length());
    }

}
