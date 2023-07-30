package org.tutorial.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.model.VO.DeptVO;
import org.tutorial.model.VO.EmpVO;
import org.tutorial.service.DeptService;
import org.tutorial.service.EmpService;
import org.tutorial.utils.ConvertToVOUtil;

@Controller
@RequestMapping("/emp")
public class EmpController {
	@Autowired
	private DeptService deptService;
	@Autowired
	EmpService empService;

	int defaultPageValue = 0;
	int defaultSizeValue = 5;
	String defaultSortFieldAndDirectionValue = "empno_ASC";

	// 加入VO概念，PO主要關注數據的存儲和持久化，而VO主要關注數據在業務邏輯層或展示層的傳遞和使用
	@GetMapping("/listAll")
	public String listAll(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "empno_ASC") String sortFieldAndDirection) {

		setDeptVOsAndEmpVOsRequestAttribute(model, page, size, sortFieldAndDirection);
		return "emp/listAll";
	}

	@GetMapping("/add")
	public String add(Model model) {
		setDeptVOsRequestAttribute(model);
		return "emp/add";
	}

	@PostMapping("/getOne_For_Display")
	public String getOneForDisplay(Model model, @ModelAttribute("empVO") EmpVO empVO, BindingResult bindingResult) {
		setupErrorMessages(model);
		List<String> errorMsgs = new LinkedList<>();

		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError error : fieldErrors) {
				if (error.getField().equals("empno")) {
					if (error.getCode().equals("typeMismatch")) {
						// 自訂錯誤訊息
						errorMsgs.add("員工編號格式不正確");
					}
				}
			}
			model.addAttribute("errorMsgs", errorMsgs);
			setDeptVOsAndEmpVOsRequestAttribute(model);
			return "index";
		}

		if (empVO.getEmpno() == null) {
			errorMsgs.add("請輸入員工編號");
			model.addAttribute("errorMsgs", errorMsgs);
			setDeptVOsAndEmpVOsRequestAttribute(model);
			return "index";
		}

		EmpPO empPO = empService.getOneEmp(empVO.getEmpno());
		if (empPO == null) {
			errorMsgs.add("查無資料");
			model.addAttribute("errorMsgs", errorMsgs);
			setDeptVOsAndEmpVOsRequestAttribute(model);
			return "index";
		} else {
			model.addAttribute("empVO", ConvertToVOUtil.convertToEmpVO(empPO));
		}

		return "emp/listOne";
	}

	@PostMapping("/getOne_For_Update")
	public String getOneForUpdate(Model model, @RequestParam Integer empno) {
		setupErrorMessages(model);

		EmpPO empPO = empService.getOneEmp(empno);
		model.addAttribute("empVO", ConvertToVOUtil.convertToEmpVO(empPO));
		setDeptVOsRequestAttribute(model);

		return "emp/update";
	}

	@PostMapping("/update")
	public String update(Model model, @Valid @ModelAttribute EmpVO empVO, BindingResult bindingResult) {
		setupErrorMessages(model);
		List<String> errorMsgs = new LinkedList<>();

		// 處理驗證錯誤
		// 手動檢查，針對VO中屬性的轉型錯誤
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				// 前端input type="date"，默認是String格式
				// 要在WebConfig配置Converter
				if (error.getCode().equals("typeMismatch")) {
					if (error.getField().equals("hiredate")) {
						// 自訂錯誤訊息
						errorMsgs.add("請輸入日期");
					} else if (error.getField().equals("sal")) {
						// 自訂錯誤訊息
						errorMsgs.add("薪水請填數字");
					} else if (error.getField().equals("comm")) {
						// 自訂錯誤訊息
						errorMsgs.add("獎金請填數字");
					}
				} else {
					errorMsgs.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("empVO", empVO);
			setDeptVOsRequestAttribute(model);
			model.addAttribute("errorMsgs", errorMsgs);
			return "emp/update";
		}

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptVO.class, DeptPO.class);
		EmpPO empPO = modelMapper.map(empVO, EmpPO.class);
		DeptPO deptPO = modelMapper.map(empVO.getDeptVO(), DeptPO.class);
		empPO.setDeptPO(deptPO);

		empService.updateEmp(empPO);
		model.addAttribute("empVO", empVO);
		setDeptVOsRequestAttribute(model);

		return "emp/listOne";
	}

	@PostMapping("/insert")
	public String insert(Model model, @Valid @ModelAttribute EmpVO empVO, BindingResult bindingResult) {
		setupErrorMessages(model);
		List<String> errorMsgs = new LinkedList<>();

		if (bindingResult.hasErrors()) {
			// 處理驗證錯誤
			for (FieldError error : bindingResult.getFieldErrors()) {
				if (error.getCode().equals("typeMismatch")) {
					if (error.getField().equals("hiredate")) {
						// 自訂錯誤訊息
						errorMsgs.add("請輸入日期");
					} else if (error.getField().equals("sal")) {
						// 自訂錯誤訊息
						errorMsgs.add("薪水請填數字");
					} else if (error.getField().equals("comm")) {
						// 自訂錯誤訊息
						errorMsgs.add("獎金請填數字");
					}
				} else {
					errorMsgs.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("empVO", empVO);
			setDeptVOsRequestAttribute(model);
			model.addAttribute("errorMsgs", errorMsgs);
			return "emp/add";
		}

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptVO.class, DeptPO.class);
		EmpPO empPO = modelMapper.map(empVO, EmpPO.class);
		DeptPO deptPO = modelMapper.map(empVO.getDeptVO(), DeptPO.class);
		empPO.setDeptPO(deptPO);

		empService.addEmp(empPO);
		model.addAttribute("empVO", empVO);
		setDeptVOsAndEmpVOsRequestAttribute(model, defaultPageValue, defaultSizeValue,
				defaultSortFieldAndDirectionValue);

		return "emp/listAll";
	}

	@PostMapping("/delete")
	public String delete(Model model, @RequestParam Integer empno, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "empno_ASC") String sortFieldAndDirection) {
		setupErrorMessages(model);

		empService.deleteEmp(empno);
		setDeptVOsAndEmpVOsRequestAttribute(model, page, size, sortFieldAndDirection);

		return "emp/listAll";
	}
	
	private void setDeptVOsAndEmpVOsRequestAttribute(Model model) {
		List<DeptPO> deptPOs = deptService.getAll();
		model.addAttribute("deptVOs", ConvertToVOUtil.convertToDeptVOList(deptPOs));

		List<EmpPO> empPOs = empService.getAll();
		model.addAttribute("empVOs", ConvertToVOUtil.convertToEmpVOList(empPOs));
	}

	private void setDeptVOsAndEmpVOsRequestAttribute(Model model, int pageNumber, int pageSize,
			String sortFieldAndDirection) {
		List<DeptPO> deptPOs = deptService.getAll();
		String[] sortFieldAndDirectionArray = sortFieldAndDirection.split("_");

		String sortField = sortFieldAndDirectionArray[0];
		Sort.Direction sortDirection = Sort.Direction.fromString(sortFieldAndDirectionArray[1]);

		model.addAttribute("deptVOs", ConvertToVOUtil.convertToDeptVOList(deptPOs));

		Page<EmpPO> empPage = empService.getEmpsByPageAndSort(pageNumber, pageSize, sortField, sortDirection);
		model.addAttribute("empVOs", ConvertToVOUtil.convertToEmpVOList(empPage.getContent()));
		model.addAttribute("page", empPage);

		model.addAttribute("sortFieldAndDirection", sortFieldAndDirection);
	}

	private void setDeptVOsRequestAttribute(Model model) {
		List<DeptPO> deptPOs = deptService.getAll();
		model.addAttribute("deptVOs", ConvertToVOUtil.convertToDeptVOList(deptPOs));
	}

	private void setupErrorMessages(Model model) {
		List<String> errorMsgs = new LinkedList<>();
		model.addAttribute("errorMsgs", errorMsgs);
	}

}
