package com.weim.controller;

import com.weim.entity.Title;
import com.weim.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
@Controller
public class TitleController {

    @Autowired
    private TitleService titleService;

    @GetMapping("/ebook/titles/{bookId}")
    public String getTitlesByBookId(@PathVariable String bookId, Model model) {

        List<Title> titleList = titleService.findAllByBookId(bookId);

        model.addAttribute("titleList", titleList);

        return "title";
    }


}
