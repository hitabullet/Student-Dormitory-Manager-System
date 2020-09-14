package cn.edu.ncc.zsh.controller;

import cn.edu.ncc.zsh.pojo.Chamber;
import cn.edu.ncc.zsh.pojo.Student;
import cn.edu.ncc.zsh.service.FrontLoginService;
import cn.edu.ncc.zsh.service.GetChamberService;
import cn.edu.ncc.zsh.sys.DTO;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/GetChamber")
public class GetChamberController {
    @Autowired
    private GetChamberService getChamberService;
    @Autowired
    private FrontLoginService frontLoginService;
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
        return getChamberService.page(chamber, dto.getPageNum(), dto.getPageSize());
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
        return getChamberService.pageDesc(chamber, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 查询该学号是否宿舍入住
     *
     * @param studentNo
     * @return
     */
    @GetMapping("valid/{value}")
    public Boolean validStudentNo(@PathVariable("value") Integer studentNo) {
        int num = getChamberService.findById(studentNo);
        if (num == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 入住宿舍
     * @param id
     * @return
     */
    @PostMapping("choose/{id}")
    public Boolean delete(@PathVariable Integer id){
        getChamberService.delete(id);
        return true;
    }

    /*
     * 处理宿舍入住
     * */
    @PostMapping("update")
    public boolean studentUpdate(@RequestBody Student student) {
        frontLoginService.updateChamber(student);
        return true;
    }



}
