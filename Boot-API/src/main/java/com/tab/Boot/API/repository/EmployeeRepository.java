package com.tab.Boot.API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tab.Boot.API.dto.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  boolean existsByMob(long mob);

  List<Employee> findByName(String name);

  Employee findByMob(long mob);

}
