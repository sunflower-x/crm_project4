package bing.hui.crm.workbench.service;

import bing.hui.crm.workbench.domain.Activity;
import bing.hui.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    //保存创建的市场活动
    int saveCreateActivity(Activity activity);

    //查询市场活动列表
    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);

    //查询市场活动表的总条数
    int selectCountForActivityByCondition(Map<String,Object> map);

    //删除选中的市场活动
    int deleteActivityByIds(String[] ids);

    //根据id查询需要修改的市场活动数据
    Activity queryActivityById(String id);

    //保存修改的市场活动数据
    int saveEditActivity(Activity activity);

    //查询所有的市场活动数据
    List<Activity> queryAllActivities();

    //查询所有的选中的市场活动数据
    List<Activity> queryAllSelectedActivities(String[] ids);

    //批量保存市场活动数据
    int saveCreateActivityByList(List<Activity> activityList);

    //通过id查询市场活动
    Activity queryActivityForDetailById(String id);



}
