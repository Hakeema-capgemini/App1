//package com.example.project.util;
//
//import java.io.StringReader;
//import java.io.StringWriter;
//
//import com.example.project.dto.UserDTO;
//
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Marshaller;
//import jakarta.xml.bind.Unmarshaller;
//
//public class Jaxb {
//
//    // Convert POJO to XML
//    public static String convertToXml(UserDTO user) throws JAXBException {
//       try {
//    	   JAXBContext context = JAXBContext.newInstance(UserDTO.class);
//           Marshaller marshaller = context.createMarshaller();
//           marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//           marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//           StringWriter writer = new StringWriter();
//   		   marshaller.marshal(user, writer);
//           return writer.toString();
//	} catch (Exception e) {
//		// TODO: handle exception
//	}
//	return null;
//    }
//
//    // Convert XML to POJO
//    public static <UserDTO> UserDTO convertFromXml(String xml, Class<UserDTO> clazz) throws JAXBException {
//        JAXBContext context = JAXBContext.newInstance(clazz);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        StringReader reader = new StringReader(xml);
//        return (UserDTO) unmarshaller.unmarshal(reader);
//    }
//    
//    public static void main(String args[]) throws JAXBException {
//    	UserDTO user = new UserDTO();
//    	user.setAge(12);
//    	user.setName("Hakeema");
//    	user.setId(33);
//    	System.out.println(JaxbConversions.convertToXml(user));
//    	UserDTO user1=convertFromXml(JaxbConversions.convertToXml(user),UserDTO.class);
//    }
//}



