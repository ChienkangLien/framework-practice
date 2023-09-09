package org.tutorial.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tutorial.model.DTO.DeptDTO;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.service.DeptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j // lombok的功能、簡化宣告
@RestController
@RequestMapping("/depts") // 更改為複數形式，表示部門資源的集合
@Api(tags = "Dept API") // 添加API的描述信息
public class DeptController {
//	 private static final Logger log = LoggerFactory.getLogger(DeptController.class);
	
	@Autowired
	private DeptService deptService;

	@GetMapping // 不需要指定路徑，表示獲取所有部門
	@ApiOperation(value = "Get all departments", notes = "Retrieve a list of all departments")
	public ResponseEntity<List<DeptDTO>> getAllDepts() {
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		return ResponseEntity.ok(deptService.getAll());
	}

	@GetMapping("/{deptno}") // 使用部門編號作為路徑參數
	@ApiOperation(value = "Get a department by ID", notes = "Retrieve a department by its unique ID")
	// @ApiParam不寫的話默認required = true
	public ResponseEntity<DeptDTO> getDept(
			 @PathVariable Integer deptno) { // @ApiParam不寫的話默認required
																											// = true
		return ResponseEntity.ok(deptService.getOneDept(deptno));
	}

	@PostMapping // 不需要指定路徑，表示創建新部門
	@ApiOperation(value = "Create a new department", notes = "Create a new department resource")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Department created") }) // 自訂狀態描述
	public ResponseEntity<DeptDTO> createDept(@RequestBody DeptPO deptPO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(deptService.insert(deptPO));
	}

	@PutMapping("/{deptno}") // 使用部門編號作為路徑參數，表示更新特定部門
	@ApiOperation(value = "Update a department", notes = "Update an existing department resource")
	public ResponseEntity<DeptDTO> updateDept(
			 @PathVariable Integer deptno,
			@RequestBody DeptPO deptPO) {
		// 在實際應用中，您可能需要檢查deptno是否匹配deptPO中的部門編號
		return ResponseEntity.ok(deptService.update(deptPO));
	}

	@DeleteMapping("/{deptno}") // 使用部門編號作為路徑參數，表示刪除特定部門
	@ApiOperation(value = "Delete a department", notes = "Delete an existing department resource")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Department deleted"), })
	public ResponseEntity<Void> deleteDept(
			@ApiParam(value = "部門編號", required = true, defaultValue = "0") @PathVariable Integer deptno) {
		deptService.deleteDept(deptno);
		return ResponseEntity.noContent().build();
	}
}