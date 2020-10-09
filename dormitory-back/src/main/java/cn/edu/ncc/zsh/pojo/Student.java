package cn.edu.ncc.zsh.pojo;

import cn.edu.ncc.zsh.sys.Model;
import lombok.Data;

/**
 * @author 学生类
 */
@Data
public class Student extends Model {

    private Integer studentNo;
    private String password;
    private String name;
    private Integer sex;
    private String number;
    private String address;
    private String className;
    private String chamber;
    private Integer people;

}
