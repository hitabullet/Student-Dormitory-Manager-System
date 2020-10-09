package cn.edu.ncc.zsh.controller;



import cn.edu.ncc.zsh.pojo.Student;
import cn.edu.ncc.zsh.service.FrontLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 学生登录注册以及忘记密码
 */
@RestController
@RequestMapping("/student")
public class FrontLoginController {
    @Autowired
    private FrontLoginService frontLoginService;

    /**
     * 处理登录功能
     *
     * @param student,session,response
     * @return
     */
    @PostMapping("login")//代表这个方法处理/student/login的post请求
    public String studentLogin(@RequestBody Student student, HttpSession session,
                               HttpServletResponse response) {
        //将用户名和密码作为条件进行数据查询，如果查到有数据，代表登录成功
        Student principal = frontLoginService.find(student);

        if (principal != null) {
            //代表登录成功,将principal存入session中
            session.setAttribute("principal", principal);
            return "success";
        }
        response.setStatus(403);
        return null;

    }

    /**
     * 处理注册功能
     *
     * @param student
     * @return
     */
    @PostMapping("register")
    public String studentRegister(@RequestBody Student student) {

        frontLoginService.save(student);
        return "success";
    }

    /**
     * 查询学号是否信息录入
     *
     * @param studentNo
     * @return
     */
    @GetMapping("valid/{value}")
    public Boolean validStudentNo(@PathVariable("value") Integer studentNo) {
        int num = frontLoginService.findById(studentNo);
           if (num == 1) {
               return false;
           } else {
               return true;
           }
    }

    /**
     * 忘记密码
     *
     * @param student
     * @return
     */
    @PostMapping("update")
    public boolean studentUpdate(@RequestBody Student student) {
        frontLoginService.updateInfo(student);
        return true;
    }

    /**
     * 忘记密码
     *
     * @param student
     * @return
     */
    @PostMapping("forget")
    public boolean studentForget(@RequestBody Student student) {
        return frontLoginService.forget(student);
    }
}
