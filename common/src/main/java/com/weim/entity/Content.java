package com.weim.entity;

import com.weim.utils.CommonUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @author weim
 * @date 18-7-26
 */

@Entity
@Table(indexes = {
        @Index(name = "CONTENT_STATUS", columnList = "status")
})
public class Content {

    @Id
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String titleId;

    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private  String content;

    @Column(columnDefinition = "tinyint default 0")
    private Integer status = CommonUtils.IS_NOT_DOWNLOAD;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
