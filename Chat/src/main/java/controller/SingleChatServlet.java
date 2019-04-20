package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 单人聊天servlet
 */
@WebServlet(value = "/singleChat")
public class SingleChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String toUsername=request.getParameter("toUsername");
        String fromUsername=request.getParameter("fromUsername");

        System.out.println(fromUsername+"   "+toUsername);
        HttpSession session=request.getSession();
        session.setAttribute("toUsername",toUsername);
        session.setAttribute("fromUsernmae",fromUsername);
        request.getRequestDispatcher("/SingleChatRoom.jsp").forward(request,response);

    }

}
