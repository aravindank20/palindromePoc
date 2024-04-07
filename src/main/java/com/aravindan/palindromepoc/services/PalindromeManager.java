package com.aravindan.palindromepoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

/**
 * This class is to check the word is Palindrome or not & save to file
 *  - cache it for future use
 *
 * @author Aravindan Kalaiselvan
 *
 */
@EnableCaching
@Service
public class PalindromeManager {

    @Autowired
    LoggingService loggingService;

    /**
     * used to store the output
     */
    @Autowired
    StorageService storageService;

    /**
     * This is to check the palindrome word and store it to the file storage
     * - cache it for future check
     * @param txtToCheck palindrome word
     * @return Boolean after all checks
     */
    @Cacheable(value = "palindrome")
    public Boolean checkPalindrome(String txtToCheck){
        loggingService.logInfo("Not in cache so going to do the check for " + txtToCheck);
        String temp  = txtToCheck.replaceAll("\\s+", "").toLowerCase();
        Boolean isPalindrome = IntStream.range(0, temp.length() / 2)
                .noneMatch(i -> temp.charAt(i) != temp.charAt(temp.length() - i - 1));
        // to store it
        storageService.save(txtToCheck, isPalindrome);
        return isPalindrome;
    }
}
