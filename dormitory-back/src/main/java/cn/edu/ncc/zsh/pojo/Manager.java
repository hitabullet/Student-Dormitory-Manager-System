package cn.edu.ncc.zsh.pojo;

import cn.edu.ncc.zsh.sys.Model;
import lombok.Data;

@Data
public class Manager extends Model {

    private String username;
    private String password;

}
