package com.example.project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
	
	private long id;
	@NotNull(message="Name should not be null")
	private String name;
	@Min(value = 1, message = "Age must be at least 1")
    @Max(value = 25, message = "Age must be no more than 25")
	private int age;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

   
	
	
}