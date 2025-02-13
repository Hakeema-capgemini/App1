package com.example.project.jaxbconversions;

import com.example.project.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.xml.bind.JAXBException;

public class JaxbConversions {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Convert POJO to JSON
	 public static String convertToJson(UserDTO user) throws Exception {
	        return objectMapper.writeValueAsString(user);
	    }

	    // Convert JSON to POJO
	    public static <UserDTO> UserDTO convertFromJson(String json, Class<UserDTO> clazz) throws Exception {
	        return objectMapper.readValue(json, clazz);
	    }

    public static void main(String[] args) throws Exception {
    	try {
            // Create a sample User object
            UserDTO userDTO = new UserDTO();
            userDTO.setId(1);
            userDTO.setName("Zahra");
            userDTO.setAge(2);

            // Convert POJO to JSON
            String json = JaxbConversions.convertToJson(userDTO);
            System.out.println("POJO to JSON:");
            System.out.println(json);

            // Convert JSON back to POJO
            UserDTO userFromJson = JaxbConversions.convertFromJson(json, UserDTO.class);
            System.out.println("\nJSON to POJO:");
            System.out.println(userFromJson);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
  
    }
}