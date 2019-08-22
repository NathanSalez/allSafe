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

    @org.junit.jupiter.api.BeforeAll
    public static void setUpBeforeClass()
    {
        DAOFactory daoFactory = DAOFactory.getInstance();
        roleDao = daoFactory.getRoleDao();
    }

    @org.junit.jupiter.api.Test
    void hasTheRightTo() {
        assertTrue( roleDao.hasTheRightTo(Role.ADMIN,"updateAs",Role.MODERATOR,Role.ADMIN));
        assertFalse( roleDao.hasTheRightTo(Role.ADMIN,null, Role.MODERATOR,Role.ADMIN ));
        assertFalse( roleDao.hasTheRightTo(null,"updateAs", Role.MODERATOR,Role.ADMIN ));
        assertFalse( roleDao.hasTheRightTo(null,null, Role.MODERATOR,Role.ADMIN ));
        assertTrue( roleDao.hasTheRightTo(Role.MODERATOR,"delete",Role.SIMPLE,null));
    }

    @org.junit.jupiter.api.Test
    void findAll() {
        Role[] allRoles = Role.values();
        assertArrayEquals(allRoles, roleDao.findAll());
    }

    @org.junit.jupiter.api.Test
    void getNewPossibleRoles() {
        ArrayList<Role> expectedRoles = new ArrayList<>(2);
        expectedRoles.add( Role.SIMPLE);
        expectedRoles.add(Role.ADMIN);
        Collection<Role> obtainedRoles = roleDao.getNewPossibleRoles(Role.ADMIN,Role.MODERATOR);
        assertArrayEquals(expectedRoles.toArray(),obtainedRoles.toArray());
    }

    @Test
    void getPossibleActions() {
        Set<String> expectedActions = new HashSet<>();
        expectedActions.add("updateAs");
        expectedActions.add("delete");
        expectedActions.add("update");
        Collection<String> obtainedActions = roleDao.getPossibleActions(Role.ADMIN,Role.MODERATOR);
        Set<String> obtainedActionsToSet = new HashSet<>(obtainedActions);
        assertArrayEquals(expectedActions.toArray(),obtainedActionsToSet.toArray());
    }
}