package cn.edu.ncc.zsh.service;

import cn.edu.ncc.zsh.dao.RecordDao;
import cn.edu.ncc.zsh.pojo.Record;
import cn.edu.ncc.zsh.sys.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService extends ModelService<Record, RecordDao> {

    public Record findSNum(Record record){
        return dao.findSNum(record);
    }

    public Record findLeave(Record record){
        return dao.findLeave(record);
    }

    public Record findMaintain(Record record){
        return dao.findMaintain(record);
    }

    public List<Record> findRepair(Record record){
        return dao.findRepair(record);
    }

}
