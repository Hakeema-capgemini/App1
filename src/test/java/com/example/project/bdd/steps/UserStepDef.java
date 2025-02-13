package com.example.project.bdd.steps;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import com.example.project.ProjectApplication;
import com.example.project.dto.UserDTO;
import com.example.project.vo.UserVO;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest(classes = ProjectApplication.class)
@ContextConfiguration(classes = { ProjectApplication.class })
public class UserStepDef {
	private static final String BASE_URL = "http://localhost:8080/app1/api/users";

    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<?> responseEntity;
    private ResponseEntity<UserVO> response;
    private List<UserVO> usersList;
 
    private static final String ADD_USER_URL = BASE_URL + "/create";
    
    private static final String GET_USER_BY_ID_URL = BASE_URL + "/retrieve/id/";
    private Long createdUserId;

    @When("Create a new User")
    public void createNewUser(DataTable dataTable) {
        // Convert DataTable to a list of maps
        List<Map<String, String>> users = dataTable.asMaps(String.class, String.class);

        // Iterate over each user in the table and send a POST request
        for (Map<String, String> user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.get("name"));
            userDTO.setAge(Integer.parseInt(user.get("age"))); // Convert age to integer

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDTO> request = new HttpEntity<>(userDTO, headers);

            // Send POST request
            responseEntity = restTemplate.postForEntity(ADD_USER_URL, request, UserDTO.class);
            
            // Optionally, validate the response for each user
            Assertions.assertEquals(200, responseEntity.getStatusCodeValue(), "User creation failed for: " + userDTO.getName());
        }
    }

    @Then("the response should confirm user creation")
    public void responseShouldConfirmUserCreation() {
        // Assert that response status is 200
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Then("the user is created")
    public void userIsCreated() {
        // Assert that response body contains user details
        UserDTO responseBody = (UserDTO) responseEntity.getBody(); // Cast to UserDTO
        Assertions.assertNotNull(responseBody, "Response body is null");

        // Validate the response fields
        Assertions.assertNotNull(responseBody.getId(), "ID is null");
        Assertions.assertNotNull(responseBody.getName(), "Name is null");
        Assertions.assertNotNull(responseBody.getAge(), "Age is null");
    }

    @Given("the user with ID {long} exists in the system")
    public void userExistsInSystem(Long id) {
        // Send GET request to retrieve user
        String url = BASE_URL + "/retrieve/" + id;
        responseEntity = restTemplate.getForEntity(url, Map.class);

        // Assert that response status is 200
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }
    @When("Retrieve User with ID {long}")
    public void retrieve_user_with_id(Long id) {
    	String url = BASE_URL + "/retrieve/" + id;
        responseEntity = restTemplate.getForEntity(url, Map.class);
    	
    }

    @Then("the response should contain the user's details")
    public void responseShouldContainUserDetails(DataTable dataTable) {
        // Convert DataTable rows to a Map
        Map<String, String> expectedDetails = dataTable.asMaps(String.class, String.class).get(0);

        // Assert response body
        Map<String, Object> actualDetails = (Map<String, Object>) responseEntity.getBody();
        Assertions.assertNotNull(actualDetails, "Response body is null");

        // Trim and compare each value
        Assertions.assertEquals(
            Long.valueOf(expectedDetails.get("id")), // Convert expected to Long
            ((Number) actualDetails.get("id")).longValue(), // Convert actual to Long
            "ID mismatch"
        );
        Assertions.assertEquals(
            expectedDetails.get("name").trim(),
            ((String) actualDetails.get("name")).trim(),
            "Name mismatch"
        );
        Assertions.assertEquals(
            Integer.parseInt(expectedDetails.get("age")), 
            actualDetails.get("age"),
            "Age mismatch"
        );
    }
}