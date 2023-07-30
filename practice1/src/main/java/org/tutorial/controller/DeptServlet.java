package org.tutorial.controller;

import java.io.IOException;
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
import org.tutorial.service.impl.DeptServiceImpl;

@WebServlet("/dept/dept.do")
public class DeptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("listAll".equals(action)) {
            setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/dept/listAll.jsp顯示使用
            RequestDispatcher successView = req.getRequestDispatcher("/dept/listAll.jsp");
            successView.forward(req, res);
        }

        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("listEmps_ByDeptno_A".equals(action) || "listEmps_ByDeptno_B".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數
                Integer deptno = Integer.valueOf(req.getParameter("deptno"));

                // 2.開始查詢資料
                DeptService deptService = new DeptServiceImpl();
                List<EmpDO> list = deptService.getEmpsByDeptno(deptno);

                // 3.查詢完成,準備轉交(Send the Success view)
                req.setAttribute("listEmps_ByDeptno", list);

                String url = null;
                if ("listEmps_ByDeptno_A".equals(action)) {
                    url = "/dept/listEmpsByDeptno.jsp"; // 轉交/dept/listEmpsByDeptno.jsp
                }
                else if ("listEmps_ByDeptno_B".equals(action)) {
                    url = "/dept/listAll.jsp"; // 轉交/dept/listAll.jsp
                }

                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/dept/listEmpsByDeptno.jsp或/dept/listAll.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher(url);
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }

        if ("getOne_For_Update_Dept".equals(action)) {
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數
                Integer deptno = Integer.valueOf(req.getParameter("deptno"));

                // 2.開始查詢資料
                DeptServiceImpl deptService = new DeptServiceImpl();
                DeptDO deptDO = deptService.getOneDept(deptno);

                // 3.查詢完成,準備轉交(Send the Success view)
                req.setAttribute("deptDO", deptDO);
                RequestDispatcher successView = req.getRequestDispatcher("/dept/update.jsp"); // 轉交/dept/update.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("無法取得要修改的資料: " + e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/dept/listAll.jsp");
                failureView.forward(req, res);
            }
        }

        if ("update".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數 - 輸入格式的錯誤處理
                Integer deptno = Integer.valueOf(req.getParameter("deptno").trim());

                String dname = req.getParameter("dname");
                if (dname == null || dname.trim().length() == 0) {
                    errorMsgs.add("部門名稱: 請勿空白");
                }
                // 以下練習正則(規)表示式(regular-expression)
                String dnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
                if (!dname.trim().matches(dnameReg)) {
                    errorMsgs.add("部門名稱: 只能是中、英文字母、數字和_，且長度必需在2到10之間");
                }

                String loc = req.getParameter("loc");
                if (loc == null || loc.trim().length() == 0) {
                    errorMsgs.add("部門基地: 請勿空白");
                }

                DeptDO deptDO = new DeptDO();
                deptDO.setDeptno(deptno);
                deptDO.setDname(dname);
                deptDO.setLoc(loc);

                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("deptDO", deptDO); // 含有輸入格式錯誤的deptDO物件，也存入req
                    RequestDispatcher failureView = req.getRequestDispatcher("/dept/update.jsp");
                    failureView.forward(req, res);
                    return;
                }

                // 2.開始修改資料
                DeptServiceImpl deptService = new DeptServiceImpl();
                deptDO = deptService.update(deptno, dname, loc);

                // 3.修改完成,準備轉交(Send the Success view)
                req.setAttribute("deptDO", deptDO);
                RequestDispatcher successView = req.getRequestDispatcher("/dept/listOne.jsp"); // 轉交/dept/listOne.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("修改資料失敗: " + e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/dept/update.jsp");
                failureView.forward(req, res);
            }
        }

        if ("delete_Dept".equals(action)) {

            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                // 1.接收請求參數
                Integer deptno = Integer.valueOf(req.getParameter("deptno"));

                // 2.開始刪除資料
                DeptService deptService = new DeptServiceImpl();
                deptService.deleteDept(deptno);

                // 3.刪除完成,準備轉交(Send the Success view)
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/dept/listAll.jsp顯示使用
                RequestDispatcher successView = req.getRequestDispatcher("/dept/listAll.jsp"); // 轉交/dept/listAll.jsp
                successView.forward(req, res);

                // 其他可能的錯誤處理
            } catch (Exception e) {
                e.printStackTrace();
                errorMsgs.add("刪除資料失敗: " + e.getMessage());
                setDeptDOsRequestAttribute(req); // 查出所有部門存入req，供/dept/listAll.jsp顯示使用
                RequestDispatcher failureView = req.getRequestDispatcher("/dept/listAll.jsp");
                failureView.forward(req, res);
            }
        }

    }

    // 查出所有部門存入req，供/dept/listAll.jsp 或 /dept/listEmpsByDeptno.jsp 或 /dept/listAll.jsp 顯示使用
    // 但不推薦這種寫法，因為有 side effect 問題
    private void setDeptDOsRequestAttribute(HttpServletRequest req) {
        DeptService deptService = new DeptServiceImpl();
        List<DeptDO> deptDOs = deptService.getAll();
        req.setAttribute("deptDOs", deptDOs);
    }

}