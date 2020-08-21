package ru.loshmanov.staff.web.screens.employee;

import com.haulmont.cuba.gui.screen.*;
import ru.loshmanov.staff.entity.Employee;

@UiController("staff_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
@LoadDataBeforeShow
public class EmployeeBrowse extends StandardLookup<Employee> {
}