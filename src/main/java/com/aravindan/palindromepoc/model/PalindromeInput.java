package com.aravindan.palindromepoc.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * PalindromeInput to match the requestBody on post http requests
 *
 * @author Aravindan Kalaiselvan
 *
 */
public class PalindromeInput {

    @Getter
    @NotEmpty(message = "Username cannot be null or empty")
    private String userName;

    @Getter
    @NotEmpty(message = "txtToCheck cannot be null or empty")
    @Pattern(regexp = "^[A-Za-z]*$", message = "txtToCheck should contain only alphabets")
    private String txtToCheck;

    /**
     * used constructor only for unit test cases
     * @param userName just for logging
     * @param txtToCheck palindrome word
     */
    public PalindromeInput(String userName, String txtToCheck) {
        this.userName = userName;
        this.txtToCheck = txtToCheck;
    }
}
