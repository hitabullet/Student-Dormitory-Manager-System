package cn.edu.ncc.zsh.pojo;

import cn.edu.ncc.zsh.sys.Model;
import lombok.Data;

@Data
public class Record extends Model {
    private String num;
    private String repairtime;
    // 入住总人数
    private Integer checkSum;
    // 已经退寝人数
    private Integer exitCheckSum;
    // 未入住人数
    private Integer uncheckSum;
}
