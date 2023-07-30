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
import org.tutorial.service.DeptService;
import org.tutorial.service.EmpService;
import org.tutorial.utils.ConvertToVOUtil;

@Controller
@RequestMapping("/dept")
public class DeptController  {
	@Autowired
	private DeptService deptService;
	@Autowired
	EmpService empService;

	// 加入VO概念，DO主要關注數據的存儲和持久化，而VO主要關注數據在業務邏輯層或展示層的傳遞和使用
	@GetMapping("/listAll")
    public String listAll(Model model) {
		setDeptVOsRequestAttribute(model);
		
        return "dept/listAll";
    }
	
	@PostMapping("/listEmps_ByDeptno_A")
	public String listEmpsByDeptnoA(Model model,@RequestParam Integer deptno) {
		setupErrorMessages(model);
	    
		List<EmpDO> empDOs = deptService.getEmpsByDeptno(deptno);
		model.addAttribute("listEmps_ByDeptno", ConvertToVOUtil.convertToEmpVOList(empDOs));
		setDeptVOsRequestAttribute(model);
		
		return "dept/listEmpsByDeptno";
	}

	@PostMapping("/listEmps_ByDeptno_B")
	public String listEmpsByDeptnoB(Model model,@RequestParam Integer deptno) {
		setupErrorMessages(model);
	    
		List<EmpDO> empDOs = deptService.getEmpsByDeptno(deptno);
		model.addAttribute("listEmps_ByDeptno", ConvertToVOUtil.convertToEmpVOList(empDOs));
		setDeptVOsRequestAttribute(model);
		
		return "dept/listAll";
	}
	
	@PostMapping("/getOne_For_Update_Dept")
	public String getOneForUpdateDept(Model model,@RequestParam Integer deptno) {
		setupErrorMessages(model);
		
		DeptDO deptDO = deptService.getOneDept(deptno);
		model.addAttribute("deptVO", ConvertToVOUtil.convertToDeptVO(deptDO));
		
		return "dept/update";
	}
	
	// @Valid用於表單驗證，甚至可以創建formEntity來存儲
	// 驗證有誤透過BindingResult操作
	// 中文亂碼處理 -> produces
	@PostMapping(value = "/update", produces = "text/html;charset=UTF-8")
	public String update(Model model,@Valid @ModelAttribute("deptVO") DeptVO deptVO, BindingResult bindingResult) {
		setupErrorMessages(model);
		
		if (bindingResult.hasErrors()) {
	        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
	        List<String> errorMsgs = new LinkedList<>();
	        for (FieldError fieldError : fieldErrors) {
	            errorMsgs.add(fieldError.getDefaultMessage());
	        }
	        
	        model.addAttribute("deptVO", deptVO);
	        model.addAttribute("errorMsgs", errorMsgs);
	        return "/dept/update";
	    }
		
		ModelMapper modelMapper = new ModelMapper();
		deptService.update(modelMapper.map(deptVO, DeptDO.class));
		model.addAttribute("deptVO", deptVO);
		
		return "/dept/listOne";
	}
	
	@PostMapping("/delete_Dept")
	public String deleteDept(Model model,@RequestParam Integer deptno) {
		setupErrorMessages(model);
		
		deptService.deleteDept(deptno);
		setDeptVOsRequestAttribute(model);
		
		return "/dept/listAll";
	}

    // 加上@ModelAttribute的方法會在每個請求處理方法執行之前被調用
    // Spring MVC會自動調用帶有@ModelAttribute注解的方法，並將其返回的屬性添加到模型中。
	// 但這邊不適合用、不是每個請求都需要套用
//    @ModelAttribute
    public void setDeptVOsRequestAttribute(Model model) {
        List<DeptDO> deptDOs = deptService.getAll();
        model.addAttribute("deptVOs", ConvertToVOUtil.convertToDeptVOList(deptDOs));
    }
    
    private void setupErrorMessages(Model model) {
        List<String> errorMsgs = new LinkedList<>();
        model.addAttribute("errorMsgs", errorMsgs);
    }

}