package dao;

import model.Role;

import java.util.Collection;

public interface RoleDao{

    boolean hasTheRightTo(Role executorRole, String action, Role affectedRole, Role newRole ) throws DAOException;

    Role[] findAll() throws DAOException;

    Collection<Role> getNewPossibleRoles(Role executorRole, Role affectedRole) throws DAOException;

    Collection<String> getPossibleActions(Role executorRole, Role affectedRole) throws DAOException;

}