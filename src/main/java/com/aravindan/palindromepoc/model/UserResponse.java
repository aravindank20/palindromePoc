package com.aravindan.palindromepoc.model;

/**
 * This class is to form the user response for palindrome check
 *
 * @author Aravindan Kalaiselvan
 *
 */
public class UserResponse {
    private String userName;
    private String txtToCheck;
    private String response;

    /**
     * used constructor to pass the response values
     * @param userName just for logging
     * @param txtToCheck palindrome word
     * @param response is true if palindrome or else false
     */
    public UserResponse(String userName, String txtToCheck, String response){
        this.userName= userName;
        this.txtToCheck= txtToCheck;
        this.response = response;
    }

    /**
     * This will be the final JSON output
     */
    @Override
    public String toString() {
        return "{\"userName\":\""+this.userName+"\",\"txtToCheck\":\""+this.txtToCheck+"\",\"response\":\""+this.response+"\"}";
    }
}
