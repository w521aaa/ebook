package com.weim.service;

import com.weim.entity.Title;

import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
public interface TitleService {
    Title saveTitle(Title title);

    List<Title> findAllByBookId(String bookId);

    Title findTitleById(String titleId);

    Title findByBookIdAndSort(String bookId, int i);

    List<Title> findAllStatus(Integer status);

    List<Title> findAllByBookIdAndStatus(String bookId, Integer status);
}
