package org.tutorial.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tutorial.model.DTO.EmpDTO;
import org.tutorial.model.Enum.SortField;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.service.EmpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/emps")
@Api(tags = "Employee API") // 添加API的描述信息
public class EmpController {
	@Autowired
	EmpService empService;

	@GetMapping
	@ApiOperation(value = "Get all employees", notes = "Retrieve a list of all employees")
	public ResponseEntity<List<EmpDTO>> getAllEmployees() {
		return ResponseEntity.ok(empService.getAll());
	}

	@GetMapping("/{empno}")
	@ApiOperation(value = "Get an employee by ID", notes = "Retrieve an employee by their unique ID")
	public ResponseEntity<EmpDTO> getEmployee(
			 @PathVariable Integer empno) {
		return ResponseEntity.ok(empService.getOneEmp(empno));
	}

	@PostMapping
	@ApiOperation(value = "Create an employee", notes = "Create a new employee resource")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Employee created") }) // 自訂狀態描述
	public ResponseEntity<EmpDTO> createEmployee(@RequestBody EmpPO empPO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(empService.addEmp(empPO));
	}

	@PutMapping("/{empno}")
	@ApiOperation(value = "Update an employee", notes = "Update an existing employee resource")
	public ResponseEntity<EmpDTO> updateEmployee(
			 @PathVariable Integer empno,
			@RequestBody EmpPO empPO) {
		// 在實際應用中，可能需要檢查empno是否匹配empPO中的員工編號
		return ResponseEntity.ok(empService.updateEmp(empPO));
	}

	@DeleteMapping("/{empno}")
	@ApiOperation(value = "Delete an employee", notes = "Delete an existing employee resource")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Employee deleted"), })
	public void deleteEmployee(
			 @PathVariable Integer empno) {
		empService.deleteEmp(empno);
	}

	@GetMapping("/dept/{deptno}")
	@ApiOperation(value = "Get employees by department", notes = "Retrieve a list of employees in a specific department")
	public ResponseEntity<List<EmpDTO>> getEmployeesByDept(
			 @PathVariable Integer deptno) {
		return ResponseEntity.ok(empService.getEmpsByDeptno(deptno));
	}

	@GetMapping("/page")
	@ApiOperation(value = "Get employees by page and sort", notes = "Retrieve a paginated and sorted list of employees")
	public ResponseEntity<Page<EmpDTO>> getEmployeesByPageAndSort(
			 @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			 @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
			 @RequestParam(name = "sortField", defaultValue = "EMP_NO") SortField sortField,
			 @RequestParam(name = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection) {
		return ResponseEntity
				.ok(empService.getEmpsByPageAndSort(pageNumber, pageSize, sortField.getValue(), sortDirection));
	}

	@GetMapping("/exportToExcel")
	@ApiOperation(value = "Export employees to Excel", notes = "Exports all employees to an Excel file.")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");

        empService.exportEmployeesToExcel(response.getOutputStream());
    }
}
