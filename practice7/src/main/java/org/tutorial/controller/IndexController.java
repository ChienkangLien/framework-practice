package org.tutorial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.tutorial.model.PO.DeptPO;
import org.tutorial.model.PO.EmpPO;
import org.tutorial.model.VO.DeptVO;
import org.tutorial.model.VO.EmpVO;
import org.tutorial.service.DeptService;
import org.tutorial.service.EmpService;
import org.tutorial.utils.ConvertToVOUtil;

@Controller
public class IndexController {
	@Autowired
	private DeptService deptService;
	@Autowired
	EmpService empService;

	@GetMapping("/")
	public String index(Model model) {
		List<DeptPO> deptPOs = deptService.getAll();
		List<DeptVO> deptVOs = ConvertToVOUtil.convertToDeptVOList(deptPOs);
		System.out.println(deptVOs);
		model.addAttribute("deptVOs", deptVOs);

		List<EmpPO> empPOs = empService.getAll();
		List<EmpVO> empVOs = ConvertToVOUtil.convertToEmpVOList(empPOs);
		System.out.println(empVOs);
		model.addAttribute("empVOs", empVOs);

		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

}
