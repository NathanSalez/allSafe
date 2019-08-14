package controller;

import com.google.gson.GsonBuilder;
import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.RoleDao;
import dao.UserDao;
import json.RoleSerializer;
import model.Role;
import model.User;
import service.UserManagementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static utils.SessionUtils.USER_SESSION_FIELD;

@WebServlet("/roles/")
public class RoleController extends HttpServlet {

    private UserManagementService userManagementService;

    @Override
    public void init() {
        DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY);
        UserDao userDao =  daoFactory.getUserDao();
        RoleDao roleDao = daoFactory.getRoleDao();

        userManagementService = new UserManagementService(userDao,roleDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch(action)
        {
            case "getPossibleNewRoles" :
                doGetPossibleNewRoles(request,response);
                break;

            default:
                System.err.println("error");

        }
    }

    private void doGetPossibleNewRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        RoleSerializer roleSerializer = new RoleSerializer();
        gsonBuilder.registerTypeAdapter(Role.class, roleSerializer);
        Map<String,Object> mapResponse = new HashMap<>();
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute(USER_SESSION_FIELD);
        if( u == null)
        {
            mapResponse.put("feedback","ko");
            mapResponse.put("error", "User's session not found.");
        }
        else
        {
            Collection<Role> newPossibleRoles = userManagementService.getNewPossibleRoles(u.getRole(),request);
            mapResponse.put("newPossibleRoles",newPossibleRoles);
            mapResponse.put("feedback","ok");
        }
        String jsonResponse = gsonBuilder.create().toJson(mapResponse);
        response.getWriter().write(jsonResponse);
    }
}
