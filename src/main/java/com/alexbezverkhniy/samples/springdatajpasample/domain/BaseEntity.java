package com.alexbezverkhniy.samples.springdatajpasample.domain;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@MappedSuperclass
public class BaseEntity {
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
