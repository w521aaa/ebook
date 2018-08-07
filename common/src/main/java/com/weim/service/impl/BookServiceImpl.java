package com.weim.service.impl;

import com.weim.entity.Book;
import com.weim.entity.Title;
import com.weim.repository.BookRepository;
import com.weim.service.BookService;
import com.weim.service.TitleService;
import com.weim.utils.CommonUtils;
import com.weim.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TitleService titleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

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

    @Override
    public void deleteBook(String bookId) {

        logger.info(CommonUtils.getNowDate() + " delete book id=> " + bookId);

        //清除redis key
        //清除content title book
        List<Title> titleList = titleService.findAllByBookId(bookId);

        for (Title title : titleList) {
            String text = MD5Utils.encode(title.getTitle());
            redisTemplate.delete(text);

            titleService.deleteTitle(title.getId());
        }

        bookRepository.deleteById(bookId);
    }
}
