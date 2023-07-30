package org.tutorial.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tutorial.dao.DeptDAO;
import org.tutorial.model.DeptDO;
import org.tutorial.model.EmpDO;

public class DeptDAOImpl implements DeptDAO {
	String driver = "com.mysql.cj.jdbc.Driver";
	// 資料庫若不允許公鑰搜尋，須加上allowPublicKeyRetrieval=true
    String url = "jdbc:mysql://localhost:3306/practice?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    String userid = "root";
    String passwd = "password";

    private static final String INSERT_STMT = "INSERT INTO dept2 (dname,loc) VALUES (?, ?)";
    private static final String GET_ALL_STMT = "SELECT deptno , dname, loc FROM dept2";
    private static final String GET_ONE_STMT = "SELECT deptno , dname, loc FROM dept2 where deptno = ?";
    private static final String GET_Emps_ByDeptno_STMT = "SELECT empno,ename,job,hiredate,sal,comm,deptno FROM emp2 where deptno = ? order by empno";

    private static final String DELETE_EMPs = "DELETE FROM emp2 where deptno = ?";
    private static final String DELETE_DEPT = "DELETE FROM dept2 where deptno = ?";

    private static final String UPDATE = "UPDATE dept2 set dname=?, loc=? where deptno = ?";

    @Override
    public void insert(DeptDO deptDO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1, deptDO.getDname());
            pstmt.setString(2, deptDO.getLoc());

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public void update(DeptDO deptDO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, deptDO.getDname());
            pstmt.setString(2, deptDO.getLoc());
            pstmt.setInt(3, deptDO.getDeptno());

            pstmt.executeUpdate();

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public void delete(Integer deptno) {
        int updateCount_EMPs = 0;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);

            // 1.設定於 pstm.executeUpdate()之前
            con.setAutoCommit(false);

            // 先刪除員工
            pstmt = con.prepareStatement(DELETE_EMPs);
            pstmt.setInt(1, deptno);
            updateCount_EMPs = pstmt.executeUpdate();
            // 再刪除部門
            pstmt = con.prepareStatement(DELETE_DEPT);
            pstmt.setInt(1, deptno);
            pstmt.executeUpdate();

            // 2.設定於 pstm.executeUpdate()之後
            con.commit();
            con.setAutoCommit(true);
            System.out.println("刪除部門編號" + deptno + "時,共有員工" + updateCount_EMPs
                    + "人同時被刪除");

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            if (con != null) {
                try {
                    // 3.設定於當有exception發生時之catch區塊內
                    con.rollback();
                } catch (SQLException excep) {
                    throw new RuntimeException("rollback error occured. "
                            + excep.getMessage());
                }
            }
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }

    }

    @Override
    public DeptDO findByPrimaryKey(Integer deptno) {

        DeptDO deptDO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, deptno);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // deptDO 也稱為 Domain objects
                deptDO = new DeptDO();
                deptDO.setDeptno(rs.getInt("deptno"));
                deptDO.setDname(rs.getString("dname"));
                deptDO.setLoc(rs.getString("loc"));
            }

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
            // Clean up JDBC resources
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return deptDO;
    }

    @Override
    public List<DeptDO> getAll() {
        List<DeptDO> list = new ArrayList<DeptDO>();
        DeptDO deptDO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                deptDO = new DeptDO();
                deptDO.setDeptno(rs.getInt("deptno"));
                deptDO.setDname(rs.getString("dname"));
                deptDO.setLoc(rs.getString("loc"));
                list.add(deptDO); // Store the row in the list
            }

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }

    @Override
    public List<EmpDO> getEmpsByDeptno(Integer deptno) {
        List<EmpDO> list = new ArrayList<EmpDO>();
        EmpDO empDO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_Emps_ByDeptno_STMT);
            pstmt.setInt(1, deptno);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                empDO = new EmpDO();
                empDO.setEmpno(rs.getInt("empno"));
                empDO.setEname(rs.getString("ename"));
                empDO.setJob(rs.getString("job"));
                empDO.setHiredate(rs.getDate("hiredate").toLocalDate());
                empDO.setSal(rs.getDouble("sal"));
                empDO.setComm(rs.getDouble("comm"));
                empDO.setDeptno(rs.getInt("deptno"));
                list.add(empDO); // Store the row in the vector
            }

            // Handle any driver errors
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. "
                    + e.getMessage());
            // Handle any SQL errors
        } catch (SQLException se) {
            throw new RuntimeException("A database error occured. "
                    + se.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
    
}
