package ru.loshmanov.staff.web.screens.project;

import com.haulmont.cuba.gui.screen.*;
import ru.loshmanov.staff.entity.Project;

@UiController("staff_Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
@LoadDataBeforeShow
public class ProjectBrowse extends StandardLookup<Project> {

}