package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    /**
     * register user while user is not exist,and send the check email
     * @param user the user object
     * @return register whether succeed
     */
    boolean regist(User user);

    /**
     * verify email activation code
     * @param code activation code
     * @return activation code is right or not
     */
    boolean active(String code);

    User login(User user);
}
