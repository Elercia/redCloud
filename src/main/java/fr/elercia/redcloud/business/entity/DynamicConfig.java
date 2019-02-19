package fr.elercia.redcloud.business.entity;

import javax.persistence.*;

@Entity
public class DynamicConfig {

    public enum DynamicConfigName {
        ENVIRONMENT,
        STORAGE_PATH
    }

    @Id
    @Enumerated(EnumType.STRING)
    private DynamicConfigName name;

    @Column
    private String value;

    public DynamicConfig(DynamicConfigName name, String value) {
        this.name = name;
        this.value = value;
    }

    public DynamicConfigName getName() {
        return name;
    }

    public void setName(DynamicConfigName name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}