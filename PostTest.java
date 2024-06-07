package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class PostTest {
    private Post post;

    // Sets up a default post object before each test.
    @BeforeEach
    void setUp() {
        List<String> tags = Arrays.asList("java", "code");
        String longDescription = "This is a detailed description which meets the length requirement for very difficult and difficult types as well. " +
                                 "This body is specifically designed to exceed 300 characters to ensure that it meets the stringent requirements " +
                                 "for posts classified under difficult categories in the system.";
        post = new Post(1, "Proper Title Length Over Ten", longDescription, tags, "Difficult", "Immediately Needed");
    }

    // Tests if a valid post is added successfully.
    @Test
    void testAddPostValid() {
        assertTrue(post.addPost(), "Post should be added when all conditions are met.");
    }

    // Tests the scenario where the post title is too short to be valid.
    @Test
    void testAddPostShortTitle() {
        post = new Post(2, "Short", "Valid body with sufficient length...", Arrays.asList("java", "tutorial"), "Easy", "Ordinary");
        assertFalse(post.addPost(), "Post should not be added with a short title.");
    }

    // Tests the scenario where the post title starts with invalid characters.
    @Test
    void testAddPostInvalidTitleCharacters() {
        post = new Post(3, "12345Invalid", "Valid body...", Arrays.asList("java", "code"), "Easy", "Ordinary");
        assertFalse(post.addPost(), "Post should not be added with invalid initial title characters.");
    }

    // Tests the scenario where the tags are not in the correct format.
    @Test
    void testAddPostInvalidTags() {
        post = new Post(4, "Valid Title", "Valid body...", Arrays.asList("java", "CodeJAVA"), "Difficult", "Ordinary");
        assertFalse(post.addPost(), "Post should not be added with invalid tag case.");
    }

    // Tests the scenario where there are not enough tags.
    @Test
    void testAddPostInvalidTagCount() {
        post = new Post(5, "Valid Title", "Valid body...", Arrays.asList("java"), "Difficult", "Ordinary");
        assertFalse(post.addPost(), "Post should not be added with fewer than required tags.");
    }

    // Tests if a valid comment is added successfully.
    @Test
    void testAddCommentValid() {
        assertTrue(post.addComment("This is a Valid comment."), "Comment should be added when valid.");
    }

    // Tests the scenario where a comment is too short.
    @Test
    void testAddCommentTooFewWords() {
        assertFalse(post.addComment("Too short"), "Comment should not be added if it has too few words.");
    }

    // Tests the scenario where a comment does not start with a capital letter.
    @Test
    void testAddCommentIncorrectCapitalization() {
        assertFalse(post.addComment("invalid lowercase start"), "Comment should not be added if the first letter is not capitalized.");
    }
}
