package controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/welcome")
public class BaseController extends HttpServlet {

    private final static String VIEW = "/WEB-INF/index.jsp";

    public final static String VIEW_FIELD_NAME = "view";
    public final static String FORM_ATTRIBUTE = "form";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("BaseController.doGet - Forward to " + VIEW );
        request.setAttribute(VIEW_FIELD_NAME,"home");
        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );

    }
}
