package bing.hui.crm.workbench.mapper;

import bing.hui.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sun Apr 10 09:36:02 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sun Apr 10 09:36:02 CST 2022
     */
    int insert(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sun Apr 10 09:36:02 CST 2022
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sun Apr 10 09:36:02 CST 2022
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sun Apr 10 09:36:02 CST 2022
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Sun Apr 10 09:36:02 CST 2022
     */
    int updateByPrimaryKey(Activity record);

    /*
    * 保存创建的市场活动
    * */
    int insertActivity(Activity activity);

    /*
    * 查询市场活动表
    * */
    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);

    /*
    * 查询市场活动的总条数
    * */

    int selectCountOfActivityByCondition(Map<String,Object> map);

    /*
    * 删除选中的市场活动
    * */
    int deleteActivityByIds(String[] ids);

    /*
    * 根据id查询需要修改的市场活动数据
    * */
    Activity selectActivityById(String id);
    /*
    * 保存修改的市场活动
    * */
    int updateActivity(Activity activity);

    /*
    * 查询所有的市场活动数据
    * */
    List<Activity> selectAllActivities();

    /*
    * 根据id查询所有选中的市场活动
    * */
    List<Activity> selectAllSelectedActivities(String[] ids);

    /*
    * 批量保存创建的市场活动数据
    * */
    int insertActivityByList(List<Activity> activityList);

    /*
    * 通过id查询查询市场活动
    * */
    Activity selectActivityForDetailById(String id);

}