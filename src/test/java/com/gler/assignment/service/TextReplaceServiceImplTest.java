package com.gler.assignment.service;

import com.gler.assignment.exception.InvalidTextException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextReplaceServiceImplTest {

    private final TextReplaceServiceImpl service =
            new TextReplaceServiceImpl();

    // Normal text replacement
    @Test
    void shouldReplaceFirstAndLastCharacters() {

        String result = service.replace("elephant");

        assertEquals("*lephan$", result);
    }

    // Short word replacement
    @Test
    void shouldReplaceForShortWord() {

        String result = service.replace("home");

        assertEquals("*om$", result);
    }

    // Length exactly 2 should return empty string
    @Test
    void shouldReturnEmptyStringWhenLengthIsTwo() {

        String result = service.replace("ab");

        assertEquals("", result);
    }

    // Length less than 2 should throw exception
    @Test
    void shouldThrowExceptionWhenLengthIsLessThanTwo() {

        assertThrows(
                InvalidTextException.class,
                () -> service.replace("a")
        );
    }

    // Empty string should throw exception
    @Test
    void shouldThrowExceptionWhenTextIsEmpty() {

        assertThrows(
                InvalidTextException.class,
                () -> service.replace("")
        );
    }

    // Mixed letters, numbers and special characters
    @Test
    void shouldHandleMixedCharacters() {

        String result = service.replace("abc#20xyz");

        assertEquals("*bc#20xy$", result);
    }

    // Three character word
    @Test
    void shouldReplaceThreeCharacterText() {

        String result = service.replace("abc");

        assertEquals("*b$", result);
    }

    // Long text from assignment example
    @Test
    void shouldReplaceLongText() {

        String result = service.replace("TestingCodeAssignmentProject");

        assertEquals("*estingCodeAssignmentProjec$", result);
    }

    // Text containing spaces
    @Test
    void shouldHandleTextWithSpaces() {

        String result = service.replace("My Test Project");

        assertEquals("*y Test Projec$", result);
    }
}