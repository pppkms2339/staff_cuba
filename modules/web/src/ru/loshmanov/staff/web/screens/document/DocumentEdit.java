package ru.loshmanov.staff.web.screens.document;

import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.util.OperationResult;
import org.slf4j.Logger;
import ru.loshmanov.staff.entity.Document;
import ru.loshmanov.staff.entity.Employee;
import ru.loshmanov.staff.web.screens.document.document_file_preview.DocumentFilePreview;
import ru.loshmanov.staff.web.screens.document.document_file_preview.DocumentPreviewComponentFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UiController("staff_Document.edit")
@UiDescriptor("document-edit.xml")
@EditedEntityContainer("documentDc")
@LoadDataBeforeShow
public class DocumentEdit extends StandardEditor<Document> {

    @Inject
    private Table<FileDescriptor> documentFilesTable;

    @Inject
    protected ExportDisplay exportDisplay;

    @Inject
    protected FileUploadField upload;

    @Inject
    protected FileUploadingAPI fileUploadingAPI;

    @Inject
    private DataManager dataManager;

    @Inject
    protected CollectionPropertyContainer<FileDescriptor> documentFilesDc;

    @Inject
    protected Notifications notifications;

    @Inject
    protected MessageBundle messageBundle;

    @Inject
    protected Logger logger;

    @Inject
    private FileStorageService fileStorageService;

    @Inject
    private HBoxLayout documentFilesWrapperLayout;

    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    private Button lookButton;

    private List<FileDescriptor> newImageDescriptors = new ArrayList<>();

    @Subscribe("documentFilesTable")
    public void onDocumentFilesTableSelection(Table.SelectionEvent<FileDescriptor> event) {
        lookButton.setEnabled(true);
        documentFilesWrapperLayout.removeAll();
        Set<FileDescriptor> selectedDocumentFiles = event.getSelected();
        if (!selectedDocumentFiles.isEmpty()) {
            Component component = documentFile(selectedDocumentFiles.iterator().next());
            if (component != null) {
                lookButton.setEnabled(true);
                documentFilesWrapperLayout.add(component);
            } else {
                lookButton.setEnabled(false);
            }
        }
    }

    private Component documentFile(FileDescriptor file) {
        DocumentPreviewComponentFactory factory = new DocumentPreviewComponentFactory(uiComponents, messageBundle, notifications);

        return factory.create(file);
    }

    @Subscribe("documentFilesTable.edit")
    public void onDocumentFilesTableEdit(Action.ActionPerformedEvent event) {
        if(lookButton.isEnabled()) {
            screenBuilders.editor(FileDescriptor.class, this)
                    .editEntity(documentFilesTable.getSingleSelected())
                    .withScreenClass(DocumentFilePreview.class)
                    .withOpenMode(OpenMode.DIALOG)
                    .show();
        }
    }

    @Subscribe("documentFilesTable.download")
    public void onDocumentFilesTableDownload(Action.ActionPerformedEvent event) {
        downloadFile(documentFilesTable.getSingleSelected());
    }

    private void downloadFile(FileDescriptor file) {
        exportDisplay.show(file, ExportFormat.OCTET_STREAM);
    }

    @Subscribe("upload")
    public void onUploadFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        FileDescriptor imageDescriptor = upload.getFileDescriptor();

        try {
            fileUploadingAPI.putFileIntoStorage(upload.getFileId(), imageDescriptor);
            // save file descriptor and remember it in case the user clicks "Cancel" and we need to remove it
            FileDescriptor savedImageDescriptor = dataManager.commit(imageDescriptor);
            newImageDescriptors.add(savedImageDescriptor);
            // add file descriptor to data container to show in the table
            documentFilesDc.getMutableItems().add(savedImageDescriptor);

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("documentFileStoredSuccessfully"))
                    .show();
        } catch (FileStorageException e) {
            String failedMessage = messageBundle.getMessage("documentFileStorageFailed");
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption(failedMessage)
                    .show();

            logger.error(failedMessage, e);
        }
    }

    @Override
    public OperationResult closeWithDiscard() {
        return super.closeWithDiscard().then(() -> {
            for (FileDescriptor fileDescriptor : newImageDescriptors) {
                try {
                    fileStorageService.removeFile(fileDescriptor);
                    if (documentFilesDc.containsItem(fileDescriptor)) { // could be removed by user right after adding
                        dataManager.remove(fileDescriptor);
                    }
                } catch (FileStorageException e) {
                    logger.warn("Unable to remove file " + fileDescriptor);
                }
            }
        });
    }

    @Subscribe("lookButton")
    public void onLookButtonClick(Button.ClickEvent event) {
        screenBuilders.editor(FileDescriptor.class, this)
                .editEntity(documentFilesTable.getSingleSelected())
                .withScreenClass(DocumentFilePreview.class)
                .withOpenMode(OpenMode.DIALOG)
                .show();
    }

    public Component generateNumberField(FileDescriptor item) {
        Label label = uiComponents.create(Label.NAME);
        label.setValue(documentFilesDc.getItems().indexOf(item) + 1);
        return label;
    }

    public Component generateFileTypeField(FileDescriptor item) {
        Label label = uiComponents.create(Label.NAME);
        label.setValue(item.getExtension().toUpperCase());
        return label;
    }

}