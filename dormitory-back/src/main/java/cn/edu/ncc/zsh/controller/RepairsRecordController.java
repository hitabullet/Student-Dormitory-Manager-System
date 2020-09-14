package cn.edu.ncc.zsh.controller;

import cn.edu.ncc.zsh.pojo.Repairs;
import cn.edu.ncc.zsh.service.RepairsRecordService;
import cn.edu.ncc.zsh.sys.DTO;
import com.github.pagehelper.PageInfo;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/repairsRecord")
public class RepairsRecordController{
    @Autowired
    private RepairsRecordService repairsRecordService;

    /**
     * 条件查询分页数据以及升序
     *
     * @param dto
     * @return
     */
    @GetMapping("page")
    public PageInfo<Repairs> page(DTO<Repairs> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        /*识别时间类型,给格式*/
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd"}));

        Repairs repairs = (Repairs) JSONObject.toBean(jsonObject,Repairs.class);
        return repairsRecordService.page(repairs, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 降序分页数据
     *
     * @param dto
     * @return
     */
    @GetMapping("pageDesc")
    public  PageInfo<Repairs> pageDesc(DTO<Repairs> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Repairs repairs = (Repairs)JSONObject.toBean(jsonObject,Repairs.class);
        return repairsRecordService.pageDesc(repairs, dto.getPageNum(), dto.getPageSize());
    }


}
