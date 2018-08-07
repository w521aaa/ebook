package com.weim.controller;

import com.weim.entity.Content;
import com.weim.entity.Title;
import com.weim.service.ContentService;
import com.weim.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author weim
 * @date 18-7-27
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private TitleService titleService;

    @GetMapping("/ebook/contents/{titleId}")
    public String getContentsByTitleId(@PathVariable String titleId, Model model) {

        Content content = contentService.findByTitleId(titleId);

        //当前页
        Title titleTemp = titleService.findTitleById(titleId);

        //上一页
        Title preTitle = titleService.findByBookIdAndSort(titleTemp.getBookId(), titleTemp.getSort()-1);

        //下一页
        Title nextTitle = titleService.findByBookIdAndSort(titleTemp.getBookId(), titleTemp.getSort()+1);


        model.addAttribute("content", content);
        model.addAttribute("prePage", preTitle);
        model.addAttribute("nextPage", nextTitle);


        return "content";
    }

}
