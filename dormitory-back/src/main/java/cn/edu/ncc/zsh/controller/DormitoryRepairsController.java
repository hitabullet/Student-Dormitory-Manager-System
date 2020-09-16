package cn.edu.ncc.zsh.controller;


import cn.edu.ncc.zsh.pojo.Repairs;
import cn.edu.ncc.zsh.service.DormitoryRepairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/DormRepairs")
public class DormitoryRepairsController {
    @Autowired
    private DormitoryRepairsService dormitoryRepairsService;

    /**
     * 添加数据
     * @param
     * @return
     */
    @PostMapping("add")
    public Boolean insert(@RequestBody Repairs repairs){

        dormitoryRepairsService.save(repairs);
        return true;
    }
    /**
     * 查询该学号是否宿舍入住
     *
     * @param studentNo
     * @return
     */
    @GetMapping("valid/{value}")
    public Boolean validStudentNo(@PathVariable("value") Integer studentNo) {
        int num = dormitoryRepairsService.findById(studentNo);
        if (num == 1) {
            return false;
        } else {
            return true;
        }
    }
}
