package cn.edu.ncc.zsh.controller;

import cn.edu.ncc.zsh.pojo.Repairs;
import cn.edu.ncc.zsh.service.RepairsService;
import cn.edu.ncc.zsh.sys.DTO;
import com.github.pagehelper.PageInfo;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repairs")
public class RepairsController {
    @Autowired
    private RepairsService repairsService;

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
        return repairsService.page(repairs, dto.getPageNum(), dto.getPageSize());
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
        return repairsService.pageDesc(repairs, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 添加数据
     * @param
     * @return
     */
    @PostMapping("repairsList")
    public Boolean insert(@RequestBody Repairs repairs){
        repairsService.save(repairs);
        return true;
    }

    /**
     * 提交修改数据
     *
     * @param repairs
     * @return
     */
    @PostMapping("save")
    public Boolean save(@RequestBody Repairs repairs){
        repairsService.save(repairs);
        return true;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Integer id){
        repairsService.delete(id);
        return true;
    }

}
