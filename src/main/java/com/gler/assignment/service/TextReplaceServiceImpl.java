package com.gler.assignment.service;

import com.gler.assignment.exception.InvalidTextException;
import org.springframework.stereotype.Service;

@Service
public class TextReplaceServiceImpl implements TextReplaceService {

    @Override
    public String replace(String text) {

        // Length less than 2 is invalid
        if (text.length() < 2) {
            throw new InvalidTextException("Text length must be at least 2");
        }

        // Length exactly 2 returns an empty string
        if (text.length() == 2) {
            return "";
        }

        // Extract text excluding first and last characters
        String middleText = text.substring(1, text.length() - 1);

        // Replace first character with '*' and last character with '$'
        return "*" + middleText + "$";
    }
}