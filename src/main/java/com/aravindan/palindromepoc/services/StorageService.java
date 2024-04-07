package com.aravindan.palindromepoc.services;

/**
 * Interface for storage service
 *
 * @author Aravindan Kalaiselvan
 *
 */
public interface StorageService {
    void save(String txtToCheck, Boolean isPalindrome);
    void getAvlPalindromeLst();
}
