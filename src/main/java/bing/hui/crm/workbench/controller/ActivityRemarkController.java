package bing.hui.crm.workbench.controller;

import bing.hui.crm.commons.constants.Constants;
import bing.hui.crm.commons.domain.ReturnObject;
import bing.hui.crm.commons.utils.DateUtils;
import bing.hui.crm.commons.utils.UUIDUtils;
import bing.hui.crm.settings.domain.User;
import bing.hui.crm.workbench.domain.ActivityRemark;
import bing.hui.crm.workbench.service.ActivityRemarkService;
import org.aspectj.apache.bcel.classfile.ConstantNameAndType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.resources.cldr.ti.CalendarData_ti_ER;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("workbench/activity/saveCreateActivityRemark.do")
    @ResponseBody
    public Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session) {
        //封装参数
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        activityRemark.setId(UUIDUtils.getUUID());
        activityRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Constants.REMARK_EDIT_FLAG_NO_EDITED);
        ReturnObject returnObject = new ReturnObject();
        //调用service层方法，保存创建的市场活动
        try {
            int result = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (result > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("workbench/activity/deleteActivityRemark.do")
    @ResponseBody
    public Object deleteActivityRemark(String id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = activityRemarkService.deleteActivityRemarkById(id);
            if (result > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("workbench/activity/saveEditActivityRemark.do")
    @ResponseBody
    public Object saveEditActivityRemark(ActivityRemark activityRemark, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        //封装参数
        activityRemark.setEditTime(DateUtils.formatDateTime(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Constants.REMARK_EDIT_FLAG_YES_EDITED);
        //调用service层方法保存修改的市场活动备注
        try {
            int result = activityRemarkService.saveEditActivityRemark(activityRemark);
            if (result > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }
}
