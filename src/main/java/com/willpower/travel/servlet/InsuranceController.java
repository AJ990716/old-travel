package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.Car;
import com.willpower.travel.pojo.Insurance;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.service.CarService;
import com.willpower.travel.service.InsuranceService;
import com.willpower.travel.utils.CommonResult;
import com.willpower.travel.utils.DataUtil;
import com.willpower.travel.utils.PagerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.servlet;
 * @author: Administrator;
 * @date: 2021/5/22 17:43;
 * @Description:
 */
@Controller
public class InsuranceController {
    @Autowired
    InsuranceService insuranceService;
    //分页查询跳转页面
    @RequestMapping("insurance_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       @RequestParam(defaultValue = "") String title,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(title)){
            wrapper.like("title", title);
        }
        IPage page = insuranceService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);//数据回显
        return "insurance/insuranceList";
    }

    //新增
    @ResponseBody
    @PostMapping("insurance_add")
    public CommonResult add(Insurance insurance){
        System.out.println(insurance);
        insurance.setAddTime(DataUtil.getNowTime());
        insurance.setDeleteStatus(0);
        insurance.setAddUserId("b496894b89754a848e9b74ff66a05d44");
        return new CommonResult(200,"ok",insuranceService.save(insurance));
    }
    //带数据跳转详情界面
    @GetMapping("insurance_toview/{id}")
    public String toview(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", insuranceService.getById(id));
        return "insurance/insuranceView";
    }
    //带数据跳转到更新数据页面
    @GetMapping("insurance_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id,Model model){
        System.out.println(id);
        model.addAttribute("entity", insuranceService.getById(id));
        return "insurance/insuranceEdit";
    }
    //执行更新的方法   异步更新
    @ResponseBody
    @PostMapping("insurance_update")
    public CommonResult update(Insurance insurance, HttpSession session){
        try {
            Insurance newInsurance = insuranceService.getById(insurance.getId());
            if (!newInsurance.getImgUrl().equals(insurance.getImgUrl())){
                //此处肯定上传了新的图片，删除老的图片
                String realPath = ResourceUtils.getURL("classpath:").getPath();
                String filePath= insurance.getImgUrl();
                realPath=realPath.substring(1,realPath.length())+"static"+filePath;
                System.out.println("realPath:"+realPath);
                File f=new File(realPath);
                if (f.exists()){
                    f.delete();
                    System.out.println("删除了老的图片,地址是："+realPath);
                }
            }
            //设置当前系统时间为更新事件
            insurance.setModifyTime(DataUtil.getNowTime());
            //设置修改人id
            insurance.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+insurance);
            return new CommonResult(200,"ok",insuranceService.updateById(insurance));
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"error");
        }
    }
    //使用异步删除
    @ResponseBody
    @GetMapping("insurance_delete/{id}")
    public CommonResult delete(@PathVariable("id") String id){
        //TODO 后面补代码如果该景点被使用则不能被删除
        Insurance insurance = insuranceService.getById(id);
        insurance.setDeleteStatus(1);
        return new CommonResult(200,"ok",insuranceService.updateById(insurance));
    }
}
