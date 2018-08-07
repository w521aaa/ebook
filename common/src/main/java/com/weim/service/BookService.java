package com.weim.service;

import com.weim.entity.Book;

import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
public interface BookService {
    Book saveBook(Book book);

    List<Book> findAllByStatus(Integer status);

    List<Book> findAll();

    Book findBookById(String bookId);

    List<Book> findAllByEndStatus(Integer endStatus);

    void deleteBook(String bookId);
}
