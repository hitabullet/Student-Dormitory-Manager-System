package cn.edu.ncc.zsh.dao;

import cn.edu.ncc.zsh.pojo.Record;
import cn.edu.ncc.zsh.sys.ModelDao;

import java.util.List;

public interface RecordDao extends ModelDao<Record> {

    Record findLeave(Record record);

    Record findMaintain(Record record);

    Record findSNum(Record record);

    List<Record> findRepair(Record record);

}
