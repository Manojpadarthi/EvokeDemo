package com.evoke.demo.employee.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.evoke.demo.employee.Employee;
import com.evoke.demo.employee.EmployeeEntity;
import com.evoke.demo.employee.JwtUtil;
import com.evoke.demo.employee.User;
import com.evoke.demo.employee.UserEntity;
import com.evoke.demo.employee.UserNotFoundException;
import com.evoke.demo.employee.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@Tag(name = "Employee Api Controller", description = "This Api provides operations for employee")
public class EmployeeController {
	@Autowired
	EmployeeService service;

	@Autowired
	ModelMapper mapper;

	@Autowired
	AuthenticationManager manager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostConstruct
	public void modelMapperConfigurationSetup() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	}

	@GetMapping("/")
	
	public ResponseEntity<String> welcome() {
		return ResponseEntity.status(HttpStatus.CREATED).body("Welcome home");
	}

	@PostMapping("/authenticate")

	public ResponseEntity<String> generateToken(@RequestBody User user) throws UserNotFoundException {
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		} catch (Exception ex) {
			throw new UserNotFoundException("inavalid username/password");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(jwtUtil.generateToken(user.getUserName()));
	}

	@PostMapping(path = "/users/save")
	
	@Operation(summary = "save-user", description = "This api operation saves user")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "creates user for registration") })
	public ResponseEntity<UserEntity> saveEmployee(@RequestBody User user) {

		UserEntity userEntity = service.saveUser(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);

	}

	@PostMapping(path = "/employees/save")
	@Operation(summary = "save-employee", description = "This api operation saves employee")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "creates employee") })
	public ResponseEntity<EmployeeEntity> saveEmployee(@RequestBody Employee employee) {

		EmployeeEntity employeeEntity = service.saveEmployee(employee);

		return ResponseEntity.status(HttpStatus.CREATED).body(employeeEntity);

	}

	@GetMapping(path = "/employees/retrieve")
	@Operation(summary = "fetches-all-employees", description = "This api operation fetches all employees")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "fetches all employees") })
	public ResponseEntity<List<EmployeeEntity>> getEmployees() {
		List<EmployeeEntity> employeeEntities = service.getEmployees();

		return ResponseEntity.status(HttpStatus.OK).body(employeeEntities);

	}

	@GetMapping(path = "/employees/retrieve/{id}")
	
	@Operation(summary = "fetches-employee", description = "This api operation fetches employee")
	@ApiResponses(value = { @ApiResponse(responseCode = "302", description = "employee found"),
			@ApiResponse(responseCode = "404", description = "employee not found") })
	public ResponseEntity<EmployeeEntity> getEmployee(@PathVariable(name = "id") Long id) {

		Optional<EmployeeEntity> employeeEntity = service.getEmployee(id);

		if (employeeEntity.isPresent())

			return ResponseEntity.status(HttpStatus.FOUND).body(employeeEntity.get());
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping(path = "/employees/update/{id}")
	@Operation(summary = "updates-employee", description = "This api operation updates employee")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "employee updated") })
	public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable(name = "id") Long id,
			@RequestBody Employee employee) {
		Optional<EmployeeEntity> employeeEntity = service.getEmployee(id);

		if (employeeEntity.isPresent()) {

			EmployeeEntity updatedEntity = service.updateEmployee(employeeEntity.get(), employee);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedEntity);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@DeleteMapping(path = "/employees/delete/{id}")
	@Operation(summary = "deletes-employee", description = "This api operation deletes employee")
	public void deleteEmployee(@PathVariable(name = "id") Long id) {
		Optional<EmployeeEntity> employeeEntity = service.getEmployee(id);

		if (employeeEntity.isPresent()) {

			service.deleteEmployee(id, employeeEntity.get());

		}

	}

}
