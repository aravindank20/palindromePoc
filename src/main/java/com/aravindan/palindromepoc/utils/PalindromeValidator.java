package com.aravindan.palindromepoc.utils;

import com.aravindan.palindromepoc.model.PalindromeInput;
import com.aravindan.palindromepoc.model.UserResponse;
import com.aravindan.palindromepoc.services.LoggingService;
import com.aravindan.palindromepoc.services.PalindromeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class to validate the palindrome word and form the response object
 *
 * @author Aravindan Kalaiselvan
 *
 */
@Service
public class PalindromeValidator {

    @Autowired
    LoggingService loggingService;

    @Autowired
    PalindromeManager palindromeManager;

    /**
     * This method is called from UserController to check for the palindrome word
     * @param palindromeInput userName and txtToCheck as JSON
     * @return ResponseEntity after all checks
     */
    public ResponseEntity<String> validatePalindrome(PalindromeInput palindromeInput){
        String userName = palindromeInput.getUserName();
        String txtToCheck = palindromeInput.getTxtToCheck();
        loggingService.logInfo("going to check the word -" + txtToCheck + " whether it is palindrome or not ");
        // to form the response
        UserResponse response = new UserResponse(userName,txtToCheck, palindromeManager.checkPalindrome(txtToCheck).toString());
        return ResponseEntity.ok(response.toString());
    }
}
