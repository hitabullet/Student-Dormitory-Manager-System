package cn.edu.ncc.zsh.controller;


import cn.edu.ncc.zsh.pojo.Manager;
import cn.edu.ncc.zsh.service.ManagerService;
import cn.edu.ncc.zsh.sys.DTO;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /*
     * 处理登录请求
     * */
    @PostMapping("login")//代表这个方法处理/manager/login的post请求
    public String managerLogin(@RequestBody Manager manager, HttpSession session,
                            HttpServletResponse response) {
        //将用户名和密码作为条件进行数据查询，如果查到有数据，代表登录成功
        Manager principal = managerService.find(manager);


        if (principal != null){
            //代表登录成功,将principal存入session中
            session.setAttribute("principal",principal);
            return "success";
        }
        response.setStatus(403);
        return null;

    }


    /**
     * 条件查询分页数据
     *
     * @param dto
     * @return
     */
    @GetMapping("page")
    public PageInfo<Manager> page(DTO<Manager> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Manager manager = (Manager)JSONObject.toBean(jsonObject, Manager.class);
        return managerService.page(manager, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 分页数据降序
     *
     * @param dto
     * @return
     */
    @GetMapping("pageDesc")
    public PageInfo<Manager> pageDesc(DTO<Manager> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Manager manager = (Manager)JSONObject.toBean(jsonObject, Manager.class);
        return managerService.pageDesc(manager, dto.getPageNum(), dto.getPageSize());
    }

}
