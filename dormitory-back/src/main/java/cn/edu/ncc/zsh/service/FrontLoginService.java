package cn.edu.ncc.zsh.service;

import cn.edu.ncc.zsh.dao.FrontLoginDao;
import cn.edu.ncc.zsh.pojo.Student;
import cn.edu.ncc.zsh.sys.ModelService;
import org.springframework.stereotype.Service;

@Service
public class FrontLoginService extends ModelService<Student, FrontLoginDao> {
    /*验证学号是否重复*/
    public Boolean validStudentNo(Integer studentNo) {
        Student conditon = new Student();
        conditon.setStudentNo(studentNo);
        return dao.find(conditon) !=null;
    }
}
