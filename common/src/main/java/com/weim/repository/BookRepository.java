package com.weim.repository;

import com.weim.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    Book findByTitle(String title);

    List<Book> findAllByStatus(Integer status);

    List<Book> findAllByEndStatus(Integer endStatus);
}
