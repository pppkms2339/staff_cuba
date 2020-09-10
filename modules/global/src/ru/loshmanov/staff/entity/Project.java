package ru.loshmanov.staff.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Table(name = "STAFF_PROJECT")
@Entity(name = "staff_Project")
@NamePattern("%s|title")
public class Project extends StandardEntity {
    private static final long serialVersionUID = 8568422729793653471L;

    @NotNull
    @Column(name = "TITLE", nullable = false)
    @NotEmpty
    private String title;

    @JoinTable(name = "STAFF_PROJECT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "PROJECT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}