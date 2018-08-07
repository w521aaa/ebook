package com.weim.service.impl;

import com.weim.entity.Book;
import com.weim.repository.BookRepository;
import com.weim.service.BookService;
import com.weim.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {

        //保存的时候  首先根据书名查找  没有则保存 有则更新
        Book bookTemp = bookRepository.findByTitle(book.getTitle());
        if(bookTemp != null) {
            book.setId(bookTemp.getId());
        } else {
            book.setId(CommonUtils.getUUID());
        }
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public List<Book> findAllByStatus(Integer status) {
        return bookRepository.findAllByStatus(status);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(String bookId) {
        return bookRepository.findById(bookId).get();
    }

    @Override
    public List<Book> findAllByEndStatus(Integer endStatus) {
        return bookRepository.findAllByEndStatus(endStatus);
    }
}
