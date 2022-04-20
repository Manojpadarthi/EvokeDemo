package com.evoke.demo.employee.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.evoke.demo.employee.Employee;
import com.evoke.demo.employee.EmployeeEntity;
import com.evoke.demo.employee.User;
import com.evoke.demo.employee.UserEntity;
import com.evoke.demo.employee.dao.DepartmentRepository;
import com.evoke.demo.employee.dao.EmployeeRepository;
import com.evoke.demo.employee.dao.UserRepository;
import com.evoke.demo.employee.DepartmentEntity;

@Service
public class EmployeeService {
	Logger log = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	EmployeeRepository repository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	DepartmentRepository depRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void modelMapperConfigurationSetup() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	}

	@Transactional
	public EmployeeEntity saveEmployee(Employee employee) {

		DepartmentEntity department = new DepartmentEntity();
		department.setDepartmentName(employee.getDepartmentName());
		department.setDepartmentCode(employee.getDepartmentName());
		DepartmentEntity departmentEntity = depRepository.save(department);

		EmployeeEntity empEntity = new EmployeeEntity();
		empEntity.setName(employee.getName());
		empEntity.setEmail(employee.getEmail());
		empEntity.setPhone(employee.getPhone());
		empEntity.setCreatedBy(employee.getCreatedBy());
		empEntity.setCreatedOn(new Date());

		empEntity.setDepartmentId(departmentEntity.getId());

		return repository.save(empEntity);

	}

	public UserEntity saveUser(User user) {

		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(user.getEmail());
		userEntity.setUserName(user.getUserName());

		userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		return userRepository.save(userEntity);

	}

	public List<EmployeeEntity> getEmployees() {
		return repository.findAll();

	}

	@Cacheable(value = "tenSecondsCache", key = "#id")
	public Optional<EmployeeEntity> getEmployee(Long id) {

		log.info("Calling service layer");

		return repository.findById(id);

	}

	@Transactional
	public void deleteEmployee(Long id, EmployeeEntity entity) {

		depRepository.deleteById(entity.getDepartmentId());
		repository.deleteById(entity.getId());

	}

	@Transactional
	public EmployeeEntity updateEmployee(EmployeeEntity entity, Employee employee) {

		DepartmentEntity department = depRepository.getById(entity.getDepartmentId());
		department.setDepartmentName(employee.getDepartmentName());
		department.setDepartmentCode(employee.getDepartmentName());
		depRepository.save(department);

		entity.setName(employee.getName());
		entity.setEmail(employee.getEmail());
		entity.setPhone(employee.getPhone());
		entity.setCreatedBy(employee.getCreatedBy());
		entity.setCreatedOn(new Date());

		return repository.save(entity);

	}

}
