package ru.loshmanov.staff.web.screens.employee;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import ru.loshmanov.staff.entity.Employee;

@UiController("staff_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
@LoadDataBeforeShow
public class EmployeeEdit extends StandardEditor<Employee> {
}