package com.willpower.travel.servlet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.willpower.travel.pojo.Hotel;
import com.willpower.travel.pojo.ScenicSpot;
import com.willpower.travel.pojo.UserOrder;
import com.willpower.travel.service.UserOrderService;
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
 * @date: 2021/5/22 9:57;
 * @Description:
 */
@Controller
public class UserOrderController {
    @Autowired
    UserOrderService orderService;
    //分页查询跳转页面
    @RequestMapping("order_list")
    public String list(@RequestParam(defaultValue = "1") Long pageNumber,
                       @RequestParam(defaultValue = "5") Long pageSize,
                       @RequestParam(defaultValue = "") String title,
                       Model model){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("delete_status", 0);
        wrapper.orderByDesc("add_time");
        if (!"".equals(title)){
            wrapper.like("product_name", title);
        }
        IPage page = orderService.page(new Page<>(pageNumber, pageSize), wrapper);
        //封装工具类
        PagerHelper<ScenicSpot> pagerHelper = new PagerHelper(pageNumber,pageSize,page.getPages(),page.getTotal(),page.getRecords());
        model.addAttribute("pagerHelper",pagerHelper);
        model.addAttribute("title", title);//数据回显
        return "order/orderList";
    }

    //带数据跳转详情界面
    @GetMapping("order_toview/{id}")
    public String toview(@PathVariable("id") String id,Model model){
        model.addAttribute("entity", orderService.getById(id));
        return "order/orderView";
    }

    //带数据跳转到更新数据页面
    @GetMapping("order_toupdate/{id}")
    public String toupdate(@PathVariable("id") String id,Model model){
        System.out.println(id);
        model.addAttribute("entity", orderService.getById(id));
        return "order/orderEdit";
    }
    //执行更新的方法   异步更新
    @ResponseBody
    @PostMapping("order_update")
    public CommonResult update(UserOrder order, HttpSession session){
        try {
            //设置当前系统时间为更新事件
            order.setModifyTime(DataUtil.getNowTime());
            //设置修改人id
            order.setModifyUserId("b496894b89754a848e9b74ff66a05d44");
            System.out.println("要更新的对象是:"+order);
            return new CommonResult(200,"ok",orderService.updateById(order));
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"error");
        }
    }
    //使用异步删除
    @ResponseBody
    @GetMapping("order_delete/{id}")
    public CommonResult delete(@PathVariable("id") String id){
        //TODO 后面补代码如果该景点被使用则不能被删除
        UserOrder order = orderService.getById(id);
        order.setDeleteStatus(1);
        return new CommonResult(200,"ok",orderService.updateById(order));
    }
}
