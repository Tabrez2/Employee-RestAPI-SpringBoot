package com.tab.Boot.API.controller;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tab.Boot.API.dto.Employee;
import com.tab.Boot.API.dto.ResponseStructure;
import com.tab.Boot.API.exception.DataExistsException;
import com.tab.Boot.API.exception.DataNotFoundException;
import com.tab.Boot.API.repository.EmployeeRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Employee Management", description = "API for managing employees")
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
      throw new DataExistsException("Data Is Repeated with : " + employee.getMob());
    } else {
      repository.save(employee);
      rs.setMsg("Data Saved Success");
      rs.setData(employee);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.CREATED);
    }
  }

  @GetMapping("/employees/{id}")
  public ResponseEntity<ResponseStructure<Employee>> findEmployee(@PathVariable int id) {
    Employee employee = repository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Data Not Found with id :" + id));

    rs.setMsg("Data Found");
    rs.setData(employee);
    return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.FOUND);

  }

  @GetMapping("/employees")
  public ResponseEntity<ResponseStructure<List<Employee>>> fetchAll() {
    List<Employee> list = repository.findAll();
    if (list.isEmpty()) {
      throw new DataNotFoundException();
    } else {
      rs2.setMsg("Records Found ");
      rs2.setData(list);

      return new ResponseEntity<ResponseStructure<List<Employee>>>(rs2, HttpStatus.FOUND);

    }
  }

  @PostMapping("/employees/many")
  public ResponseEntity<ResponseStructure<List<Employee>>> saveAll(@RequestBody List<Employee> employees) {
    for (Employee employee : employees) {
      if (repository.existsByMob(employee.getMob())) {
        throw new DataExistsException("Data Is Repeated with : " + employee.getMob());

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
      throw new DataNotFoundException("Data Not Found with the name :" + name);
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
      throw new DataNotFoundException("Data Not Found with the mobile no. :" + mob);
    } else {
      rs.setMsg("Record Found");
      rs.setData(employee);
      return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.FOUND);
    }
  }

  @DeleteMapping("/employees/{id}")
  public ResponseEntity<ResponseStructure<Employee>> deleteById(@PathVariable int id) {
    Employee employee = repository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Data Not Found with id :" + id));

    repository.deleteById(id);
    rs.setMsg("Record Removed");
    rs.setData(employee);
    return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.OK);

  }

  @PutMapping("/employees")
  public ResponseEntity<ResponseStructure<Employee>> putUpdate(@RequestBody Employee employee) {

    repository.save(employee);
    rs.setMsg("Data Updated");
    rs.setData(employee);
    return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.OK);
  }

  @PatchMapping("/employees/{id}")
  public ResponseEntity<ResponseStructure<Employee>> patchById(@RequestBody Employee employee, @PathVariable int id) {

    Employee emp = repository.findById(id).orElseThrow(() -> new DataNotFoundException());

    if (employee.getName() != null)
      emp.setName(employee.getName());
    if (employee.getMob() != 0)
      emp.setMob(employee.getMob());

    repository.save(emp);
    rs.setMsg("Data Updated");
    rs.setData(employee);
    return new ResponseEntity<ResponseStructure<Employee>>(rs, HttpStatus.OK);

  }

}
// java home _>properties advance sys JAVA_HOME select jdk path -->jar file
// creation java -jar file address in cmd
// extract 7zip boot inf ->.classFIle
// how to change prop ,compressed the extract file 3 files archive to
// jar-->compression level-0
// we can not change jar file but we can change app-prop