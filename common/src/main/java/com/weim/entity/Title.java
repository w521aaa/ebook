package com.weim.entity;

import com.weim.utils.CommonUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @author weim
 * @date 18-7-27
 */
@Entity
@Table(indexes = {
        @Index(name = "TITLE_STATUS", columnList = "status")
})
public class Title {

    @Id
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String bookId;

    private String title;

    private String url;

    private Integer sort;

    private Date createDate;

    @Column(columnDefinition = "tinyint default 0")
    private Integer status = CommonUtils.IS_NOT_DOWNLOAD;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
}
