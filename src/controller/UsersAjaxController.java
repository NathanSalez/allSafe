package controller;

import com.google.gson.Gson;
import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.RoleDao;
import dao.UserDao;
import service.UserManagementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/users/")
public class UsersAjaxController extends HttpServlet {

    @Override
    public void init() {
        DAOFactory daoFactory = (DAOFactory) getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY);
        UserDao userDao =  daoFactory.getUserDao();
        RoleDao roleDao = daoFactory.getRoleDao();

        userManagementService = new UserManagementService(userDao,roleDao);
    }

    private UserManagementService userManagementService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        String jsonResponse = "";
        switch(action)
        {
            case "countNbUsers" :
                jsonResponse = doGetSearchConnectedUsers();
                break;

            case "searchUsers" :
                break;

            default:
                jsonResponse = doMethodError();
        }
        response.getWriter().write(jsonResponse);
    }

    private String doGetSearchConnectedUsers()
    {
        Long loggedUsers = userManagementService.countLoggedUsers();
        Map<String,Object> mapResponse = new HashMap<>();
        if( userManagementService.getFeedback() )
        {
            mapResponse.put("feedback","ok");
            mapResponse.put("nbConnectedUsers",loggedUsers);
        }
        else
        {
            mapResponse.put("feedback","ko");
            mapResponse.put("error", userManagementService.getErrors());
        }
        return new Gson().toJson(mapResponse);
    }

    private String doMethodError()
    {
        Map<String,Object> mapResponse = new HashMap<>();
        mapResponse.put("feedback","ko");
        mapResponse.put("error", "Action parameter not found.");
        return new Gson().toJson(mapResponse);
    }
}
