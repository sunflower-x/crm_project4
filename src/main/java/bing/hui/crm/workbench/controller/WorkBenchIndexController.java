package bing.hui.crm.workbench.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkBenchIndexController {
    @RequestMapping("workbench/index.do")
    public String index() {
        //跳转到业务主界面
        return "workbench/index";
    }
}
