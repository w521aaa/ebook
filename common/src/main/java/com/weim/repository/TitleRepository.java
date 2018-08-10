package com.weim.repository;

import com.weim.entity.Title;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author weim
 * @date 18-7-27
 */
@Repository
public interface TitleRepository extends JpaRepository<Title, String> , JpaSpecificationExecutor<Title> {
    Title findByTitle(String title);

    List<Title> findAllByBookId(String bookId, Sort sort);

    Optional<Title> findByBookIdAndSort(String bookId, int sort);

    List<Title> findAllByStatus(Integer status);

    List<Title> findAllByBookIdAndStatus(String bookId, Integer status);

    List<Title> findAllByBookIdIsAndCreateDateGreaterThanEqual(String bookId, Date date);
}
