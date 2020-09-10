package ru.loshmanov.staff.web.screens.document;

import com.haulmont.cuba.gui.screen.*;
import ru.loshmanov.staff.entity.Document;

@UiController("staff_Document.browse")
@UiDescriptor("document-browse.xml")
@LookupComponent("documentsTable")
@LoadDataBeforeShow
public class DocumentBrowse extends StandardLookup<Document> {
}