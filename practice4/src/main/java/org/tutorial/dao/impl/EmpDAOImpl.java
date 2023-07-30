package org.tutorial.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.tutorial.dao.EmpDAO;
import org.tutorial.model.DO.EmpDO;

@Repository
public class EmpDAOImpl implements EmpDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int insert(EmpDO empDO) {
		Integer key = null;
		entityManager.persist(empDO);
		key = empDO.getEmpno();
		return key;
	}

	@Override
	public void update(EmpDO empDO) {
		// 根據 ID 獲取對應的實體物件
		EmpDO existingEmp = entityManager.find(EmpDO.class, empDO.getEmpno());
		System.out.println("empDO:" + empDO);
		if (existingEmp != null) {
			// 更新實體物件的屬性值
			existingEmp.setEname(empDO.getEname());
			existingEmp.setJob(empDO.getJob());
			existingEmp.setHiredate(empDO.getHiredate());
			existingEmp.setSal(empDO.getSal());
			existingEmp.setComm(empDO.getComm());
			existingEmp.setDeptDO(empDO.getDeptDO());
			existingEmp.setEmpno(empDO.getEmpno());
		} else {
			System.out.println("查無資料，無法進行修改！");
		}
	}

	@Override
	public void delete(Integer empno) {
		EmpDO existingEmp = entityManager.find(EmpDO.class, empno);
		// 判斷非空、以防空指標異常
		if (existingEmp != null) {
			entityManager.remove(existingEmp);
		} else {
			System.out.println("無此資料，無法進行刪除！");
		}
	}

	@Override
	public EmpDO findByPrimaryKey(Integer empno) {
		EmpDO empDO = entityManager.find(EmpDO.class, empno);
		return empDO;
	}

//	@SuppressWarnings("unchecked")
	@Override
	public List<EmpDO> getAll() {
//		1. jpql
		String jpql = "SELECT e FROM EmpDO e";

//		返回的 Query 類型是一個泛型類型，表示一個未指定結果類型的查詢。
//		這樣的查詢對象在執行查詢並獲取結果時，需要進行類型轉換才能將結果轉換為期望的類型。
//		編譯器在這種情況下會生成一個“未經檢查的轉換”警告，
//		因為編譯器無法驗證轉換的類型是否安全。為了抑制這個警告，
//		可以使用 @SuppressWarnings("unchecked") 注解來告訴編譯器在這個地方忽略該警告。
//		Query query = entityManager.createQuery(jpql);

//		使用 TypedQuery 可以提供更好的類型安全性
		TypedQuery<EmpDO> query = entityManager.createQuery(jpql, EmpDO.class);

//		2. 原生SQL
//        沒有TypedQuery
//        String sql = "SELECT * FROM EMP2";
//        Query query = entityManager.createNativeQuery(sql, EmpDO.class);

//      3. NamedQuery
//		需另在在實體類上使用 @NamedQuery 注解定義命名查詢
//        TypedQuery<EmpDO> query = entityManager.createNamedQuery("EmpDO.findAll", EmpDO.class);

//		4. Criteria
//		獲取一個 CriteriaBuilder 對象，它用於構建 Criteria 查詢的各個組件
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		創建一個 CriteriaQuery<EmpDO> 對象，指定查詢的返回類型為 EmpDO
//		CriteriaQuery<EmpDO> criteriaQuery = criteriaBuilder.createQuery(EmpDO.class);
//		創建一個 Root<EmpDO> 對象，表示查詢的根實體，即從哪個實體開始進行查詢
//		Root<EmpDO> root = criteriaQuery.from(EmpDO.class);
//		通過 criteriaQuery.select(root) 指定查詢的結果，這里表示查詢所有的字段
//		criteriaQuery.select(root);
//		TypedQuery<EmpDO> query = entityManager.createQuery(criteriaQuery);

		List<EmpDO> list = query.getResultList(); // 要在entityManager關掉之前get出來
		return list;
	}
}
