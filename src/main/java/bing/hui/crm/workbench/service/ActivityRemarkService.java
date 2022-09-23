package bing.hui.crm.workbench.service;

import bing.hui.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    int saveCreateActivityRemark(ActivityRemark activityRemark);

    int deleteActivityRemarkById(String id);

    int saveEditActivityRemark(ActivityRemark activityRemark);
}
