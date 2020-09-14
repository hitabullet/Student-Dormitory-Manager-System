package cn.edu.ncc.zsh.controller;

import cn.edu.ncc.zsh.pojo.Chamber;
import cn.edu.ncc.zsh.service.ChamberService;
import cn.edu.ncc.zsh.service.StudentService;
import cn.edu.ncc.zsh.sys.DTO;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamber")
public class ChamberController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ChamberService chamberService;

    /**
     * 条件查询分页数据以及升序
     *
     * @param dto
     * @return
     */
    @GetMapping("page")
    public PageInfo<Chamber> page(DTO<Chamber> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Chamber chamber = (Chamber) JSONObject.toBean(jsonObject,Chamber.class);
        return chamberService.page(chamber, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 降序分页数据
     *
     * @param dto
     * @return
     */
    @GetMapping("pageDesc")
    public  PageInfo<Chamber> pageDesc(DTO<Chamber> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Chamber chamber = (Chamber)JSONObject.toBean(jsonObject,Chamber.class);
        return chamberService.pageDesc(chamber, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 提交修改数据
     *
     * @param chamber
     * @return
     */
    @PostMapping("save")
    public Boolean save(@RequestBody Chamber chamber){
        chamberService.save(chamber);
        return true;
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Integer id){
        chamberService.delete(id);
        return true;
    }

    /**
     * 查询宿舍是否重复
     *
     * @param chamberNo
     * @return
     */
    @GetMapping("validChamberNo/{value}")
    public Boolean validChamberNo(@PathVariable("value")Integer chamberNo){
        return chamberService.validChamberNo(chamberNo);
    }

    /**
     * 添加数据
     * @param
     * @return
     */
    @PostMapping("chamberList")
    public Boolean insert(@RequestBody Chamber chamber){
        chamberService.save(chamber);
        return true;
    }

}
