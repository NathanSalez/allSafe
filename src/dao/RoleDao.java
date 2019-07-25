package dao;

import model.Role;

public interface RoleDao{

    boolean hasTheRightTo(Role executorRole, String action, Role affectedRole, Role newRole ) throws DAOException;

    Role[] findAll() throws DAOException;

}