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
        @Index(name = "BOOK_STATUS", columnList = "status")
})
public class Book {

    @Id
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    private String title;

    private String url;

    private String image;

    private String localImage;

    private String author;

    @Column(columnDefinition = "tinyint default 0")
    private Integer status = CommonUtils.IS_NOT_DOWNLOAD;

    @Column(columnDefinition = "tinyint default 0")
    private Integer endStatus = 0;

    private Date createDate;

    public Book() {
        this.setCreateDate(new Date());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocalImage() {
        return localImage;
    }

    public void setLocalImage(String localImage) {
        this.localImage = localImage;
    }

    public Integer getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(Integer endStatus) {
        this.endStatus = endStatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", localImage='" + localImage + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                '}';
    }
}
