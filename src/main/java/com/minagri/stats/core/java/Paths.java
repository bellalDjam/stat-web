package com.minagri.stats.core.java;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public interface Paths {

    @SneakyThrows
    static void delete(Path path) {
        try (Stream<Path> deletePaths = Files.walk(path)) {
            deletePaths.sorted(Comparator.reverseOrder()).forEach(deletePath -> {
                boolean deleted = deletePath.toFile().delete();
                if (!deleted) {
                    throw new RuntimeException("unable to delete file " + deletePath);
                }
            });
        }
    }

    static String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        if (!fileName.contains(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    static String getBaseName(Path path) {
        String fileName = path.getFileName().toString();
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
}
