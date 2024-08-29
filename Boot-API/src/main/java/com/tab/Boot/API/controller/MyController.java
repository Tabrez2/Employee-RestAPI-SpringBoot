package com.tab.Boot.API.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tab.Boot.API.dto.Employee;
import com.tab.Boot.API.dto.ResponseStructure;
import com.tab.Boot.API.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class MyController {
  @Autowired
  EmployeeRepository repository;
  @Autowired
  ResponseStructure<Employee> rs;
  @Autowired
  ResponseStructure<List<Employee>> rs2;

  @PostMapping("/employees")
  public ResponseEntity<ResponseStructure<Employee>> saveEmployee(@RequestBody Employee employee) {
    if (repository.existsByMob(employee.getMob())) {
      rs.setMsg("Data Already Exists");
      rs.setData(null);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.BAD_REQUEST);
    } else {
      repository.save(employee);
      rs.setMsg("Data Saved Success");
      rs.setData(employee);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.CREATED);
    }
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<ResponseStructure<Employee>> findEmployee(@PathVariable int id) {
    Employee employee = repository.findById(id).orElse(null);
    if (employee == null) {
      rs.setMsg("Data is not found");
      rs.setData(null);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.NOT_FOUND);
    } else {
      rs.setMsg("Data found");
      rs.setData(employee);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/employees")
  public ResponseEntity<ResponseStructure<List<Employee>>> fetchAll() {
    List<Employee> employees = repository.findAll();
    if (employees.isEmpty()) {
      rs2.setMsg("Data Not Found");
      rs2.setData(null);
      return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.NOT_FOUND);
    } else {
      rs2.setMsg("Records Found ");
      rs2.setData(employees);

      return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.FOUND);

    }
  }

  @PostMapping("/employees/many")
  public ResponseEntity<ResponseStructure<List<Employee>>> saveAll(@RequestBody List<Employee> employees) {
    for (Employee employee : employees) {
      if (repository.existsByMob(employee.getMob())) {
        rs2.setMsg("Record Already Existed");
        rs2.setData(null);
        return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.BAD_REQUEST);
      }
    }
    repository.saveAll(employees);
    rs2.setData(employees);
    rs2.setMsg("All Records Saved");
    return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.CREATED);

  }

  @GetMapping("/employees/name/{name}")
  public ResponseEntity<ResponseStructure<List<Employee>>> fetchByName(@PathVariable String name) {
    List<Employee> list = repository.findByName(name);
    if (list.isEmpty()) {
      rs2.setMsg("Data Not Found");
      rs2.setData(null);
      return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.NOT_FOUND);
    } else {
      rs2.setMsg("Data Found");
      rs2.setData(list);
      return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.FOUND);
    }
  }

  @GetMapping("/employees/mob/{mob}")
  public ResponseEntity<ResponseStructure<Employee>> fetchByMob(@PathVariable long mob) {
    Employee employee = repository.findByMob(mob);
    if (employee == null) {
      rs.setMsg("Record Not Found");
      rs.setData(null);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.NOT_FOUND);
    } else {
      rs.setMsg("Record Found");
      rs.setData(employee);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.FOUND);
    }
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<ResponseStructure<Employee>> deleteById(@PathVariable int id) {
    Employee employee = repository.findById(id).orElse(null);
    if (employee == null) {
      rs.setMsg("Record Not Found");
      rs.setData(null);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.NOT_FOUND);
    } else {
      repository.deleteById(id);
      rs.setMsg("Record Removed");
      rs.setData(employee);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.OK);
    }
  }

}
