package test.dao;

import dao.DAOException;
import dao.DAOFactory;
import dao.UserDao;

import model.Role;
import model.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private static UserDao userDao;

    private static List<User> usersTest;

    private static Collection<User> usersAlreadyRegistered;

    @BeforeAll
    public static void setUpBeforeClass()
    {
        DAOFactory daoFactory = DAOFactory.getInstance();
        userDao = daoFactory.getUserDao();
        usersTest = new ArrayList<>();
        usersAlreadyRegistered = userDao.findAll();
        usersTest.add( new User("Test1","mdp1")); // user registered
        usersTest.add( new User( "Test2", "mdp2")); // user not registered
    }

    @BeforeEach
    public void setUp()
    {
        for( User u : usersTest) {
            assertFalse(userDao.findAll().contains(u));
        }
        userDao.create(usersTest.get(0));

    }

    @AfterEach
    public void tearDown()
    {
        if( userDao.find(usersTest.get(0).getPseudo()) != null)
            userDao.delete(usersTest.get(0).getPseudo());
    }


    @Test
    void create()
    {
        userDao.delete(usersTest.get(0).getPseudo());
        User uRegistered = usersTest.get(0);
        assertDoesNotThrow( () -> userDao.create(uRegistered));
        assertEquals( userDao.find(uRegistered.getPseudo()).getPseudo(),uRegistered.getPseudo() );
        assertThrows(DAOException.class, () -> userDao.create(new User()));
        assertThrows(NullPointerException.class, () -> userDao.create(null));
    }

    @Test
    void find() {
        User uRegistered = usersTest.get(0);
        User uNotRegistered = usersTest.get(1);

        assertEquals( uRegistered, userDao.find(uRegistered.getPseudo()));
        assertNull(userDao.find(null));
        assertNull(userDao.find(uNotRegistered.getPseudo()));
    }

    @Test
    void updateState() {
        User uRegistered = usersTest.get(0);
        User uNotRegistered = usersTest.get(1);

        assertDoesNotThrow(() -> userDao.updateState(uRegistered.getPseudo(),true) );
        assertDoesNotThrow(() -> userDao.updateState(uRegistered.getPseudo(),true) );
        assertThrows(DAOException.class, () -> userDao.updateState(uNotRegistered.getPseudo(),true));
        assertThrows(DAOException.class, () -> userDao.updateState(null,false));
        assertDoesNotThrow(() -> userDao.updateState(uRegistered.getPseudo(),false) );
    }

    @Test
    void countLoggedUsers() {
        assertEquals(0,userDao.countLoggedUsers());
        userDao.updateState(usersTest.get(0).getPseudo(),true);
        assertEquals(1, userDao.countLoggedUsers());
    }

    @Test
    void findAll() {
        usersAlreadyRegistered.add( usersTest.get(0));
        assertIterableEquals(userDao.findAll(), usersAlreadyRegistered);
    }

    @Test
    void delete() {
        User uRegistered = usersTest.get(0);
        User uNotRegistered = usersTest.get(1);
        assertDoesNotThrow(() -> userDao.delete(uRegistered.getPseudo()) );
        assertThrows(DAOException.class, () -> userDao.delete(uNotRegistered.getPseudo()));
    }

    @Test
    void updateRole() {
        User uRegistered = usersTest.get(0);
        User uNotRegistered = usersTest.get(1);
        assertDoesNotThrow(() -> userDao.updateRole(uRegistered.getPseudo(), new Role("ADMIN","Administrator") ));
        assertThrows(DAOException.class, () -> userDao.updateRole(uNotRegistered.getPseudo(),new Role("ADMIN","Administrator")));
    }
}