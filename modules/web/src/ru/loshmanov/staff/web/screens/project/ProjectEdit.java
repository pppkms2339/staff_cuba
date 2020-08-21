package ru.loshmanov.staff.web.screens.project;

import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import ru.loshmanov.staff.entity.Employee;
import ru.loshmanov.staff.entity.Project;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.sl.usermodel.PresetColor.Window;

@UiController("staff_Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
@LoadDataBeforeShow
public class ProjectEdit extends StandardEditor<Project> {

    @Inject
    private UiComponents uiComponents;

    @Inject
    private Screens screens;

    private List<Employee> selectedItemsList = new ArrayList<>();

    public Component generateCheckBox(Employee item) {
        CheckBox checkBox = uiComponents.create(CheckBox.NAME);
        if (selectedItemsList.contains(item)) {
            checkBox.setValue(true);
        }
        checkBox.addValueChangeListener(e -> {
            //List<Project> list = item.getProjects();
            if (e.getValue().equals(Boolean.TRUE)) {
                //list.add;
                selectedItemsList.add(item);
            } else {
                selectedItemsList.remove(item);
            }
        });
        return checkBox;
    }

    @Subscribe(id = "employeesDc", target = Target.DATA_CONTAINER)
    public void onEmployeesDcCollectionChange(CollectionContainer.CollectionChangeEvent<Employee> event) {
        if (selectedItemsList.size() != 0) {
            return;
        }
        selectedItemsList.addAll(event.getSource().getItems());
    }

//    @Subscribe("okButton")
//    public void onOkButtonClick(Button.ClickEvent event) {
//        Project project = getEditedEntityContainer().getItem();
//        project.setEmployees(selectedItemsList);
//    }

    public void save() {
        Project project = getEditedEntityContainer().getItem();
        project.setEmployees(selectedItemsList);
        closeWithCommit();
    }
}