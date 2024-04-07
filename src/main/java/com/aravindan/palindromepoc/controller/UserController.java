package com.aravindan.palindromepoc.controller;

import com.aravindan.palindromepoc.model.PalindromeInput;
import com.aravindan.palindromepoc.services.LoggingService;
import com.aravindan.palindromepoc.utils.PalindromeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RestController to accept http requests
 *
 * @author Aravindan Kalaiselvan
 *
 */

@RestController
@RequestMapping("/poc")
public class UserController {

    /**
     * used for validating input
     */
    @Autowired
    PalindromeValidator validatorInput;

    @Autowired
    LoggingService loggingService;

    /**
     * This accepts http post request to check palindrome or not
     * @param palindromeInput userName and txtToCheck as JSON
     * @return ResponseEntity output as JSON
     */
    @PostMapping(value = "/checkPalindrome")
    public ResponseEntity<String> checkPalindrome(@Valid @RequestBody PalindromeInput palindromeInput) {
        loggingService.logInfo("Received request to check palindrome on word - " + palindromeInput.getTxtToCheck() + " by user - " + palindromeInput.getUserName());
        return validatorInput.validatePalindrome(palindromeInput);
    }
}
