package controller;

import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/disconnect")
public class DisconnectController extends HttpServlet {

    public static final String URL_BASE = "http://localhost:8080";

    private UserDao userDao;

    @Override
    public void init() {
        userDao = ( (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY) ).getUserDao();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        String urlRedirection = URL_BASE + request.getContextPath() + "/accessible/login";
        response.sendRedirect(urlRedirection);

    }
}
