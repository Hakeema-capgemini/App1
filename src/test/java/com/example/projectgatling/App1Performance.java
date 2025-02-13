package com.example.projectgatling;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
public class App1Performance extends Simulation{

	    // Define the base URL of your application
	    private static final String BASE_URL = "http://localhost:8080/app1/api/users";

	    // Define HTTP protocol	
	    HttpProtocolBuilder httpProtocol = http
	        .baseUrl(BASE_URL) // Base URL
	        .acceptHeader("application/json") // Common headers
	        .contentTypeHeader("application/json");

	    // Scenario 1: Test User Creation
	    ScenarioBuilder createUserScenario = scenario("Create User Scenario")
	        .exec(
	            http("Create User") // HTTP request name
	                .post("/create") // POST request
	                .body(StringBody("{\"name\": \"Rizwana\", \"age\": 25}")) // JSON body
	                .check(status().is(200)) // Validate HTTP status
	        );

	    // Scenario 2: Test User Retrieval
	    ScenarioBuilder retrieveUserScenario = scenario("Retrieve User Scenario")
	            .exec(
	                http("Retrieve User") // HTTP request name
	                    .get("/retrieve/1") // GET request with user ID
	                    .check(status().is(200)) // Validate HTTP status
	                    .check(
	                        jsonPath("$.name")
	                            .transform(s -> s.trim()) // Trim any whitespace from the response
	                            .is("Rizwana") // Validate the expected name
	                    )
	            );

	    // Set up performance test
	    {
	        setUp(
	            // Run both scenarios with different users
	            createUserScenario.injectOpen(rampUsers(10).during(5)), // 50 users over 10 seconds
	            retrieveUserScenario.injectOpen(rampUsers(10).during(5))
	        ).protocols(httpProtocol);
	    }
	}

