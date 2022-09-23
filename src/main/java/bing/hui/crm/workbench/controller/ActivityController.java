package bing.hui.crm.workbench.controller;

import bing.hui.crm.commons.constants.Constants;
import bing.hui.crm.commons.domain.ReturnObject;
import bing.hui.crm.commons.utils.DateUtils;
import bing.hui.crm.commons.utils.HSSFUtils;
import bing.hui.crm.commons.utils.UUIDUtils;
import bing.hui.crm.settings.domain.User;
import bing.hui.crm.settings.service.Userservice;
import bing.hui.crm.workbench.domain.Activity;
import bing.hui.crm.workbench.domain.ActivityRemark;
import bing.hui.crm.workbench.service.ActivityRemarkService;
import bing.hui.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@SuppressWarnings("ALL")
@Controller
public class ActivityController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;


    @RequestMapping("workbench/activity/toActivity.do")
    public ModelAndView toActivity(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userservice.queryAllUsers();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("workbench/activity/index");
        return modelAndView;
    }

    @RequestMapping("workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session) {

        User user = (User) session.getAttribute(Constants.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法，保存创建的市场活动
            int result = activityService.saveCreateActivity(activity);
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

    /*
     * 实现分页功能
     * */
    @RequestMapping("workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name, String owner, String startDate, String endDate, int pageNo, int pageSize) {
        //封装参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        //调用service层方法，查询数据
        List<Activity> activityList = activityService.selectActivityByConditionForPage(map);
        int totalRows = activityService.selectCountForActivityByCondition(map);
        //根据查询结果生成响应信息
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("activityList", activityList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }
    /*@RequestMapping("workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate, int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用service层方法，查询数据
        List<Activity> activityList=activityService.selectActivityByConditionForPage(map);
        int totalRows=activityService.selectCountForActivityByCondition(map);
        //根据查询结果结果，生成响应信息
        Map<String,Object> retMap=new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }*/

    /*
     * 删除选中的市场活动
     * */
    //疑问：为什么这里用ModelAndView不行
    /*@RequestMapping("workbench/activity/deleteActivityByIds.do")
    public ModelAndView deleteActivityByIds(String[] id){
        ModelAndView modelAndView = new ModelAndView();
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法
            int result = activityService.deleteActivityByIds(id);
            if(result>0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        }catch(Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        modelAndView.addObject("returnObject",returnObject);
        System.out.println("---------------------------------------------------------");
        return modelAndView;
    }*/
    @RequestMapping("workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法
            int result = activityService.deleteActivityByIds(id);
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

    /*
     * 根据id查询需要修改的市场活动数据
     * */
    @RequestMapping("workbench/activity/queryActivityById.do")
    @ResponseBody
    public Activity queryActivityById(String id) {
        //调用service层方法，查询市场活动
        //根据查询结果，返回响应信息
        return activityService.queryActivityById(id);
    }

    @RequestMapping("workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity activity, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        //封装参数
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        activity.setEditBy(user.getId());
        try {
            //调用service层方法保存修改的市场活动
            int result = activityService.saveEditActivity(activity);
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

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryAllActivities();
        /*for (Activity activity : activityList) {
            System.out.println(activity.toString());
        }*/


        //创建excel文件，并且把activityList写入到excel文件中
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("owner");
        cell = row.createCell(2);
        cell.setCellValue("name");
        cell = row.createCell(3);
        cell.setCellValue("startDate");
        cell = row.createCell(4);
        cell.setCellValue("endDate");
        cell = row.createCell(5);
        cell.setCellValue("cost");
        cell = row.createCell(6);
        cell.setCellValue("description");
        cell = row.createCell(7);
        cell.setCellValue("createTime");
        cell = row.createCell(8);
        cell.setCellValue("createBy");
        cell = row.createCell(9);
        cell.setCellValue("editTime");
        cell = row.createCell(10);
        cell.setCellValue("editBy");

        //遍历activityList
        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                //每遍历出一个市场活动对象，生成一行
                row = sheet.createRow(i + 1);
                //填充一行数据中的每一列
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        //根据workbook对象生成excel文件
       /* FileOutputStream fileOutputStream = new FileOutputStream("D:\\TestPoi\\a\\activityList.xls");
        workbook.write(fileOutputStream);//使用这个方法效率太低，可以优化

        //关闭资源
        fileOutputStream.close();
        workbook.close();*/


        //把生成的excel文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activityList.xls");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        /*FileInputStream fileInputStream = new FileInputStream("D:\\TestPoi\\a\\activityList.xls");
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        fileInputStream.close();*/
        workbook.close();
        outputStream.flush();
    }

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("workbench/activity/exportSelectedActivities.do")
    public void exportSelectedActivities(HttpServletResponse response, String[] ids) throws IOException {
        List<Activity> activityList = activityService.queryAllSelectedActivities(ids);
        //创建excel文件，并且把activityList写入到excel文件中
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("owner");
        cell = row.createCell(2);
        cell.setCellValue("name");
        cell = row.createCell(3);
        cell.setCellValue("startDate");
        cell = row.createCell(4);
        cell.setCellValue("endDate");
        cell = row.createCell(5);
        cell.setCellValue("cost");
        cell = row.createCell(6);
        cell.setCellValue("description");
        cell = row.createCell(7);
        cell.setCellValue("createTime");
        cell = row.createCell(8);
        cell.setCellValue("createBy");
        cell = row.createCell(9);
        cell.setCellValue("editTime");
        cell = row.createCell(10);
        cell.setCellValue("editBy");

        //遍历activityList
        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                //每遍历出一个市场活动对象，生成一行
                row = sheet.createRow(i + 1);
                //填充一行数据中的每一列
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        //根据workbook对象生成excel文件
       /* FileOutputStream fileOutputStream = new FileOutputStream("D:\\TestPoi\\a\\activityList.xls");
        workbook.write(fileOutputStream);//使用这个方法效率太低，可以优化

        //关闭资源
        fileOutputStream.close();
        workbook.close();*/


        //把生成的excel文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activityList.xls");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        /*FileInputStream fileInputStream = new FileInputStream("D:\\TestPoi\\a\\activityList.xls");
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        fileInputStream.close();*/
        workbook.close();
        outputStream.flush();
    }

    @RequestMapping("workbench/activity/uploadFile.do")
    @ResponseBody
    public Object uploadFile(String name, MultipartFile uploadFile) throws IOException {
        System.out.println("---------------------------------------");
        System.out.println("name" + name);
        String originalFilename = uploadFile.getOriginalFilename();
        File file = new File("D:\\TestPoi\\b", originalFilename);
        uploadFile.transferTo(file);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        returnObject.setMessage("YES");
        return returnObject;
    }

    /*@RequestMapping("workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile multipartFile,HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        try {
            //把接收到的excel文件写到磁盘目录中
            String originalFilename = multipartFile.getOriginalFilename();
            File file = new File("D:\\TestPoi\\b", originalFilename);
            multipartFile.transferTo(file);
            //解析excel文件，获取文件中的数据，并且封装成activityList
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Activity> activityList = new ArrayList<>();
            Activity activity=null;
            HSSFRow row=null;
            HSSFCell cell=null;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                activity=new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getName());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                row=sheet.getRow(i);
                for (int j=0;j<row.getLastCellNum();j++){
                    cell=row.getCell(j);
                    //获取列中的数据
                    String cellValue=HSSFUtils.getCellValueForStr(cell);
                    if(j==0){
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if(j==4){
                        activity.setDescription(cellValue);
                    }
                }
                    activityList.add(activity);
            }
            int result = activityService.saveCreateActivityByList(activityList);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(result);
        } catch (IOException e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }*/
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody
    Object importActivity(MultipartFile activityFile, String userName, HttpSession session) {
        System.out.println("userName=" + userName);
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        try {
            //把excel文件写到磁盘目录中
            /*String originalFilename = activityFile.getOriginalFilename();
            File file = new File("D:\\course\\18-CRM\\阶段资料\\serverDir\\", originalFilename);//路径必须手动创建好，文件如果不存在，会自动创建
            activityFile.transferTo(file);*/

            //解析excel文件，获取文件中的数据，并且封装成activityList
            //根据excel文件生成HSSFWorkbook对象，封装了excel文件的所有信息
            //InputStream is=new FileInputStream("D:\\course\\18-CRM\\阶段资料\\serverDir\\"+originalFilename);

            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            //根据wb获取HSSFSheet对象，封装了一页的所有信息
            //页的下标，下标从0开始，依次增加
            HSSFSheet sheet = wb.getSheetAt(0);
            //根据sheet获取HSSFRow对象，封装了一行的所有信息
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            //sheet.getLastRowNum()：最后一行的下标
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //行的下标，下标从0开始，依次增加
                row = sheet.getRow(i);
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getName());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                //row.getLastCellNum():最后一列的下标+1
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    //根据row获取HSSFCell对象，封装了一列的所有信息
                    //列的下标，下标从0开始，依次增加
                    cell = row.getCell(j);

                    //获取列中的数据
                    String cellValue = HSSFUtils.getCellValueForStr(cell);
                    if (j == 0) {
                        activity.setName(cellValue);
                    } else if (j == 1) {
                        activity.setStartDate(cellValue);
                    } else if (j == 2) {
                        activity.setEndDate(cellValue);
                    } else if (j == 3) {
                        activity.setCost(cellValue);
                    } else if (j == 4) {
                        activity.setDescription(cellValue);
                    }
                }

                //每一行中所有列都封装完成之后，把activity保存到list中
                activityList.add(activity);
            }

            //调用service层方法，保存市场活动
            int ret = activityService.saveCreateActivityByList(activityList);

            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(ret);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }

        return returnObject;
    }

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("workbench/activity/detailActivity.do")
    public String detailActivity(String id, HttpServletRequest request) {
        //System.out.println(id);
        //调用service层方法查询数据
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //把数据保存到request中
        request.setAttribute("activity", activity);
        request.setAttribute("remarkList", remarkList);
        //请求转发
        return "workbench/activity/detail";

    }
}
