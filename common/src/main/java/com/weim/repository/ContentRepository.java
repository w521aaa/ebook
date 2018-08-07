package com.weim.repository;

import com.weim.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, String> , JpaSpecificationExecutor<Content> {

    List<Content> findAllByStatus(Integer status);

    Content findByTitleId(String titleId);

    void deleteByTitleId(String titleId);
}
