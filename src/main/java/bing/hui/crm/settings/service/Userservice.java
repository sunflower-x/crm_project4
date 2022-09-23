package bing.hui.crm.settings.service;

import bing.hui.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface Userservice {
    User queryUserByLoginActAndPwd(Map<String,Object> map);

    List<User> queryAllUsers();
}
