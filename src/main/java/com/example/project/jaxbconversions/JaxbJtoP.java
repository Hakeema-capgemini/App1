package com.example.project.jaxbconversions;

import java.io.StringReader;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import com.example.project.dto.UserDTO;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class JaxbJtoP {
	 public static void main(String[] args) throws JAXBException {
		 String json = "\uFEFF{ \"id\": 1, \"name\": \"Rizwana\", \"age\": 24 }";
		 json = json.replaceAll("[^\\x20-\\x7E]", ""); // Remove non-ASCII characters


	        // Create JAXB Context with MOXy JSON support
	        JAXBContext context = JAXBContext.newInstance(UserDTO.class);

	        // Create Unmarshaller
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json"); // Configure for JSON
	        unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);      // Root element not included in JSON

	        // Unmarshal JSON to POJO
	        StringReader reader = new StringReader(json);
	        UserDTO user = (UserDTO) unmarshaller.unmarshal(reader);

	        // Print POJO
	        System.out.println("ID: " + user.getId());
	        System.out.println("Name: " + user.getName());
	        System.out.println("Age: " + user.getAge());
	    }
}
