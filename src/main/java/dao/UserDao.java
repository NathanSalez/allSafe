package dao;

import model.Role;
import model.User;

import java.util.Collection;

public interface UserDao{

    void create( User user ) throws DAOException;

    User find( String pseudo) throws DAOException;

    void updateState(String pseudo, boolean connect) throws DAOException;

    void updateRole(String pseudo, Role newRole) throws DAOException;

    Long countLoggedUsers() throws DAOException;

    void delete(String pseudo) throws DAOException;

    Collection<User> findAll() throws DAOException;

}
