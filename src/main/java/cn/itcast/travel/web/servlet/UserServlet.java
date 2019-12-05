package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author baiziyu
 * @time 2019-11-19 17:42
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    /**
     * register
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the verification code
        String check = request.getParameter("check");
        ResultInfo info = new ResultInfo();
        // get the true verification code
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); // 保证验证码只能使用一次
        System.out.println("session[CHECKCODE_SERVER] delete");
//        System.out.println(check+" "+checkcode_server);
        // check code and judge it expired or not
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // if code is wrong send message ,and response as json
            info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        // get the user information from request
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败,用户名已存在!");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * login
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the verification code
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        ResultInfo info = new ResultInfo();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER"); // 保证验证码只能使用一次
        System.out.println("session[CHECKCODE_SERVER] delete");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // if code is wrong send message ,and response as json
            info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //UserServ ice service = new UserServiceImpl();
        User u = service.login(user);

        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名密码错误");
        }

        if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您尚未激活");
        }

        if (u != null && "Y".equals(u.getStatus())) {
            info.setFlag(true);
            request.getSession().setAttribute("user", u);
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    /**
     * find user name
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

    /**
     * exit login
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * get verification code
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            String msg = null;
            if (flag) {
                // active success
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                // active fail
                msg = "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
