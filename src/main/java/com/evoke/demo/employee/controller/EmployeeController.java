package com.evoke.demo.employee.controller;


import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evoke.demo.employee.Employee;
import com.evoke.demo.employee.EmployeeEntity;
import com.evoke.demo.employee.EmployeeNotFoundException;
import com.evoke.demo.employee.service.EmployeeService;

@RestController
public class EmployeeController 
{
	@Autowired
	EmployeeService service;
	
	@Autowired
	ModelMapper mapper;
	
	 
	  @PostConstruct
	  public void modelMapperConfigurationSetup() {
		 
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		   
	  }
	
	@PostMapping(path="/employees/save")
	public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody Employee employee) 
	{
		
		EmployeeEntity employeeEntity = service.saveEmployee(employee);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeEntity);
		
	}
	
	
	@GetMapping(path="/employees/retrieve")
	public ResponseEntity<List<EmployeeEntity>> getEmployees() 
	{
		List<EmployeeEntity> employeeEntities=service.getEmployees();
		
		return ResponseEntity.status(HttpStatus.OK).body(employeeEntities);
		
	}
	
	
	@GetMapping(path="/employees/retrieve/{id}")
	public ResponseEntity<EmployeeEntity> getEmployee(@PathVariable(name = "id") Long id) 
	{
		
		Optional<EmployeeEntity> employeeEntity=service.getEmployee(id);
		
		EmployeeEntity entity=employeeEntity.orElseThrow(()-> new EmployeeNotFoundException("Employee Does not exist with given Id"));
	
		return ResponseEntity.status(HttpStatus.OK).body(entity);
	}
	
	@PutMapping(path="/employees/update/{id}")
	public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable(name = "id") Long id, @RequestBody Employee employee){
        Optional<EmployeeEntity> employeeEntity=service.getEmployee(id);
		//EmployeeEntity entity=employeeEntity.orElseThrow(()-> new EmployeeNotFoundException("Employee Does not exist with given Id"));
	   /* entity.setName(employee.getName());
		entity.setPhone(employee.getPhone());
		entity.setEmail(employee.getEmail());
		entity.setCreatedBy(employee.getCreatedBy());
		entity.setCreatedOn(new Date());*/
        EmployeeEntity updatedEntity = service.updateEmployee(employeeEntity.get(),employee);
	    return ResponseEntity.status(HttpStatus.CREATED).body(updatedEntity);
	}
	
	@DeleteMapping(path="/employees/delete/{id}")
	public void deleteEmployee(@PathVariable(name = "id") Long id) 
	{
		 Optional<EmployeeEntity> employeeEntity=service.getEmployee(id);
			//EmployeeEntity entity=employeeEntity.orElseThrow(()-> new EmployeeNotFoundException("Employee Does not exist with given Id"));
        service.deleteEmployee(id,employeeEntity.get());
	}
	
	

}
