package com.example.project.pacttest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.project.vo.UserVO;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;

@Provider("App1Provider") // Name of the provider
@PactFolder("C:\\Users\\hshaik\\OneDrive - Capgemini\\Desktop\\Springapp\\project2\\target\\pacts") // Location of the Pact files

public class App1ProviderTest {
	
	@LocalServerPort
    private int port;

    public HttpTestTarget target ;

    @BeforeEach
    void setUp(PactVerificationContext context) {
    	target = new HttpTestTarget("localhost", 8080 , "/"); // Dynamically set the port from the Spring Boot test
        context.setTarget(target);
    }
     @State("User with ID 1 exists")
      public void userWithId1Exists() {
    	UserVO userVO = new UserVO();
    	userVO.setId(1);
    	userVO.setName("Rizwana");
    	userVO.setAge(24);
   }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class) 
    void verifyPacts(PactVerificationContext context) {
        context.verifyInteraction(); // Verifies interactions defined in the Pact file
    }
    
}