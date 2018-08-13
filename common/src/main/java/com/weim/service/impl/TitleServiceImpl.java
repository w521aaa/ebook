package com.weim.service.impl;

import com.weim.entity.Title;
import com.weim.exception.EbookException;
import com.weim.repository.TitleRepository;
import com.weim.service.ContentService;
import com.weim.service.TitleService;
import com.weim.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author weim
 * @date 18-7-27
 */

@Service
@Transactional
public class TitleServiceImpl implements TitleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private ContentService contentService;

    @Override
    public Title saveTitle(Title title) {

        //保存的时候  首先根据章节名查找  没有则保存 有则更新
        Title titleTemp = titleRepository.findByTitle(title.getTitle());
        if(titleTemp != null) {
            title.setId(titleTemp.getId());
        } else {
            title.setId(CommonUtils.getUUID());
        }

        return titleRepository.saveAndFlush(title);
    }

    @Override
    public List<Title> findAllByBookId(String bookId) {
        return titleRepository.findAllByBookId(bookId, new Sort(Sort.Direction.ASC, "sort"));
    }

    @Override
    public Title findTitleById(String titleId) {
        Title titleTemp = titleRepository.findById(titleId).get();
        return titleTemp;
    }

    @Override
    public Title findByBookIdAndSort(String bookId, int sort) {
        return titleRepository.findByBookIdAndSort(bookId, sort).orElse(null);
    }

    @Override
    public List<Title> findAllStatus(Integer status) {
        return titleRepository.findAllByStatus(status);
    }

    @Override
    public List<Title> findAllByBookIdAndStatus(String bookId, Integer status) {
        return titleRepository.findAllByBookIdAndStatus(bookId, status);
    }

    @Override
    public void deleteTitle(String titleId) {

        contentService.deleteContentByTitleId(titleId);

        titleRepository.deleteById(titleId);
    }

    @Override
    public List<Title> findAllByBookIdAndCreateDate(String bookId, Date date) {
        return titleRepository.findAllByBookIdIsAndCreateDateGreaterThanEqual(bookId,date, new Sort(Sort.Direction.ASC, "sort"));
    }
}
