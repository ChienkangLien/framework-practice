package org.tutorial.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.tutorial.dao.EmpDAO;
import org.tutorial.model.EmpDO;

public class EmpDAOImpl implements EmpDAO {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:33060/practice";
    String userid = "sa";
    String passwd = "123456";

    private static final String INSERT_STMT = "INSERT INTO emp2 (ename,job,hiredate,sal,comm,deptno) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT = "SELECT empno,ename,job,hiredate,sal,comm,deptno FROM emp2 order by empno";
    private static final String GET_ONE_STMT = "SELECT empno,ename,job,hiredate,sal,comm,deptno FROM emp2 where empno = ?";
    private static final String DELETE = "DELETE FROM emp2 where empno = ?";
    private static final String UPDATE = "UPDATE emp2 set ename=?, job=?, hiredate=?, sal=?, comm=?, deptno=? where empno = ?";

    @Override
    public void insert(EmpDO empDO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1, empDO.getEname());
            pstmt.setString(2, empDO.getJob());
            pstmt.setDate(3, Date.valueOf(empDO.getHiredate()));
            pstmt.setDouble(4, empDO.getSal());
            pstmt.setDouble(5, empDO.getComm());
            pstmt.setInt(6, empDO.getDeptno());

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
    public void update(EmpDO empDO) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, empDO.getEname());
            pstmt.setString(2, empDO.getJob());
            pstmt.setDate(3, Date.valueOf(empDO.getHiredate()));
            pstmt.setDouble(4, empDO.getSal());
            pstmt.setDouble(5, empDO.getComm());
            pstmt.setInt(6, empDO.getDeptno());
            pstmt.setInt(7, empDO.getEmpno());

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
    public void delete(Integer empno) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setInt(1, empno);

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
    public EmpDO findByPrimaryKey(Integer empno) {

        EmpDO empDO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, empno);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                // empDO 也稱為 Domain objects
                empDO = new EmpDO();
                empDO.setEmpno(rs.getInt("empno"));
                empDO.setEname(rs.getString("ename"));
                empDO.setJob(rs.getString("job"));
                empDO.setHiredate(rs.getDate("hiredate").toLocalDate());
                empDO.setSal(rs.getDouble("sal"));
                empDO.setComm(rs.getDouble("comm"));
                empDO.setDeptno(rs.getInt("deptno"));
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
        return empDO;
    }

    @Override
    public List<EmpDO> getAll() {
        List<EmpDO> list = new ArrayList<>();
        EmpDO empDO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // empDO 也稱為 Domain objects
                empDO = new EmpDO();
                empDO.setEmpno(rs.getInt("empno"));
                empDO.setEname(rs.getString("ename"));
                empDO.setJob(rs.getString("job"));
                empDO.setHiredate(rs.getDate("hiredate").toLocalDate());
                empDO.setSal(rs.getDouble("sal"));
                empDO.setComm(rs.getDouble("comm"));
                empDO.setDeptno(rs.getInt("deptno"));
                list.add(empDO); // Store the row in the list
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
        return list;
    }
    
    public static void main(String[] args) {

        EmpDAO dao = new EmpDAOImpl();

        // 新增
//        EmpDO empDO1 = new EmpDO();
//        empDO1.setEname("王小明1");
//        empDO1.setJob("manager");
//        empDO1.setHiredate(LocalDate.parse(("2020-04-01")));
//        empDO1.setSal(new Double(50000));
//        empDO1.setComm(new Double(500));
//        empDO1.setDeptno(10);
//        dao.insert(empDO1);
//
//        // 修改
//        EmpDO empDO2 = new EmpDO();
//        empDO2.setEmpno(7002);
//        empDO2.setEname("王小明2");
//        empDO2.setJob("manager2");
//        empDO2.setHiredate(LocalDate.parse(("2020-04-01")));
//        empDO2.setSal(new Double(20000));
//        empDO2.setComm(new Double(200));
//        empDO2.setDeptno(20);
//        dao.update(empDO2);
//
//        // 刪除
//        dao.delete(7014);
//
//        // 查詢
//        EmpDO empDO3 = dao.findByPrimaryKey(7001);
//        System.out.print(empDO3.getEmpno() + ",");
//        System.out.print(empDO3.getEname() + ",");
//        System.out.print(empDO3.getJob() + ",");
//        System.out.print(empDO3.getHiredate() + ",");
//        System.out.print(empDO3.getSal() + ",");
//        System.out.print(empDO3.getComm() + ",");
//        System.out.println(empDO3.getDeptno());
//        System.out.println("---------------------");

        // 查詢
        List<EmpDO> list = dao.getAll();
        for (EmpDO empDO : list) {
            System.out.print(empDO.getEmpno() + ",");
            System.out.print(empDO.getEname() + ",");
            System.out.print(empDO.getJob() + ",");
            System.out.print(empDO.getHiredate() + ",");
            System.out.print(empDO.getSal() + ",");
            System.out.print(empDO.getComm() + ",");
            System.out.print(empDO.getDeptno());
            System.out.println();
        }
    }

}
