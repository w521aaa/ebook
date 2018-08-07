package com.weim.service.impl;

import com.weim.entity.Content;
import com.weim.repository.ContentRepository;
import com.weim.service.ContentService;
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
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public List<Content> findAllByStatus(Integer status) {
        return contentRepository.findAllByStatus(status);
    }

    @Override
    public Content saveContent(Content content) {

        Content contentTemp = contentRepository.findByTitleId(content.getTitleId());
        if(contentTemp != null) {
            content.setId(contentTemp.getId());
        } else {
            content.setId(CommonUtils.getUUID());
        }

        return contentRepository.save(content);
    }

    @Override
    public Content findByTitleId(String titleId) {
        return contentRepository.findByTitleId(titleId);
    }
}
