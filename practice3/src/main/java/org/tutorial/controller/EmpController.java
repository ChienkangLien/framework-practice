package org.tutorial.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;
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

	// 加入VO概念，DO主要關注數據的存儲和持久化，而VO主要關注數據在業務邏輯層或展示層的傳遞和使用
	@GetMapping("/listAll")
	public String listAll(Model model) {
		setDeptVOsAndEmpVOsRequestAttribute(model);
		return "emp/listAll";
	}

	@GetMapping("/add")
	public String add(Model model) {
		setDeptVOsRequestAttribute(model);
		return "emp/add";
	}

	@PostMapping("/getOne_For_Display")
	public String getOneForDisplay(Model model, @ModelAttribute("empVO") EmpVO empVO,
			BindingResult bindingResult) {
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

		EmpDO empDO = empService.getOneEmp(empVO.getEmpno());
		if (empDO == null) {
			errorMsgs.add("查無資料");
			model.addAttribute("errorMsgs", errorMsgs);
			setDeptVOsAndEmpVOsRequestAttribute(model);
			return "index";
		}else {
			model.addAttribute("empVO", ConvertToVOUtil.convertToEmpVO(empDO));
		}

		return "emp/listOne";
	}

	@PostMapping("/getOne_For_Update")
	public String getOneForUpdate(Model model, @RequestParam Integer empno) {
		setupErrorMessages(model);

		EmpDO empDO = empService.getOneEmp(empno);
		model.addAttribute("empVO", ConvertToVOUtil.convertToEmpVO(empDO));
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
					}else if (error.getField().equals("sal")) {
						// 自訂錯誤訊息
						errorMsgs.add("薪水請填數字");
					}else if (error.getField().equals("comm")) {
						// 自訂錯誤訊息
						errorMsgs.add("獎金請填數字");
					}
				}else {
					errorMsgs.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("empVO", empVO);
			setDeptVOsRequestAttribute(model);
			model.addAttribute("errorMsgs", errorMsgs);
			return "emp/update";
		}

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptVO.class, DeptDO.class);
		EmpDO empDO = modelMapper.map(empVO, EmpDO.class);
		DeptDO deptDO = modelMapper.map(empVO.getDeptVO(), DeptDO.class);
		empDO.setDeptDO(deptDO);
		
		empService.updateEmp(empDO);
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
					}else if (error.getField().equals("sal")) {
						// 自訂錯誤訊息
						errorMsgs.add("薪水請填數字");
					}else if (error.getField().equals("comm")) {
						// 自訂錯誤訊息
						errorMsgs.add("獎金請填數字");
					}
				}else {
					errorMsgs.add(error.getDefaultMessage());
				}
			}
			model.addAttribute("empVO", empVO);
			setDeptVOsRequestAttribute(model);
			model.addAttribute("errorMsgs", errorMsgs);
			return "emp/add";
		}
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(DeptVO.class, DeptDO.class);
		EmpDO empDO = modelMapper.map(empVO, EmpDO.class);
		DeptDO deptDO = modelMapper.map(empVO.getDeptVO(), DeptDO.class);
		empDO.setDeptDO(deptDO);
		
		empService.addEmp(empDO);
		model.addAttribute("empVO", empVO);
		setDeptVOsAndEmpVOsRequestAttribute(model);
		
		return "emp/listAll";
	}

	@PostMapping("/delete")
	public String delete(Model model, @RequestParam Integer empno) {
		setupErrorMessages(model);

		empService.deleteEmp(empno);
		setDeptVOsAndEmpVOsRequestAttribute(model);
		
		return "emp/listAll";
	}

	private void setDeptVOsAndEmpVOsRequestAttribute(Model model) {
		List<DeptDO> deptDOs = deptService.getAll();
		model.addAttribute("deptVOs", ConvertToVOUtil.convertToDeptVOList(deptDOs));

		List<EmpDO> empDOs = empService.getAll();
		model.addAttribute("empVOs", ConvertToVOUtil.convertToEmpVOList(empDOs));
	}

	private void setDeptVOsRequestAttribute(Model model) {
		List<DeptDO> deptDOs = deptService.getAll();
		model.addAttribute("deptVOs", ConvertToVOUtil.convertToDeptVOList(deptDOs));
	}

	private void setupErrorMessages(Model model) {
		List<String> errorMsgs = new LinkedList<>();
		model.addAttribute("errorMsgs", errorMsgs);
	}

}
