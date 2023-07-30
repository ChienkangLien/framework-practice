package org.tutorial.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.tutorial.dao.EmpDAO;
import org.tutorial.model.EmpDO;
import org.tutorial.utils.JPAUtil;

public class EmpDAOImpl implements EmpDAO {

	@Override
	public int insert(EmpDO empDO) {
		Integer key = null;
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(empDO);
			key = empDO.getEmpno();
			transaction.commit();

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		}
		entityManager.close();
		return key;
	}

	@Override
	public void update(EmpDO empDO) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		// 根據 ID 獲取對應的實體物件
		EmpDO existingEmp = entityManager.find(EmpDO.class, empDO.getEmpno());
		if (existingEmp != null) {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				// 更新實體物件的屬性值
				existingEmp.setEname(empDO.getEname());
				existingEmp.setJob(empDO.getJob());
				existingEmp.setHiredate(empDO.getHiredate());
				existingEmp.setSal(empDO.getSal());
				existingEmp.setComm(empDO.getComm());
				existingEmp.setDeptDO(empDO.getDeptDO());
				existingEmp.setEmpno(empDO.getEmpno());

				// 在事務提交前將更改同步到資料庫(非必要)，使用時機可參考以下
				// 確保立即可見的變更
				// 避免長時間事務
				// 處理併發衝突
				entityManager.flush();
				transaction.commit();
			} catch (Exception e) {
				transaction.rollback();
				e.printStackTrace();
			}
		} else {
			System.out.println("查無資料，無法進行修改！");
		}

//		將傳入的物件與資料庫中的對應物件合併，並在必要時插入新物件。
//		如果資料庫中不存在具有相同 ID 的資料，merge 方法將認為該物件是新的
//		並將其插入到資料庫中
//		entityManager.merge(empDO);

		entityManager.close();
	}

	@Override
	public void delete(Integer empno) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EmpDO existingEmp = entityManager.find(EmpDO.class, empno);

		// 判斷非空、以防空指標異常
		if (existingEmp != null) {
			EntityTransaction transaction = entityManager.getTransaction();
			try {
				transaction.begin();
				entityManager.remove(existingEmp);
				transaction.commit();

			} catch (Exception e) {
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				e.printStackTrace();
			}

		} else {
			System.out.println("無此資料，無法進行刪除！");
		}
		entityManager.close();

	}

	@Override
	public EmpDO findByPrimaryKey(Integer empno) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		EmpDO empDO = entityManager.find(EmpDO.class, empno);
		entityManager.close();
		return empDO;
	}

//	@SuppressWarnings("unchecked")
	@Override
	public List<EmpDO> getAll() {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
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
		entityManager.close();
		return list;
	}

}
