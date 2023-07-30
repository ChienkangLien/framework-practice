package org.tutorial.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.tutorial.dao.DeptDAO;
import org.tutorial.model.DO.DeptDO;
import org.tutorial.model.DO.EmpDO;
@Repository
public class DeptDAOImpl implements DeptDAO {

	private static final String WILD_CARD = "%";
	
	@PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional
	public void insert(DeptDO deptDO) {
			entityManager.persist(deptDO);
	}

	@Override
	@Transactional
	public void update(DeptDO deptDO) {
		// 根據 ID 獲取對應的實體物件
		DeptDO existingDept = entityManager.find(DeptDO.class, deptDO.getDeptno());
		
		if (existingDept != null) {
				// 更新實體物件的屬性值
				existingDept.setDname(deptDO.getDname());
				existingDept.setLoc(deptDO.getLoc());
		} else {
			System.out.println("查無資料，無法進行修改！");
		}
	}

	@Override
	@Transactional
	public void delete(Integer deptno) {
		DeptDO existingDept = entityManager.find(DeptDO.class, deptno);

		// 判斷非空、以防空指標異常
		if (existingDept != null) {
				entityManager.remove(existingDept);
		} else {
			System.out.println("無此資料，無法進行刪除！");
		}
	}

	@Override
	@Transactional
	public DeptDO findByPrimaryKey(Integer deptno) {
		DeptDO deptDO = entityManager.find(DeptDO.class, deptno);
		return deptDO;
	}

//	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DeptDO> getAll() {
//		1. jpql
		String jpql = "SELECT d FROM DeptDO d";

//		返回的 Query 類型是一個泛型類型，表示一個未指定結果類型的查詢。
//		這樣的查詢對象在執行查詢並獲取結果時，需要進行類型轉換才能將結果轉換為期望的類型。
//		編譯器在這種情況下會生成一個“未經檢查的轉換”警告，
//		因為編譯器無法驗證轉換的類型是否安全。為了抑制這個警告，
//		可以使用 @SuppressWarnings("unchecked") 注解來告訴編譯器在這個地方忽略該警告。
//		Query query = entityManager.createQuery(jpql);

//		使用 TypedQuery 可以提供更好的類型安全性
		TypedQuery<DeptDO> query = entityManager.createQuery(jpql, DeptDO.class);

//		2. 原生SQL
//        沒有TypedQuery
//        String sql = "SELECT * FROM DEPT2";
//        Query query = entityManager.createNativeQuery(sql, DeptDO.class);

//      3. NamedQuery
//		需另在在實體類上使用 @NamedQuery 注解定義命名查詢
//        TypedQuery<DeptDO> query = entityManager.createNamedQuery("DeptDO.findAll", DeptDO.class);

//		4. Criteria
////		獲取一個 CriteriaBuilder 對象，它用於構建 Criteria 查詢的各個組件
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
////		創建一個 CriteriaQuery<DeptDO> 對象，指定查詢的返回類型為 DeptDO
//		CriteriaQuery<DeptDO> criteriaQuery = criteriaBuilder.createQuery(DeptDO.class);
////		創建一個 Root<DeptDO> 對象，表示查詢的根實體，即從哪個實體開始進行查詢
//		Root<DeptDO> root = criteriaQuery.from(DeptDO.class);
////		通過 criteriaQuery.select(root) 指定查詢的結果，這里表示查詢所有的字段
//		criteriaQuery.select(root);
//		TypedQuery<DeptDO> query = entityManager.createQuery(criteriaQuery);

