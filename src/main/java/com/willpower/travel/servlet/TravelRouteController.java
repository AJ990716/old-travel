package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.TravelRoute;
import com.willpower.travel.service.TravelRouteService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/18 11:40;
 * @Description: 旅游路线模块
 */
@Controller
public class TravelRouteController {
    @Autowired
    TravelRouteService routeService;

    //分页查询 支持各种访问
    @RequestMapping("travelRoute_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       @RequestParam(defaultValue = "") String title,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(title))wrapper.like("title",title);
        IPage page = routeService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<TravelRoute> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);
        return "travelRoute/travelRouteList";
    }
    //添加travelRoute对象 异步添加
    @ResponseBody
    @PostMapping("travelRoute_add")
    public CommonResult add(TravelRoute travelRoute){
        travelRoute.setAddTime(DataUtil.getNowTime());
        travelRoute.setDay(DataUtil.getTravelDay(travelRoute.getStartTime(),travelRoute.getEndTime()));
        //这里先写死后面再session中取
        travelRoute.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        System.out.println("要新增的对象是:"+travelRoute);
        return new CommonResult(200,"ok",routeService.save(travelRoute));
    }
    //带数据跳转页面
    @GetMapping("travelRoute_todetail/{id}")
    public String todetail(@PathVariable("id") String id, Model model){
        TravelRoute travelRoute = routeService.getById(id);
        model.addAttribute("entity", travelRoute);
        return "travelRoute/travelRouteView";
    }
    //带数据跳转到修改页面
    @GetMapping("travelRoute_toedit/{id}")
    public String toeidt(@PathVariable("id") String id,Model model){
        TravelRoute travelRoute = routeService.getById(id);
        model.addAttribute("entity", travelRoute);
        return "travelRoute/travelRouteEdit";
    }
    //根据id修改数据  异步修改
    @ResponseBody
    @PostMapping("travelRoute_edit")
    public CommonResult edit(TravelRoute travelRoute){
        try {
            //对象是数据库中原对象
            TravelRoute old_route = routeService.getById(travelRoute.getId());
            if (!old_route.getImgUrl().equals(travelRoute.getImgUrl())){
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath= old_route.getImgUrl();
                realPath=realPath.substring(1,realPath.length())+"static"+filePath;
                System.out.println("realPath:"+realPath);
                File f=new File(realPath);
                if (f.exists()){
                    f.delete();
                    System.out.println("删除了老的图片,地址是："+realPath);
                }
            }
            travelRoute.setModifyTime(DataUtil.getNowTime());
            travelRoute.setDay(DataUtil.getTravelDay(travelRoute.getStartTime(),travelRoute.getEndTime()));
            //这里先写死后面再session中取
            travelRoute.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            return new CommonResult(200,"ok",routeService.updateById(travelRoute));
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult(500,"error");
        }
    }
    //根据id删除 路线
    @ResponseBody
    @RequestMapping("travelRoute_delete/{id}")
    public CommonResult delete(@PathVariable("id") String id){
        System.out.println(id);
        TravelRoute travelRoute = routeService.getById(id);
        travelRoute.setDeleteStatus(1);
        boolean delete = routeService.updateById(travelRoute);
        return new CommonResult(delete?200:500,"error",delete);
    }
}
