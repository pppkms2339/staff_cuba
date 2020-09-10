package ru.loshmanov.staff.web.screens.project;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.screen.*;
import ru.loshmanov.staff.entity.Employee;
import ru.loshmanov.staff.entity.Project;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("staff_Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
@LoadDataBeforeShow
public class ProjectEdit extends StandardEditor<Project> {

    @Inject
    private UiComponents uiComponents;

    public Component generateCheckBox(Employee item) {
        CheckBox checkBox = uiComponents.create(CheckBox.NAME);
        List<Employee> employees = getEditedEntity().getEmployees();
        if (employees.contains(item)) {
            checkBox.setValue(true);
        }
        checkBox.addValueChangeListener(e -> {
            if (e.getValue().equals(Boolean.TRUE)) {
                employees.add(item);
            } else {
                employees.remove(item);
            }
        });
        return checkBox;
    }

}