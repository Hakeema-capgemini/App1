package com.example.project.jaxbconversions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.jaxb.JAXBContextFactory;

import com.example.project.dto.UserDTO;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class JaxbPtoJ {

	 public static void main(String[] args) throws Exception {
	        // Create and populate the POJO
	        UserDTO userDTO = new UserDTO();
	        userDTO.setId(1L);
	        userDTO.setName("Rizwana");
	        userDTO.setAge(24);

	        // Create MOXy-specific JAXB Context
	        Map<String, Object> properties = new HashMap<>();
	        properties.put("eclipselink.media-type", "application/json");
	        properties.put("eclipselink.json.include-root", false);
	        
	        JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[]{UserDTO.class}, properties);
	        Marshaller marshaller = jaxbContext.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Convert POJO to JSON
	        marshaller.marshal(userDTO, System.out);
	    }
	}

