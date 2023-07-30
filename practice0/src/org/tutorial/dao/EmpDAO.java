package org.tutorial.dao;

import java.util.List;

import org.tutorial.model.EmpDO;

public interface EmpDAO {

    void insert(EmpDO empDO);

    void update(EmpDO empDO);

    void delete(Integer empno);

    EmpDO findByPrimaryKey(Integer empno);

    List<EmpDO> getAll();

}
