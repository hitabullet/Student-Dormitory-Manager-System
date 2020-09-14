package cn.edu.ncc.zsh.sys;

import cn.edu.ncc.zsh.pojo.Student;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class ModelService <T extends Model,D extends ModelDao<T>>{

    @Autowired
    protected D dao;

    public T find(T condition){
        return dao.find(condition);
    }

    public int findById(int id) {return dao.findById(id);};

    public T findNo(T condition){
        return dao.findNo(condition);
    }

    public T findSNum(T condition){
        return dao.findSNum(condition);
    }

    public T findLeave(T condition){
        return dao.findLeave(condition);
    }

    public T findQuit(T condition){
        return dao.findQuit(condition);
    }

    public T findMaintain(T condition){
        return dao.findMaintain(condition);
    }

    public List<T> findRepair(T condition){
        return dao.findRepair(condition);
    }

    public List<T> findList(T condition){
        return dao.findList(condition);
    }

    public List<T> findListDesc(T condition){
        return dao.findListDesc(condition);
    }

    public List<T> findAll(T condition){
        return dao.findAll(condition);
    }

    public List<T> findPeople(int people){
        return dao.findPeople(people);
    }

    public void delete(int id){
        dao.delete(id);
    }

    public Boolean forget(T condition){return  dao.forget(condition);}

    public void updateChamber(T condition){dao.updateChamber(condition);}

    public void updateInfo(T condition){dao.updateInfo(condition);}

    public int findChamber(String chamber){
       return dao.findChamber(chamber);
    }

    public  String editChamber(String chamber){
        return dao.editChamber(chamber);
    }

    public T selectbychamber(String chamber){
        return dao.selectbychamber(chamber);
    }

    public void save(T t){
        if (t.getId()==null){
            dao.insert(t);
        }else {
            dao.update(t);
        }
    }
    /**
     * 根据条件进行分页查询
     * @param condition
     * @return
     */
    public PageInfo<T> page(T condition, Integer pageNum, Integer pageSize){
        return PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(()->dao.findList(condition));
    }

    /**
     * 降序
     * @param condition
     * @return
     */
    public PageInfo<T> pageDesc(T condition, Integer pageNum, Integer pageSize){

        return PageHelper.startPage(pageNum,pageSize).doSelectPageInfo(()->dao.findListDesc(condition));
    }


}
