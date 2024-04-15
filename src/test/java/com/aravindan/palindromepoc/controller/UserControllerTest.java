package com.aravindan.palindromepoc.controller;

import com.aravindan.palindromepoc.model.PalindromeInput;
import com.aravindan.palindromepoc.services.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RestController tests
 * This will help to check whether checkPalindrome http request is working or not
 * And file storage is working or not
 * And to validate the cache on repeating words
 *
 * @author Aravindan Kalaiselvan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "palindrome.name=palindromeTest.txt" })
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    private static final String API_POST_PATH = "/poc/checkPalindrome";

    @Autowired
    private MockMvc mockMvc;

    @Value("${palindrome.name}")
    private String palindromeFile;

    private FileWriter file;

    @Autowired
    UserController userController;

    @SpyBean
    @Autowired
    private StorageService storageService;

    @BeforeAll
    public void setUp() throws IOException {
        file = new FileWriter(palindromeFile,true);
        file.write("madam-true");
        file.close();
        // this is just to check whether it is able to put it on cache or not
        storageService.getAvlPalindromeLst();
    }

    /**
     * This will help to check whether http requests are processing or not with the expected output
     */
    @ParameterizedTest
    @CsvSource({"arv,madam,true", "arv,madamm,false", "arv,level,true", "arv,radar,true"})
    public void succesfullRestOutput(String userName, String txtToCheck, String isPalindrome) throws Exception {
        PalindromeInput palindromeInput = new PalindromeInput(userName, txtToCheck);
        this.mockMvc.perform(post(API_POST_PATH).content(asJsonString(palindromeInput))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(isPalindrome));
        ;
    }

    /**
     * This will help to check whether invalid request are thrown with the error
     */
    @ParameterizedTest
    @MethodSource("provideStrings")
    public void validateInputAndThrowError(String userName, String txtToCheck, String expected) throws Exception {
        PalindromeInput palindromeInput = new PalindromeInput(userName, txtToCheck);
        this.mockMvc.perform(post(API_POST_PATH).content(asJsonString(palindromeInput))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.txtToCheck").value(expected));
        ;
    }

    /**
     * invalid requests
     */
    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of("arv","madam123","txtToCheck should contain only alphabets"),
                Arguments.of("arv","mad am123","txtToCheck should contain only alphabets"),
                Arguments.of("arv", null,"txtToCheck cannot be null or empty")
        );
    }

    /**
     * This will help to check cache storage
     * and are we retrieving it from the cache on repeating words
     */
    @ParameterizedTest
    @CsvSource({"arv,kayak,true,1", "arv,kayak,true,0", "arv,kayakk,false,1","arv,Rotator,true,1"})
    public void testSaveToFileAndThenCache(String userName, String txtToCheck, Boolean isPalindrome ,int storeToCache){
        PalindromeInput palindromeInput = new PalindromeInput(userName, txtToCheck);
        userController.checkPalindrome(palindromeInput);
        Mockito.verify(storageService,Mockito.times(storeToCache)).save(txtToCheck,isPalindrome);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public void tearDown() throws IOException {
        File delFile = new File(palindromeFile);
        delFile.delete();
    }

}