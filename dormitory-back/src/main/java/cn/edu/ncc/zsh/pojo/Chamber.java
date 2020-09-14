package cn.edu.ncc.zsh.pojo;

import cn.edu.ncc.zsh.sys.Model;
import lombok.Data;

@Data
public class Chamber extends Model {

    private String chamberName;
    private Integer chamberNo;
    private Integer people;
    private Integer peoples;
    private String chamber;

}
