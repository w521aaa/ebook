package com.weim.repository;

import com.weim.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author weim
 * @date 18-8-1
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, String>, JpaSpecificationExecutor<Message> {
}
