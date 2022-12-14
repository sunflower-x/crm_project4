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
        //????????????
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            //??????service???????????????????????????????????????
            int result = activityService.saveCreateActivity(activity);
            if (result > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
        }
        return returnObject;
    }

    /*
     * ??????????????????
     * */
    @RequestMapping("workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name, String owner, String startDate, String endDate, int pageNo, int pageSize) {
        //????????????
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        //??????service????????????????????????
        List<Activity> activityList = activityService.selectActivityByConditionForPage(map);
        int totalRows = activityService.selectCountForActivityByCondition(map);
        //????????????????????????????????????
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("activityList", activityList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }
    /*@RequestMapping("workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate, int pageNo,int pageSize){
        //????????????
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //??????service????????????????????????
        List<Activity> activityList=activityService.selectActivityByConditionForPage(map);
        int totalRows=activityService.selectCountForActivityByCondition(map);
        //?????????????????????????????????????????????
        Map<String,Object> retMap=new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }*/

    /*
     * ???????????????????????????
     * */
    //???????????????????????????ModelAndView??????
    /*@RequestMapping("workbench/activity/deleteActivityByIds.do")
    public ModelAndView deleteActivityByIds(String[] id){
        ModelAndView modelAndView = new ModelAndView();
        ReturnObject returnObject = new ReturnObject();
        try {
            //??????service?????????
            int result = activityService.deleteActivityByIds(id);
            if(result>0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        }catch(Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
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
            //??????service?????????
            int result = activityService.deleteActivityByIds(id);
            if (result > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
        }
        return returnObject;
    }

    /*
     * ??????id???????????????????????????????????????
     * */
    @RequestMapping("workbench/activity/queryActivityById.do")
    @ResponseBody
    public Activity queryActivityById(String id) {
        //??????service??????????????????????????????
        //???????????????????????????????????????
        return activityService.queryActivityById(id);
    }

    @RequestMapping("workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity activity, HttpSession session) {
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        //????????????
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        activity.setEditBy(user.getId());
        try {
            //??????service????????????????????????????????????
            int result = activityService.saveEditActivity(activity);
            if (result > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("???????????????????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????...");
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


        //??????excel??????????????????activityList?????????excel?????????
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("??????????????????");
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

        //??????activityList
        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                //???????????????????????????????????????????????????
                row = sheet.createRow(i + 1);
                //?????????????????????????????????
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

        //??????workbook????????????excel??????
       /* FileOutputStream fileOutputStream = new FileOutputStream("D:\\TestPoi\\a\\activityList.xls");
        workbook.write(fileOutputStream);//?????????????????????????????????????????????

        //????????????
        fileOutputStream.close();
        workbook.close();*/


        //????????????excel????????????????????????
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
        //??????excel??????????????????activityList?????????excel?????????
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("??????????????????");
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

        //??????activityList
        if (activityList != null && activityList.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                //???????????????????????????????????????????????????
                row = sheet.createRow(i + 1);
                //?????????????????????????????????
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

        //??????workbook????????????excel??????
       /* FileOutputStream fileOutputStream = new FileOutputStream("D:\\TestPoi\\a\\activityList.xls");
        workbook.write(fileOutputStream);//?????????????????????????????????????????????

        //????????????
        fileOutputStream.close();
        workbook.close();*/


        //????????????excel????????????????????????
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
            //???????????????excel???????????????????????????
            String originalFilename = multipartFile.getOriginalFilename();
            File file = new File("D:\\TestPoi\\b", originalFilename);
            multipartFile.transferTo(file);
            //??????excel???????????????????????????????????????????????????activityList
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
                    //?????????????????????
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
            returnObject.setMessage("???????????????????????????...");
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
            //???excel???????????????????????????
            /*String originalFilename = activityFile.getOriginalFilename();
            File file = new File("D:\\course\\18-CRM\\????????????\\serverDir\\", originalFilename);//?????????????????????????????????????????????????????????????????????
            activityFile.transferTo(file);*/

            //??????excel???????????????????????????????????????????????????activityList
            //??????excel????????????HSSFWorkbook??????????????????excel?????????????????????
            //InputStream is=new FileInputStream("D:\\course\\18-CRM\\????????????\\serverDir\\"+originalFilename);

            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            //??????wb??????HSSFSheet???????????????????????????????????????
            //????????????????????????0?????????????????????
            HSSFSheet sheet = wb.getSheetAt(0);
            //??????sheet??????HSSFRow???????????????????????????????????????
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            //sheet.getLastRowNum()????????????????????????
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //????????????????????????0?????????????????????
                row = sheet.getRow(i);
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getName());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                //row.getLastCellNum():?????????????????????+1
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    //??????row??????HSSFCell???????????????????????????????????????
                    //????????????????????????0?????????????????????
                    cell = row.getCell(j);

                    //?????????????????????
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

                //????????????????????????????????????????????????activity?????????list???
                activityList.add(activity);
            }

            //??????service??????????????????????????????
            int ret = activityService.saveCreateActivityByList(activityList);

            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(ret);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("???????????????????????????....");
        }

        return returnObject;
    }

    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @RequestMapping("workbench/activity/detailActivity.do")
    public String detailActivity(String id, HttpServletRequest request) {
        //System.out.println(id);
        //??????service?????????????????????
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //??????????????????request???
        request.setAttribute("activity", activity);
        request.setAttribute("remarkList", remarkList);
        //????????????
        return "workbench/activity/detail";

    }
}
