package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * register user while user is not exist,and send the check email
     * @param user the user object
     * @return register whether succeed
     */
    @Override
    public boolean regist(User user) {
        // according to username check the user exist or not
        User u = userDao.findByUsername(user.getUsername());
        if(u != null) {
            return false;
        }
        // set code and status,uniquely string
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        userDao.save(user);

        // send email and set email content
        String content = "<a href=\"http://localhost/travel/activeUserServlet?code="+user.getCode()+"\">点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }


    /**
     * verify email activation code
     * @param code activation code
     * @return activation code is right or not
     */
    @Override
    public boolean active(String code) {
        // 1. according to code check user
        User user = userDao.findByCode(code);
        if(user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
