package com.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Post {
    private int postID;
    private String postTitle;
    private String postBody;
    private List<String> postTags;
    private String postType;
    private String postEmergency;
    private List<String> postComments;

    // Constructor initializes a post with the specified details.
    public Post(int postID, String postTitle, String postBody, List<String> postTags, String postType, String postEmergency) {
        this.postID = postID;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postTags = new ArrayList<>(postTags);
        this.postType = postType;
        this.postEmergency = postEmergency;
        this.postComments = new ArrayList<>();
    }

    // Attempts to add a post to the system if it meets all validation criteria.
    public boolean addPost() {
        if (validatePost()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("posts.txt", true))) {
                writer.write(this.toString());
                writer.newLine();
                return true;
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    // Validates the post details against specified criteria.
    private boolean validatePost() {
        boolean titleLengthValid = postTitle.length() >= 10 && postTitle.length() <= 250;
        boolean titleStartValid = !Pattern.matches("^[0-9\\W]{5}.*", postTitle);
        boolean bodyLengthValid = postBody.length() >= 250;
        boolean tagsCountValid = postTags.size() >= 2 && postTags.size() <= 5;
        boolean tagsDetailValid = postTags.stream().noneMatch(tag -> tag.length() < 2 || tag.length() > 10 || !tag.equals(tag.toLowerCase()));
        boolean limitEasyValid = !("Easy".equals(postType) && postTags.size() > 3);
        //boolean lengthDifficultValid = !("Very Difficult".equals(postType) || "Difficult".equals(postType)) || postBody.length() >= 300;
        //boolean emergencyValid = !("Easy".equals(postType) && ("Immediately Needed".equals(postEmergency) || "Highly Needed".equals(postEmergency)))
                             //&& !(("Very Difficult".equals(postType) || "Difficult".equals(postType)) && "Ordinary".equals(postEmergency));

        // Debugging output
        System.out.println("Title Length Valid: " + titleLengthValid);
        System.out.println("Title Start Valid: " + titleStartValid);
        System.out.println("Body Length Valid: " + bodyLengthValid);
        System.out.println("Tags Count Valid: " + tagsCountValid);
        System.out.println("Tags Detail Valid: " + tagsDetailValid);
        System.out.println("Limit Easy Valid: " + limitEasyValid);
        //System.out.println("Length Difficult Valid: " + lengthDifficultValid);
        //System.out.println("Emergency Valid: " + emergencyValid);

        return titleLengthValid && titleStartValid && bodyLengthValid && tagsCountValid && tagsDetailValid && limitEasyValid; //System.out.println("Length Difficult Valid: " + lengthDifficultValid);
        //System.out.println("Emergency Valid: " + emergencyValid);
    }
    
    // Attempts to add a comment to the post if it meets validation criteria.
    public boolean addComment(String comment) {
        if (validateComment(comment)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("comments.txt", true))) {
                writer.write(comment);
                writer.newLine();
                return true;
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    // Validates the comment details against specified criteria.
    private boolean validateComment(String comment) {
        String[] words = comment.split("\\s+");
        if (words.length < 4 || words.length > 10 || !Character.isUpperCase(words[0].charAt(0)))
            return false;
        if (this.postComments.size() >= (postType.equals("Easy") || postEmergency.equals("Ordinary") ? 3 : 5))
            return false;
        return true;
    }

    // Returns a string representation of the post.
    @Override
    public String toString() {
        return "Post ID: " + postID +
               "\nTitle: " + postTitle +
               "\nBody: " + postBody +
               "\nTags: " + String.join(", ", postTags) +
               "\nType: " + postType +
               "\nEmergency: " + postEmergency;
    }
}
