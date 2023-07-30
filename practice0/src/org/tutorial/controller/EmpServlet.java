package org.tutorial.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
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

@WebServlet("/emp/emp.do")
public class EmpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("listAll".equals(action)) {
            setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/emp/listAll.jsp顯示使用
            RequestDispatcher successView = req.getRequestDispatcher("/emp/listAll.jsp");
            successView.forward(req, res);
            return;
        }

        if ("add".equals(action)) {
            setDeptDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/emp/add.jsp顯示使用
            RequestDispatcher successView = req.getRequestDispatcher("/emp/add.jsp");
            successView.forward(req, res);
            return;
        }

        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("getOne_For_Display".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數 - 輸入格式的錯誤處理
                String str = req.getParameter("empno");
                if (str == null || (str.trim()).length() == 0) {
                    errorMsgs.add("請輸入員工編號");
                }
                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/index.jsp顯示使用
                    RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
                    failureView.forward(req, res);
                    return;
                }

                Integer empno = null;
                try {
                    empno = new Integer(str);
                } catch (Exception e) {
                    errorMsgs.add("員工編號格式不正確");
                }
                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/index.jsp顯示使用
                    RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
                    failureView.forward(req, res);
                    return;
                }

                // 2.開始查詢資料
                EmpService empService = new EmpServiceImpl();
                EmpDO empDO = empService.getOneEmp(empno);
                if (empDO == null) {
                    errorMsgs.add("查無資料");
                }
                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/index.jsp顯示使用
                    RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
                    failureView.forward(req, res);
                    return;
                }

                // 3.查詢完成，準備轉交(Send the Success view)
                req.setAttribute("empDO", empDO);
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/listOne.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher("/emp/listOne.jsp"); // 轉交/emp/listOne.jsp
                successView.forward(req, res);


                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("無法取得資料: " + e.getMessage());
                setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/index.jsp顯示使用
                RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
                failureView.forward(req, res);
            }
        }


        if ("getOne_For_Update".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數
                Integer empno = new Integer(req.getParameter("empno"));

                // 2.開始查詢資料
                EmpService empService = new EmpServiceImpl();
                EmpDO empDO = empService.getOneEmp(empno);

                // 3.查詢完成，準備轉交(Send the Success view)
                req.setAttribute("empDO", empDO);
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/update.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher("/emp/update.jsp");// 轉交/emp/update.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("無法取得要修改的資料: " + e.getMessage());
                setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/emp/listAll.jsp顯示使用
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/listAll.jsp");
                failureView.forward(req, res);
            }
        }


        if ("update".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數 - 輸入格式的錯誤處理
                Integer empno = new Integer(req.getParameter("empno").trim());

                String ename = req.getParameter("ename");
                if (ename == null || ename.trim().length() == 0) {
                    errorMsgs.add("員工姓名: 請勿空白");
                }
                // 以下練習正則(規)表示式(regular-expression)
                String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
                if (!ename.trim().matches(enameReg)) {
                    errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_，且長度必需在2到10之間");
                }

                String job = req.getParameter("job").trim();
                LocalDate hiredate = null;
                try {
                    hiredate = LocalDate.parse(req.getParameter("hiredate").trim());
                } catch (IllegalArgumentException e) {
                    hiredate = LocalDate.now();
                    errorMsgs.add("請輸入日期");
                }

                Double sal = null;
                try {
                    sal = new Double(req.getParameter("sal").trim());
                } catch (NumberFormatException e) {
                    sal = 0.0;
                    errorMsgs.add("薪水請填數字");
                }

                Double comm = null;
                try {
                    comm = new Double(req.getParameter("comm").trim());
                } catch (NumberFormatException e) {
                    comm = 0.0;
                    errorMsgs.add("獎金請填數字");
                }

                Integer deptno = new Integer(req.getParameter("deptno").trim());

                EmpDO empDO = new EmpDO();
                empDO.setEmpno(empno);
                empDO.setEname(ename);
                empDO.setJob(job);
                empDO.setHiredate(hiredate);
                empDO.setSal(sal);
                empDO.setComm(comm);
                empDO.setDeptno(deptno);

                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("empDO", empDO); // 含有輸入格式錯誤的empDO物件，也存入req
                    setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/update.jsp顯示使用
                    RequestDispatcher failureView = req.getRequestDispatcher("/emp/update.jsp");
                    failureView.forward(req, res);
                    return;
                }

                // 2.開始修改資料
                EmpService empService = new EmpServiceImpl();
                empDO = empService.updateEmp(empno, ename, job, hiredate, sal, comm, deptno);

                // 3.修改完成,準備轉交(Send the Success view)
                req.setAttribute("empDO", empDO);
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/listOne.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher("/emp/listOne.jsp"); // 轉交/emp/listOne.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("修改資料失敗:" + e.getMessage());
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/update.jsp顯示使用
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/emp/update.jsp");
                failureView.forward(req, res);
            }
        }

        if ("insert".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數 - 輸入格式的錯誤處理
                String ename = req.getParameter("ename");
                if (ename == null || ename.trim().length() == 0) {
                    errorMsgs.add("員工姓名: 請勿空白");
                }
                // 以下練習正則(規)表示式(regular-expression)
                String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
                if (!ename.trim().matches(enameReg)) {
                    errorMsgs.add("員工姓名:只能是中、英文字母、數字和_，且長度必需在2到10之間");
                }

                String job = req.getParameter("job").trim();
                LocalDate hiredate = null;
                try {
                    hiredate = LocalDate.parse(req.getParameter("hiredate").trim());
                } catch (IllegalArgumentException e) {
                    hiredate = LocalDate.now();
                    errorMsgs.add("請輸入日期");
                }

                Double sal = null;
                try {
                    sal = new Double(req.getParameter("sal").trim());
                } catch (NumberFormatException e) {
                    sal = 0.0;
                    errorMsgs.add("薪水請填數字");
                }

                Double comm = null;
                try {
                    comm = new Double(req.getParameter("comm").trim());
                } catch (NumberFormatException e) {
                    comm = 0.0;
                    errorMsgs.add("獎金請填數字");
                }

                Integer deptno = new Integer(req.getParameter("deptno").trim());

                EmpDO empDO = new EmpDO();
                empDO.setEname(ename);
                empDO.setJob(job);
                empDO.setHiredate(hiredate);
                empDO.setSal(sal);
                empDO.setComm(comm);
                empDO.setDeptno(deptno);

                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("empDO", empDO); // 含有輸入格式錯誤的empDO物件，也存入req
                    setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/add.jsp顯示使用
                    RequestDispatcher failureView = req.getRequestDispatcher("/emp/add.jsp");
                    failureView.forward(req, res);
                    return;
                }

                // 2.開始新增資料
                EmpService empService = new EmpServiceImpl();
                empService.addEmp(ename, job, hiredate, sal, comm, deptno);

                // 3.新增完成，準備轉交(Send the Success view)
                setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/emp/listAll.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher("/emp/listAll.jsp"); // 轉交/emp/listAll.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add(e.getMessage());
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/emp/add.jsp顯示使用
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/add.jsp");
                failureView.forward(req, res);
            }
        }


        if ("delete".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數
                Integer empno = new Integer(req.getParameter("empno"));

                // 2.開始刪除資料
                EmpService empService = new EmpServiceImpl();
                empService.deleteEmp(empno);

                // 3.刪除完成,準備轉交(Send the Success view)
                setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/emp/listAll.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher("/emp/listAll.jsp");// 轉交/emp/listAll.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("刪除資料失敗: " + e.getMessage());
                setDeptDOsAndEmpDOsRequestAttribute(req); // 查出所有部門及員工存入req，供/emp/listAll.jsp顯示使用
                RequestDispatcher failureView = req.getRequestDispatcher("/emp/listAll.jsp");
                failureView.forward(req, res);
            }
        }
    }

    // 查出所有部門及員工存入req，供 /index.jsp 或 /emp/listAll.jsp 畫面顯示使用
    // 但不推薦這種寫法，因為有 side effect 問題
    private void setDeptDOsAndEmpDOsRequestAttribute(HttpServletRequest req) {
        DeptService deptService = new DeptServiceImpl();
        List<DeptDO> deptDOs = deptService.getAll();
        req.setAttribute("deptDOs", deptDOs);

        EmpService empService = new EmpServiceImpl();
        List<EmpDO> empDOs = empService.getAll();
        req.setAttribute("empDOs", empDOs);
    }

    // 查出所有部門存入req，供 /emp/add.jsp 或 /emp/update.jsp 或 /emp/listOne.jsp 畫面顯示使用
    private void setDeptDOsRequestAttribute(HttpServletRequest req) {
        DeptService deptService = new DeptServiceImpl();
        List<DeptDO> deptDOs = deptService.getAll();
        req.setAttribute("deptDOs", deptDOs);
    }

}
