package controller;

import bean.Request;
import bean.User;
import net.sf.json.JSONArray;
import util.DBCPFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 查找好友列表servlet,返回json格式
 */
@WebServlet("/getFriends")
public class getFriendsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username=request.getParameter("username");
        // set集合存放friends列表
        Set<String > friends=new HashSet<String >();
        // DBCPFactory 从数据库select所有该username的Friends
        Connection connection= DBCPFactory.getConnection();
        String sql="SELECT * FROM friends where username = ?";
        try {
            PreparedStatement pst=connection.prepareStatement(sql);
            pst.setString(1,username);
            ResultSet resultSet=pst.executeQuery();
            while (resultSet.next()){
                String friendName=resultSet.getString("tousername");
                    friends.add(friendName);

            }
            // 转为json格式
            JSONArray array=JSONArray.fromObject(friends);
            System.out.println(array);
            String json="{\"friends\": "+array.toString()+"}";
            response.getWriter().print(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
