package com.weim.service;

import com.weim.entity.Content;

import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
public interface ContentService {
    List<Content> findAllByStatus(Integer isNotDownload);

    Content saveContent(Content content);

    Content findByTitleId(String titleId);

    void deleteContentByTitleId(String titleId);
}
