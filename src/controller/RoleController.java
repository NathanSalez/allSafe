package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.RoleDao;
import json.RoleSerializer;
import model.Role;
import model.User;
import service.RoleService;

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

    private RoleService roleService;

    @Override
    public void init() {
        DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY);
        RoleDao roleDao = daoFactory.getRoleDao();

        roleService = new RoleService(roleDao);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String jsonResponse;
        response.setContentType("application/json");
        switch(action)
        {
            case "getPossibleNewRoles" :
                jsonResponse = doGetPossibleNewRoles(request);
                break;

            default:
                jsonResponse = doMethodError();

        }
        response.getWriter().write(jsonResponse);
    }

    private String doGetPossibleNewRoles(HttpServletRequest request)
    {
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
            Collection<Role> newPossibleRoles = roleService.getNewPossibleRoles(u.getRole(),request);
            mapResponse.put("newPossibleRoles",newPossibleRoles);
            mapResponse.put("feedback","ok");
        }
        return gsonBuilder.create().toJson(mapResponse);
    }

    private String doMethodError()
    {
        Map<String,Object> mapResponse = new HashMap<>();
        mapResponse.put("feedback","ko");
        mapResponse.put("error", "Action parameter not found.");
        return new Gson().toJson(mapResponse);
    }
}
