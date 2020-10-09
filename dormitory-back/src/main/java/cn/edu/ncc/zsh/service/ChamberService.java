package cn.edu.ncc.zsh.service;

import cn.edu.ncc.zsh.dao.ChamberDao;
import cn.edu.ncc.zsh.pojo.Chamber;
import cn.edu.ncc.zsh.sys.ModelService;
import org.springframework.stereotype.Service;

@Service
public class ChamberService extends ModelService<Chamber, ChamberDao> {
    /*验证宿舍号是否重复*/
    public Boolean validChamberNo(Integer chamberNo) {
        Chamber conditon = new Chamber();
        conditon.setChamberNo(chamberNo);
        return dao.find(conditon) !=null;
    }
}
