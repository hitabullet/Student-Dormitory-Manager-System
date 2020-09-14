package cn.edu.ncc.zsh.sys;

import lombok.Data;

@Data
public class DTO<T> {
    private Integer pageNum;
    private Integer pageSize;
    private T condition;
}
