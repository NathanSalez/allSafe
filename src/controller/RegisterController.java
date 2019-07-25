package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.UserDao;
import model.User;
import service.RegisterService;

import static controller.BaseController.VIEW_FIELD_NAME;


@WebServlet("/accessible/register")
public class RegisterController extends HttpServlet {

    private final static String VIEW = "/WEB-INF/accessible/register.jsp";

    private RegisterService registerService;

    @Override
    public void init(){
        UserDao userDao = ( (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY) ).getUserDao();
        registerService = new RegisterService(userDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User newUser = registerService.registerUser(request);

        if( !registerService.getFeedback())
        {
            request.setAttribute("feedback", "ko");
            request.setAttribute("form", registerService);
            request.setAttribute("newUser",newUser);
            request.setAttribute(VIEW_FIELD_NAME,"register");
            this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
        }
        else
        {
            String urlRedirection = DisconnectController.URL_BASE + request.getContextPath() + "/accessible/login";
            response.sendRedirect(urlRedirection);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(VIEW_FIELD_NAME,"register");
        this.getServletContext().getRequestDispatcher( VIEW ).forward( request, response );
    }
}
