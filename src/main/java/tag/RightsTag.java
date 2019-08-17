package tag;

import config.InitialisationDaoFactory;
import dao.DAOFactory;
import dao.RoleDao;
import model.Role;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Nathan Salez
 */
public class RightsTag extends BodyTagSupport {

    private static RoleDao roleDao;

    private Role executorRole;

    private String action;

    private Role affectedRole;

    private Role newRole;

    public void setExecutorRole(String executorRole) {
        this.executorRole = Role.getRole(executorRole);
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAffectedRole(String affectedRole) {
        this.affectedRole = Role.getRole(affectedRole);
    }

    public void setNewRole(String newRole) {
        this.newRole = Role.getRole(newRole);
    }

    private void getInstanceRoleDao()
    {
        if( roleDao == null )
            roleDao = ( (DAOFactory) pageContext.getServletContext().getAttribute(InitialisationDaoFactory.ATT_DAO_FACTORY) ).getRoleDao();
    }
    @Override
    public int doStartTag() throws JspException {
        getInstanceRoleDao();
        if( roleDao.hasTheRightTo(executorRole,action,affectedRole,newRole))
        {
            return EVAL_BODY_INCLUDE;
        }
        else
        {
            return SKIP_BODY;
        }

    }
}
