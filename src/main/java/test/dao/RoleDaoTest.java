package test.dao;

import dao.DAOFactory;
import dao.RoleDao;
import model.Role;

import java.util.ArrayList;
import java.util.Collection;

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
}