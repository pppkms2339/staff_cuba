package ru.loshmanov.staff.web.screens.document.document_file_preview;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.MessageBundle;
import org.springframework.http.MediaType;

import javax.inject.Inject;

public class DocumentPreviewComponentFactory {

    private final UiComponents uiComponents;
    private final MessageBundle messageBundle;
    private final Notifications notifications;

    public DocumentPreviewComponentFactory(UiComponents uiComponents, MessageBundle messageBundle, Notifications notifications) {
        this.uiComponents = uiComponents;
        this.messageBundle = messageBundle;
        this.notifications = notifications;
    }

    public Component create(FileDescriptor file) {
        GroupBoxLayout groupBoxLayout = uiComponents.create(GroupBoxLayout.class);
        groupBoxLayout.setShowAsPanel(true);
        groupBoxLayout.setWidthFull();
        groupBoxLayout.setHeightFull();
        groupBoxLayout.setStyleName("well");
        groupBoxLayout.setCaption(messageBundle.formatMessage("previewFile", file.getName()));
        if (isPdf(file)) {
            groupBoxLayout.add(filePdfComponent(file));
        } else if (isImage(file)) {
            groupBoxLayout.add(fileImageComponent(file));
        } else if (isNotShownFile(file)) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("documentFileNotShown"))
                    .show();
            return null;
        }
        return groupBoxLayout;
    }

    private boolean isPdf(FileDescriptor file) {
        return file.getExtension().contains("pdf");
    }

    private boolean isImage(FileDescriptor imageFile) {
        return imageFile.getExtension().contains("png")
                || imageFile.getExtension().contains("jpg")
                || imageFile.getExtension().contains("jpeg");
    }

    private boolean isNotShownFile(FileDescriptor file) {
        return file.getExtension().contains("doc")
                || file.getExtension().contains("docx")
                || file.getExtension().contains("xls")
                || file.getExtension().contains("xlsx")
                || file.getExtension().contains("ppt")
                || file.getExtension().contains("pptx");
    }

    private Component filePdfComponent(FileDescriptor imageFile) {
        BrowserFrame browserFrame = uiComponents.create(BrowserFrame.class);
        browserFrame.setAlignment(Component.Alignment.MIDDLE_CENTER);
        browserFrame.setWidthFull();
        browserFrame.setHeightFull();
        browserFrame.setSource(FileDescriptorResource.class)
                .setFileDescriptor(imageFile)
                .setMimeType(MediaType.APPLICATION_PDF_VALUE);
        return browserFrame;
    }

    private Component fileImageComponent(FileDescriptor imageFile) {
        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.SCALE_DOWN);
        image.setAlignment(Component.Alignment.MIDDLE_CENTER);
        image.setWidthFull();
        image.setHeightFull();
        image.setSource(FileDescriptorResource.class)
                .setFileDescriptor(imageFile);
        return image;
    }

}
