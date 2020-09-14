package cn.edu.ncc.zsh.controller;

import cn.edu.ncc.zsh.pojo.Chamber;
import cn.edu.ncc.zsh.pojo.Student;
import cn.edu.ncc.zsh.service.ChamberService;
import cn.edu.ncc.zsh.service.StudentService;
import cn.edu.ncc.zsh.sys.DTO;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ChamberService chamberService;

    /**
     * 条件查询分页数据
     *
     * @param dto
     * @return
     */
    @GetMapping("page")
    public  PageInfo<Student> page(DTO<Student> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Student student = (Student)JSONObject.toBean(jsonObject,Student.class);
        return studentService.page(student, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 降序分页数据
     *
     * @param dto
     * @return
     */
    @GetMapping("pageDesc")
    public  PageInfo<Student> pageDesc(DTO<Student> dto) {
        /*转换前台提交的数据为对象*/
        JSONObject jsonObject = JSONObject.fromObject(dto.getCondition());
        Student student = (Student)JSONObject.toBean(jsonObject,Student.class);
        return studentService.pageDesc(student, dto.getPageNum(), dto.getPageSize());
    }

    /**
     * 提交修改数据
     *
     * @param student
     * @return
     */
    @PostMapping("save")
    public Boolean save(@RequestBody Student student){
         studentService.save(student);
         return true;
    }

    /**
     * 删除数据
     * @param
     * @return
     */
    @PostMapping("delete")
    public Boolean delete(@RequestBody Student student){
        studentService.delete(student.getId());
        Chamber chamber = chamberService.selectbychamber(student.getChamber());
        chamber.setPeople(studentService.findChamber(student.getChamber()));
        chamberService.save(chamber);
        return true;
    }


    /**
     * 添加数据
     * @param
     * @return
     */
    @PostMapping("addList")
    public Boolean insert(@RequestBody Student student){
        studentService.save(student);
        Chamber chamber = chamberService.selectbychamber(student.getChamber());
        chamber.setPeople(studentService.findChamber(student.getChamber()));
        chamberService.save(chamber);
        return true;
    }


    /**
     * 查询学号是否重复
     *
     * @param studentNo
     * @return
     */
    @GetMapping("validStudentNo/{value}")
    public Boolean validStudentNo(@PathVariable("value")Integer studentNo){
        return studentService.validStudentNo(studentNo);
    }

    /**
     * 查询寝室号是否有六人
     *
     * @param
     * @return
     */
    @GetMapping("validChamber/{value}")
    public Boolean validChamber(@PathVariable("value")String chamber){
        int num = studentService.findChamber(chamber);
        if (num == 6){
                return true;
            }

        return false;
    }


}
