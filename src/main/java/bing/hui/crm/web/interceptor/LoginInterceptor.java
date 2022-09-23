package bing.hui.crm.web.interceptor;

import bing.hui.crm.commons.constants.Constants;
import bing.hui.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //如果用户没有登录成功，则跳转到登录页
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        if (user==null){
            //httpServletResponse.sendRedirect("/crm_project4_war_exploded/"); //重定向时，url必须加项目的名称
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
