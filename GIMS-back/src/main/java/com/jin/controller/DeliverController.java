package com.jin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jin.common.Result;
import com.jin.dto.DeliverDetailDto;
import com.jin.entity.Deliver;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author 金磊
 */
@RequestMapping("/deliver")
@RestController
public class DeliverController extends BaseController {

    @RequestMapping("/list")
    public Result getList(Long deliverId) {
        Page<Deliver> pageData = deliverService.page(getPage(),
                new QueryWrapper<Deliver>()
                        .like(deliverId != null, "deliver_id", deliverId)
        );

        return Result.success(pageData);
    }

    @GetMapping("/info/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        Deliver deliver = deliverService.getById(id);
        return Result.success(deliver);
    }

    @RequestMapping("/add")
    public Result addDeliver(@RequestBody Deliver deliver) {
        deliverService.save(deliver);
        return Result.success(deliver);
    }

    @RequestMapping("/update")
    public Result updateDeliver(@RequestBody Deliver deliver) {
        deliverService.updateById(deliver);
        return Result.success(deliver);
    }

    @RequestMapping("/delete")
    public Result deleteDeliver(@RequestBody Long[] ids) {
        deliverService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }

    @RequestMapping("/getDeliver")
    public Result getDeliverByStudentId(@RequestBody Long id) {
        List<Deliver> list = deliverService.getDeliverByStudentId(id);
        return Result.success(list);
    }

    @RequestMapping("/detail")
    public Result getDetailedInfo(@RequestBody Long id) {
        List<DeliverDetailDto> detailedInfo = deliverService.getDetailedInfo(id);
        return Result.success(detailedInfo);
    }

    @RequestMapping("/access")
    public Result access(@RequestBody Long id) {
        Deliver deliver = deliverService.getById(id);
        deliver.setDeliverStatus(1);
        deliverService.updateById(deliver);

        return Result.success("");
    }
}
