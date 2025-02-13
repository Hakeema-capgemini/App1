package com.example.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class UserDTO {

	private long id;
	@NotNull(message = "Name should not be null")
	private String name;
	@Min(value = 1, message = "Age must be at least 1")
	@Max(value = 25, message = "Age must be no more than 25")
	private int age;

	@XmlElement
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    @XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    @XmlElement
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	 @Override
	    public String toString() {
	        return "UserDTO [id=" + id + ", name=" + name + ", age=" + age + "]";
	 }
}