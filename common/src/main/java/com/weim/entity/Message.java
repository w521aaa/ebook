package com.weim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author weim
 * @date 18-8-1
 */

@Entity
public class Message {

    @Id
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String message;

    private Date createDate;

    private String email;

    @Column(columnDefinition = "tinyint default 0")
    private Integer status = 0; //是否处理

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
