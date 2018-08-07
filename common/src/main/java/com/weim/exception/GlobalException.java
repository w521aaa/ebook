package com.weim.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * @author weim
 * @date 18-7-27
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(EbookException.class)
    public String dealEbookException(Exception e, Model model) {

        model.addAttribute("msg",e.getMessage());

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String dealException(Exception e, Model model) {

        model.addAttribute("msg",e.getMessage());

        return "error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElement(Exception e, Model model) {
        model.addAttribute("msg",e.getMessage());

        return "error";
    }


}
