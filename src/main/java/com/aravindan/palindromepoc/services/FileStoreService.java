package com.aravindan.palindromepoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * This class is to store the palindrome word & the response to file
 *  - retrieve it from the file during spring start up and put it on cache
 *
 * @author Aravindan Kalaiselvan
 *
 */
@Service
@Component
public class FileStoreService implements StorageService, CommandLineRunner {

    @Autowired
    LoggingService loggingService;

    @Value("${palindrome.name}")
    private String palindromeFile;

    private static final String NEWLINEREGEX = System.getProperty("line.separator");

    @Autowired
    private CacheManager cacheManager;

    /**
     * This is to save the word and response to the file storage
     * @param txtToCheck palindrome word
     * @param isPalindrome is true if palindrome or else false
     */
    @Override
    public void save(String txtToCheck, Boolean isPalindrome) {
        FileWriter file = null;
        try {
            loggingService.logInfo("storing " +  txtToCheck + " in file for future reference");
            // File path is passed as parameter with append true
            file = new FileWriter(palindromeFile,true);
            file.write(txtToCheck+"-"+isPalindrome+ NEWLINEREGEX);
            file.close();
        } catch (Exception e) {
            loggingService.logError(String.valueOf(e));
        }

    }

    /**
     * This will retrieve the palindrome word and response from file storage
     * And store it in cache
     */
    @Override
    public void getAvlPalindromeLst() {
        loggingService.logInfo("updating the palindrome cache if the file has contents");
        // File path is passed as parameter
        File file = new File(palindromeFile);
        if(file.exists() && file.length() > 0) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String palindromeTxt;
                while ((palindromeTxt = br.readLine()) != null){
                    if(!palindromeTxt.isEmpty()){
                        String[] result = palindromeTxt.split("-");
                        // below will update the word in cache
                        if(cacheManager.getCache("palindrome")!=null){
                            cacheManager.getCache("palindrome").put(result[0], Boolean.valueOf(result[1]));
                        }

                    }
                }
            } catch (Exception e) {
                loggingService.logError(String.valueOf(e));
            }
        } else {
            loggingService.logInfo("palindrome cache failed file not available or empty");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        getAvlPalindromeLst();
    }
}
