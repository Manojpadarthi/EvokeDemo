package com.evoke.demo.employee.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.evoke.demo.employee.CacheEventLogger;
import com.evoke.demo.employee.Employee;
import com.evoke.demo.employee.EmployeeEntity;
import com.evoke.demo.employee.dao.EmployeeRepository;

@Service
public class EmployeeService 
{
	Logger log = LoggerFactory.getLogger(EmployeeService .class);

	@Autowired
	EmployeeRepository repository;
	
	public EmployeeEntity saveEmployee(EmployeeEntity employee) {
		
		return repository.save(employee);
		
	}
	
    public List<EmployeeEntity> getEmployees() {
		return repository.findAll();
		
	}
    
    @Cacheable(value = "tenSecondsCache",key = "#id")
   public Optional<EmployeeEntity> getEmployee(Long id) {
    	
    	log.info("Calling service layer");
		
		return repository.findById(id);
		
	}
	
   public void deleteEmployee(Long id) {
		
		repository.deleteById(id);
		
	}
	
}
