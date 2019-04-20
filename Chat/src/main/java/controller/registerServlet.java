package controller;

import util.DBCPFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(value = "/registerServlet")
public class registerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String stuID=request.getParameter("stuID");
        Connection conn= DBCPFactory.getConnection();
        System.out.println(username);
        // 查找用户
        String sql = "SELECT username FROM users where stuID = ? ";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, stuID);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){// 判断是否已经存在该用户名
                /*response.getWriter().print("{\"code\": 1}");// 已有该用户名*/
                response.getWriter().print("已有该用户名");
            }else {// 若不存在，则将user信息插入数据库
                String sql1 = "INSERT INTO users (username,password,stuID) VALUES (?,?,?)";
                PreparedStatement pst2 = conn.prepareStatement(sql1);
                pst2.setString(1, username);
                pst2.setString(2, password);
                pst2.setString(3, stuID);
                if (pst2.executeUpdate()==1){
                    /*response.getWriter().print("{\"code\": 0}");// 成功创建*/
                    response.sendRedirect("" +
                            "index.jsp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
