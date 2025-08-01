package com.minagri.stats.vaadin.download;

import com.minagri.stats.core.exception.ExceptionConsumer;
import com.minagri.stats.core.exception.Exceptions;
import com.minagri.stats.core.java.Strings;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceRegistry;
import com.vaadin.flow.server.VaadinSession;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Downloads {

    static void download(String fileName, ExceptionConsumer<OutputStream> outputStreamConsumer) {
        StreamResourceRegistry resourceRegistry = VaadinSession.getCurrent().getResourceRegistry();

        AtomicReference<StreamRegistration> streamRegistrationAtomicReference = new AtomicReference<>();

        StreamResource streamResource = new StreamResource(fileName, (outputStream, session) -> {
            outputStreamConsumer.accept(outputStream);
            outputStream.flush();

            StreamRegistration streamRegistration = streamRegistrationAtomicReference.get();
            if (streamRegistration != null) {
                streamRegistration.unregister();
            }
        });

        StreamRegistration registration = resourceRegistry.registerResource(streamResource);
        streamRegistrationAtomicReference.set(registration);

        UI.getCurrent().getPage().executeJs("window.open($0, $1)", registration.getResourceUri().toString(), "_blank");
    }

    static void downloadInputStream(String fileName, InputStream inputStream) {
        download(fileName, outputStream -> Exceptions.sneakyThrows(() -> inputStream.transferTo(outputStream)));
    }

    static void downloadInputStream(String baseFileName, String extension, InputStream inputStream) {
        downloadInputStream(baseFileName + "." + extension, inputStream);
    }

    static void downloadXlsInputStream(String baseFileName, InputStream inputStream) {
        downloadInputStream(baseFileName, "xls", inputStream);
    }

    static void downloadInputStreamResponse(Response response) {
        String contentDispositionHeader = response.getHeaderString("Content-Disposition");

        String fileName = null;
        if (!Strings.isEmpty(contentDispositionHeader)) {
            Matcher filenameMatcher = Pattern.compile(".+filename=(.+\\..*)").matcher(contentDispositionHeader);
            if (filenameMatcher.find()) {
                fileName = filenameMatcher.group(1);
            }
        }

        if (fileName == null) {
            throw new RuntimeException("No filename found in Content-Disposition header");
        }

        downloadInputStream(fileName, response.readEntity(InputStream.class));
    }

    static void downloadBytesInChunks(String fileName, BiFunction<Long, Long, byte[]> offsetLimitChunkFetcher) {
        long defaultLimit = 1024 * 1024; // 1MB
        downloadBytesInChunks(fileName, defaultLimit, offset -> offsetLimitChunkFetcher.apply(offset, defaultLimit));
    }

    static void downloadBytesInChunks(String fileName, Long limit, Function<Long, byte[]> offsetChunkFetcher) {
        download(fileName, outputStream -> {
            long offset = 0;
            byte[] chunkBytes;

            do {
                chunkBytes = offsetChunkFetcher.apply(offset);
                outputStream.write(chunkBytes);

                offset += limit;
            } while (chunkBytes.length == limit);
        });
    }
}
