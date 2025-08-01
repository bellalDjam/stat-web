package com.minagri.stats.vaadin.component.upload;

import com.minagri.stats.vaadin.component.button.LCMButton;
import com.minagri.stats.vaadin.component.html.LCMSpan;
import com.minagri.stats.vaadin.translation.entity.CommonMessage;
import com.minagri.stats.vaadin.translation.entity.TranslationKey;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.server.streams.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class LCMFileUpload extends Upload {
    @Setter
    Path uploadDirectoryPath;

    @Getter
    List<Path> uploadFilePaths = new ArrayList<>();
    List<Path> uploadFilePathsInProgress = new ArrayList<>();

    LCMButton uploadButton;
    LCMSpan dropLabel;
    Runnable succeededListener;

    public LCMFileUpload() {
        uploadButton = new LCMButton()
                .withText(CommonMessage.UPLOAD_FILE)
                .withPrimaryTheme();

        dropLabel = new LCMSpan()
                .withText(CommonMessage.DROP_FILE_HERE);

        FileUploadCallback fileUploadCallback = (_, _) -> Optional.ofNullable(succeededListener).ifPresent(Runnable::run);
        FileFactory fileFactory = metadata -> {
            if (uploadDirectoryPath == null) {
                uploadDirectoryPath = Files.createTempDirectory("lcm-file-upload");
            }

            Path uploadFilePath = uploadDirectoryPath.resolve(metadata.fileName());
            uploadFilePathsInProgress.add(uploadFilePath);

            return uploadFilePath.toFile();
        };
        TransferProgressListener transferProgressListener = new TransferProgressListener() {
            @Override
            public void onError(TransferContext context, IOException reason) {
                log.error("upload failed for file {}", context.fileName(), reason);
            }

            @Override
            public void onComplete(TransferContext context, long transferredBytes) {
                uploadFilePaths.clear();
                uploadFilePaths.addAll(uploadFilePathsInProgress);
                uploadFilePathsInProgress.clear();
            }
        };

        setUploadHandler(UploadHandler.toFile(fileUploadCallback, fileFactory, transferProgressListener));
        setUploadButton(uploadButton);
        setDropLabel(dropLabel);
        addAllFinishedListener(event -> clearFileList());
        setMaxFiles(1);
    }

    public Path getUploadFilePath() {
        return uploadFilePaths.getFirst();
    }

    public LCMFileUpload withMultipleFileSupport() {
        return withMultipleFileSupport(Integer.MAX_VALUE);
    }

    public LCMFileUpload withMultipleFileSupport(Integer maxFiles) {
        setMaxFiles(maxFiles);
        return this;
    }

    public LCMFileUpload withUploadDirectoryPath(Path uploadDirectoryPath) {
        setUploadDirectoryPath(uploadDirectoryPath);
        return this;
    }

    public LCMFileUpload withSucceededListener(Runnable succeededListener) {
        this.succeededListener = succeededListener;
        return this;
    }

    public LCMFileUpload withUploadButtonLabel(TranslationKey uploadButtonLabel) {
        uploadButton.withTextKey(uploadButtonLabel);
        return this;
    }

    public LCMFileUpload withDropLabel(TranslationKey dropLabel) {
        this.dropLabel.withTextKey(dropLabel);
        return this;
    }
}
