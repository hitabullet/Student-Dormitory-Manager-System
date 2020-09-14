package cn.edu.ncc.zsh.service;


import cn.edu.ncc.zsh.dao.StudentDao;
import cn.edu.ncc.zsh.pojo.Student;
import cn.edu.ncc.zsh.sys.ModelService;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends ModelService<Student, StudentDao> {
    /**
     * 验证学号是否重复
     * @param studentNo
     * @return
     */
    public Boolean validStudentNo(Integer studentNo) {
        Student conditon = new Student();
        conditon.setStudentNo(studentNo);
        return dao.findNo(conditon) !=null;
    }

}
