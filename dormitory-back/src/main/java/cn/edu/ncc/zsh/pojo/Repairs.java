package cn.edu.ncc.zsh.pojo;

import cn.edu.ncc.zsh.sys.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.util.Date;

@Data
public class Repairs extends Model {

    private String chamber;
    private String message;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date repairtime;

}
