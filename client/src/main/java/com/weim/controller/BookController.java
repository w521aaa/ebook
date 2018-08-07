package com.weim.controller;

import com.weim.VO.TextFile;
import com.weim.entity.Book;
import com.weim.service.BookService;
import com.weim.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weim
 * @date 18-7-27
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Value("${access.host.domain.name}")
    private String localHostName;

    @GetMapping("/ebook/books")
    public String getBooks (Model model) {

        List<Book> bookList = bookService.findAll();

        model.addAttribute("bookList", bookList);

        return "book";
    }

    @GetMapping("/ebook/books/download")
    public String downloadBooks (Model model) {

        String[] pathFileName = CommonUtils.getPathFileName("");

        List<TextFile> textFileList = new ArrayList<>();

        for(int index = 0; index < pathFileName.length; index++) {

            TextFile textFile = new TextFile();
            int temp = pathFileName[index].lastIndexOf(".");
            textFile.setTitle(pathFileName[index].substring(0, temp));
            textFile.setUrl(localHostName + "/upload/text/" + pathFileName[index]);

            textFileList.add(textFile);
        }

        model.addAttribute("textFileList", textFileList);

        return "download";
    }

}
