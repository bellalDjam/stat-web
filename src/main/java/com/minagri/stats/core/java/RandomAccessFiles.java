package com.minagri.stats.core.java;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public interface RandomAccessFiles {

    @SneakyThrows
    static String read(RandomAccessFile file, long position, int length, Charset charset) {
        long fileLength = file.length();
        if (position < 0 || fileLength < position + length) {
            throw new RuntimeException("Invalid read position: " + position + ", length: " + length + ", file length: " + fileLength);
        }

        byte[] buffer = new byte[length];
        file.seek(position);
        file.read(buffer);
        return new String(buffer, charset);
    }

    @SneakyThrows
    static String safeRead(RandomAccessFile file, long position, int length, Charset charset) {
        if (position < 0 || file.length() < position + length) {
            return null;
        }

        return read(file, position, length, charset);
    }
}
