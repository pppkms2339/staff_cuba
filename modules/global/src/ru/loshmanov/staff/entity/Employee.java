package ru.loshmanov.staff.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "STAFF_EMPLOYEE")
@Entity(name = "staff_Employee")
@NamePattern("%s|name")
public class Employee extends StandardEntity {
    private static final long serialVersionUID = -6555767483384128337L;

    @Column(name = "NAME")
    @NotNull
    @NotEmpty
    private String name;
    @JoinTable(name = "STAFF_PROJECT_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROJECT_ID"))
    @ManyToMany
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}