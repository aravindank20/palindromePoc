This is the POC app to check whether the given word is Palindrome or not.
#System Requirements
JDK - 17 or more
Maven - 3.*
Port - 8081 should be free or open if not please change the server.port in [](src/main/resources/application.properties)

#Framework used
Spring Boot - Starter, Web, Validation, Swagger,starter-test
jackson, json-simple
jakarta.validation-api
mockito-core, jacoco-report

#REST Doc 
[](palindrome-api-docs.yaml)

#Steps to use the application
1) Import project into any IDE where you have JDK 17 or more and Maven support- I have used IntelliJ
2) Use "mvn clean compile" using command terminal or click clean & compile in Maven window to build the project - Mandatory to be successful before proceeding to step3
3) Use "mvn spring-boot:start" using command terminal or click spring-boot:start to start the app
4) Use "mvn spring-boot:stop" using command terminal or click spring-boot:stop to stop the app

#Usage
#For now this POC supports only POST request with no authorisation - Refer [](palindrome-api-docs.yaml) on expected input format
Please use the [Swagger API to use the post checker ](http://localhost:8081/swagger-ui/index.html)

or 

Please use the Curl in command terminal - example as below 

`curl -X POST -H "Content-Type: application/json" -d '{"userName":"John Doe","txtToCheck":"kayak"}' http://localhost:8081/poc/checkPalindrome`

Sample OutPut:
{"userName":"John Doe","txtToCheck":"kayak","response":"true"}

or 

Any other tool like POSTMAN follow above CURL command for URL, header and request body

#Code information
1) Used 2 pointer algo and stream to check whether the given word is a palindrome or not in [](src/main/java/com/aravindan/palindromepoc/services/PalindromeManager.java). Hence performance will not be affected
2) Used Spring cache to store it in cache and loading it from file to cache during start up [](src/main/java/com/aravindan/palindromepoc/services/FileStoreService.java). For larger application Redis can be used.
3) Having StorageService as an interface. Another storage solution can be easily substituted.
4) Http request handler  in [](src/main/java/com/aravindan/palindromepoc/controller/UserController.java). This is the entry point
5) Unit test in [](src/test/java/com/aravindan/palindromepoc/controller/UserControllerTest.java). Explained all the cases inside the class.
6) Test coverage can be seen in [](site/jacoco/index.html)