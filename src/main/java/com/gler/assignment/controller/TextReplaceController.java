package com.gler.assignment.controller;

import com.gler.assignment.service.TextReplaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextReplaceController {
    private final TextReplaceService textReplaceService;

    public TextReplaceController(TextReplaceService textReplaceService) {
        this.textReplaceService = textReplaceService;
    }

    @GetMapping("/replace")
    public String replace(@RequestParam String text) {
        return textReplaceService.replace(text);
    }
}