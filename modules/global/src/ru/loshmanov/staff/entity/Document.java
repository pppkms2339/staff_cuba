package ru.loshmanov.staff.entity;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "STAFF_DOCUMENT")
@Entity(name = "staff_Document")
public class Document extends StandardEntity {
    private static final long serialVersionUID = -1622465649952466494L;

    @Column(name = "SHORT_NAME")
    @NotNull
    @NotEmpty
    @Length(max = 30)
    private String shortName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "URL")
    private String url;

    @Column(name = "PRIORITY")
    private Integer priority;

    @JoinTable(name = "STAFF_DOCUMENT_FILE_DESCRIPTOR_LINK",
            joinColumns = @JoinColumn(name = "DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "FILE_DESCRIPTOR_ID"))
    @ManyToMany
    private List<FileDescriptor> documentFiles;

    public List<FileDescriptor> getDocumentFiles() {
        return documentFiles;
    }

    public void setDocumentFiles(List<FileDescriptor> documentFiles) {
        this.documentFiles = documentFiles;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}