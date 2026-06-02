package com.nowcoder.community.controller;

import com.nowcoder.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.Date;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    private void setDefaultDateRange(Model model) {
        Calendar calendar = Calendar.getInstance();
        Date end = calendar.getTime();
        calendar.add(Calendar.DATE, -7);
        Date start = calendar.getTime();
        model.addAttribute("uvStartDate", start);
        model.addAttribute("uvEndDate", end);
        model.addAttribute("dauStartDate", start);
        model.addAttribute("dauEndDate", end);
    }

    //统计页面
    @RequestMapping(path = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataPage(Model model){
        if (!model.containsAttribute("uvStartDate")) {
            setDefaultDateRange(model);
        }
        return "/site/admin/data";
    }

    //统计网站UV
    @RequestMapping(path="/data/uv",method = RequestMethod.POST)
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model){
        long uv = dataService.calculateUV(start,end);
        model.addAttribute("uvResult",uv);
        model.addAttribute("uvStartDate",start);
        model.addAttribute("uvEndDate",end);
        model.addAttribute("dauStartDate", start);
        model.addAttribute("dauEndDate", end);

        return "forward:/data";

    }

    @RequestMapping(path="/data/dau",method = RequestMethod.POST)
    public String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model){
        long dau = dataService.caculateDAU(start,end);
        model.addAttribute("dauResult",dau);
        model.addAttribute("dauStartDate",start);
        model.addAttribute("dauEndDate",end);
        model.addAttribute("uvStartDate", start);
        model.addAttribute("uvEndDate", end);

        return "forward:/data";

    }
}
