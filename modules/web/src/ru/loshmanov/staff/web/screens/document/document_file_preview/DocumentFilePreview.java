package ru.loshmanov.staff.web.screens.document.document_file_preview;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.components.FileDescriptorResource;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("staff_DocumentFilePreview")
@UiDescriptor("document-file-preview.xml")
@LoadDataBeforeShow
@EditedEntityContainer("fileDc")
public class DocumentFilePreview extends StandardEditor<FileDescriptor> {

    @Inject
    protected Image image;

    @Inject
    protected InstanceContainer<FileDescriptor> fileDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        image.setSource(FileDescriptorResource.class)
                .setFileDescriptor(fileDc.getItem());
    }

}
