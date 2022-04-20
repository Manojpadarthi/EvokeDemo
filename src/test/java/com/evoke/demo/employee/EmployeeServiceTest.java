package com.evoke.demo.employee;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import com.evoke.demo.employee.dao.DepartmentRepository;
import com.evoke.demo.employee.dao.EmployeeRepository;
import com.evoke.demo.employee.DepartmentEntity;
import com.evoke.demo.employee.service.EmployeeService;

@SpringBootTest
@RunWith(SpringRunner.class)
class EmployeeServiceTest {
	
	@Autowired
	@InjectMocks
	EmployeeService service;
	
	@MockBean
    private EmployeeRepository employeeRepository;
     
	@MockBean
    private DepartmentRepository deptRepository;
	
	/*@MockBean
	private ModelMapper mapper;*/
	
	@BeforeEach
	public void setUp() {
		EmployeeEntity ramu = new EmployeeEntity();
		ramu.setName("ramu");
		ramu.setPhone("9999999999");
		
		EmployeeEntity jampa = new EmployeeEntity();
		jampa.setName("jampa");
		jampa.setPhone("111111111111");
		
		List<EmployeeEntity> e= new ArrayList<EmployeeEntity>();
		e.add(ramu);
		e.add(jampa);

	    Mockito.when(employeeRepository.findAll())
	      .thenReturn(e);
	    
	    Optional<EmployeeEntity> ee=Optional.of(ramu);
	    
	    Mockito.when(employeeRepository.findById(any(Long.class))).thenReturn(ee);
	     
	    DepartmentEntity dept1 = new DepartmentEntity();
	    
	    dept1.setDepartmentName("HR");
	    
	    Mockito.when(deptRepository.save(any(DepartmentEntity.class))).thenReturn(dept1);
	 //Mockito.when(deptRepository.getById(1L)).thenReturn(dept1);
	   
	    
	    EmployeeEntity raju = new EmployeeEntity();
		raju.setName("raju");
		raju.setPhone("111111111111");
		
		//Mockito.when(mapper.map(any(Employee.class), EmployeeEntity.class)).thenReturn(raju);
		
		
		
		 Mockito.when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(raju);
		
	
	}
	
	
	
	@Test
	void testGetEmployees() 
	{
		assertEquals("jampa",service.getEmployees().get(1).getName());
	}
	
	@Test
	void testGetEmployee() 
	{
		assertEquals("ramu",service.getEmployee(1L).get().getName());
	}
	
	
	@Test
	void testSaveEmployee() 
	{
		assertEquals("raju",service.saveEmployee(new Employee()).getName());
	}

}
