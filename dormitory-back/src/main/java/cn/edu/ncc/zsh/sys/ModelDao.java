package cn.edu.ncc.zsh.sys;


import java.util.List;

public interface ModelDao <T extends Model>{
    void insert(T t);

    void update(T t);

    T selectbychamber(String chamber);

    Boolean forget(T t);

    void updateChamber(T t);

    void updateInfo(T t);

    void delete(int id);

    T find(T condition);

    T findNo(T condition);

    int findById(int id);

    List<T> findList(T condition);

    List<T> findAll(T condition);

    List<T> findListDesc(T condition);

    int findChamber(String chamber);

    String editChamber(String chamber);

    List<T> findPeople(int people);
}
