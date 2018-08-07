package com.weim.utils;

import org.springframework.data.domain.Page;

/**
 * @author weim
 * @date 18-6-29
 */
public class ResPageObject extends ResObject {
    public int number;
    public int size;
    public long sum;
    public int pages;

    public ResPageObject(Page<?> pages) {
        code = 0;
        msg = "ok";
        this.content = pages.getContent();
        this.number = pages.getNumber();
        this.size = pages.getSize();
        this.sum = pages.getTotalElements();
        this.pages = pages.getTotalPages();
    }

}
