package org.tutorial.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tutorial.model.DeptDO;
import org.tutorial.model.EmpDO;
import org.tutorial.service.DeptService;
import org.tutorial.service.EmpService;
import org.tutorial.service.impl.DeptServiceImpl;
import org.tutorial.service.impl.EmpServiceImpl;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        DeptService deptService = new DeptServiceImpl();
        List<DeptDO> deptDOs = deptService.getAll();
        req.setAttribute("deptDOs", deptDOs);

        EmpService empService = new EmpServiceImpl();
        List<EmpDO> empDOs = empService.getAll();
        req.setAttribute("empDOs", empDOs);

        RequestDispatcher successView = req.getRequestDispatcher("index.jsp");
        successView.forward(req, res);
    }

}
