package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * get user from mysql by username
     * @param username username
     * @return user information
     */
    User findByUsername(String username);

    /**
     * save user information in mysql
     * @param user user information
     */
    void save(User user);

    /**
     * get user from mysql by username
     * @param code activation code
     * @return user information
     */
    User findByCode(String code);

    /**
     * update status
     * @param user user information
     */
    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
