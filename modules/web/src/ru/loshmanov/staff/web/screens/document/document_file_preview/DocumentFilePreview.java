package ru.loshmanov.staff.web.screens.document.document_file_preview;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("staff_DocumentFilePreview")
@UiDescriptor("document-file-preview.xml")
@LoadDataBeforeShow
@EditedEntityContainer("fileDc")
public class DocumentFilePreview extends StandardEditor<FileDescriptor> {

    @Inject
    protected VBoxLayout documentFilePreview;

    @Inject
    protected InstanceContainer<FileDescriptor> fileDc;

    @Inject
    protected MessageBundle messageBundle;

    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected Notifications notifications;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        documentFilePreview.removeAll();
        Component component = documentFile(fileDc.getItem());
        if (component != null) {
            documentFilePreview.add(component);
        }
    }

    private Component documentFile(FileDescriptor file) {
        DocumentPreviewComponentFactory factory = new DocumentPreviewComponentFactory(uiComponents, messageBundle, notifications);
        return factory.create(file);
    }

}