		List<DeptDO> list = query.getResultList();
		return list;
	}

	@Override
	@Transactional
	public List<EmpDO> getEmpsByDeptno(Integer deptno) {
//		使用FETCH以防懶加載失效
//		1. jpql
		String jpql = "SELECT d FROM DeptDO d LEFT JOIN FETCH d.empDOs WHERE d.deptno = :deptno";

//		返回的 Query 類型是一個泛型類型，表示一個未指定結果類型的查詢。
//		這樣的查詢對象在執行查詢並獲取結果時，需要進行類型轉換才能將結果轉換為期望的類型。
//		編譯器在這種情況下會生成一個“未經檢查的轉換”警告，
//		因為編譯器無法驗證轉換的類型是否安全。為了抑制這個警告，
//		可以使用 @SuppressWarnings("unchecked") 注解來告訴編譯器在這個地方忽略該警告。
//		Query query = entityManager.createQuery(jpql);

//		使用 TypedQuery 可以提供更好的類型安全性
		TypedQuery<DeptDO> query = entityManager.createQuery(jpql, DeptDO.class);
		query.setParameter("deptno", deptno);

//		2. 原生SQL
//        沒有TypedQuery、也沒有LEFT JOIN FETCH
//        String sql = "SELECT * FROM DEPT2 d LEFT JOIN EMP2 e ON d.deptno = e.deptno WHERE d.deptno = :deptno";
//        Query query = entityManager.createNativeQuery(sql, DeptDO.class);
//        query.setParameter("deptno", deptno);

//      3. NamedQuery
//		需另在在實體類上使用 @NamedQuery 注解定義命名查詢
//        TypedQuery<DeptDO> query = entityManager.createNamedQuery("DeptDO.findByDeptno", DeptDO.class);
//        query.setParameter("deptno", deptno);

//		4. Criteria
////		獲取一個 CriteriaBuilder 對象，它用於構建 Criteria 查詢的各個組件
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
////		創建一個 CriteriaQuery<DeptDO> 對象，指定查詢的返回類型為 DeptDO
//		CriteriaQuery<DeptDO> criteriaQuery = criteriaBuilder.createQuery(DeptDO.class);
////		創建一個 Root<DeptDO> 對象，表示查詢的根實體，即從哪個實體開始進行查詢
//		Root<DeptDO> root = criteriaQuery.from(DeptDO.class);
////		關聯的預加載，即指定要關聯的屬性 "empDOs" 進行左外連接查詢
//		root.fetch("empDOs", JoinType.LEFT);
////		通過 criteriaQuery.select(root) 指定查詢的結果，這里表示查詢所有的字段
//		criteriaQuery.select(root);
////		設置查詢條件，即限定部門編號為指定的值
//		criteriaQuery.where(criteriaBuilder.equal(root.get("deptno"), deptno));
//		TypedQuery<DeptDO> query = entityManager.createQuery(criteriaQuery);

		List<EmpDO> list = query.getSingleResult().getEmpDOs();
		return list;
	}

	@Override
	@Transactional
	public List<DeptDO> findByCriteria(DeptDO deptDO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DeptDO> criteriaQuery = criteriaBuilder.createQuery(DeptDO.class);
		Root<DeptDO> column = criteriaQuery.from(DeptDO.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (deptDO.getDeptno() != null) {
			predicates.add(criteriaBuilder.equal(column.get("deptno"), deptDO.getDeptno()));
		}
		if (StringUtils.isNoneBlank(deptDO.getDname())) {
			predicates.add(criteriaBuilder.like(column.get("dname"), WILD_CARD + deptDO.getDname() + WILD_CARD));
		}
		if (StringUtils.isNoneBlank(deptDO.getLoc())) {
			predicates.add(criteriaBuilder.like(column.get("loc"), WILD_CARD + deptDO.getLoc() + WILD_CARD));
		}

//		criteriaBuilder.and((Predicate[]) predicates.toArray())強制類型轉換
//		可能會引發 ClassCastException 異常；以下方式更為常用
//		指定 new Predicate[0] 作為目標數組，
//		系統會根據集合的大小自動創建一個與集合大小相等的新數組。
//		這種方式避免了手動類型轉換，並且能夠確保返回的數組長度與集合大小一致
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
		TypedQuery<DeptDO> query = entityManager.createQuery(criteriaQuery);

		List<DeptDO> list = query.getResultList();
		return list;
	}

}
