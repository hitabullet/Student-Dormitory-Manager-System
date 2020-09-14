package cn.edu.ncc.zsh.controller;


import cn.edu.ncc.zsh.pojo.Repairs;
import cn.edu.ncc.zsh.service.DormitoryRepairsService;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
