package serviceImpl;

import dao.UserDAO;
import pojo.User;
import service.UserManagement;


public class UserManagementImpl implements UserManagement {
    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public void removeUser(int id) {
        userDAO.deleteUser(id);
    }
}
