package test.dao;

import dao.DAOFactory;
import dao.RoleDao;
import model.Role;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleDaoTest {

    private static RoleDao roleDao;

    static Role modo = new Role("MODERATOR","Moderator");
    static Role simple = new Role("SIMPLE","Simple user");
    static Role admin = new Role("ADMIN","Administrator");

    @org.junit.jupiter.api.BeforeAll
    public static void setUpBeforeClass()
    {
        DAOFactory daoFactory = DAOFactory.getInstance();
        roleDao = daoFactory.getRoleDao();
    }

    @Test
    void hasTheRightTo() {
        assertTrue( roleDao.hasTheRightTo(admin,"updateAs",modo,admin));
        assertFalse( roleDao.hasTheRightTo(admin,null, modo,admin ));
        assertFalse( roleDao.hasTheRightTo(null,"updateAs", modo,admin ));
        assertFalse( roleDao.hasTheRightTo(null,null, modo,admin ));
        assertTrue( roleDao.hasTheRightTo(modo,"delete",simple,null));
    }

    @org.junit.jupiter.api.Test
    void findAll() {
        Role[] allRoles = new Role[3];
        allRoles[0] = simple;
        allRoles[1] = modo;
        allRoles[2] = admin;
        assertArrayEquals(allRoles, roleDao.findAll());
    }

    @org.junit.jupiter.api.Test
    void getNewPossibleRoles() {
        ArrayList<Role> expectedRoles = new ArrayList<>(2);
        expectedRoles.add(simple);
        expectedRoles.add(admin);
        Collection<Role> obtainedRoles = roleDao.getNewPossibleRoles(admin,modo);
        assertArrayEquals(expectedRoles.toArray(),obtainedRoles.toArray());
    }

    @Test
    void getPossibleActions() {
        Set<String> expectedActions = new HashSet<>();
        expectedActions.add("updateAs");
        expectedActions.add("delete");
        expectedActions.add("update");
        Collection<String> obtainedActions = roleDao.getPossibleActions(new Role("ADMIN","Administrator"),new Role("MODERATOR","Moderator"));
        Set<String> obtainedActionsToSet = new HashSet<>(obtainedActions);
        assertArrayEquals(expectedActions.toArray(),obtainedActionsToSet.toArray());
    }
}