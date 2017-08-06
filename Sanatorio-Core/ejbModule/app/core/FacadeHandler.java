package app.core;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import app.client.core.BaseFacadeLocal;
import app.client.core.BaseFacadeRemote;
import app.client.utilities.CollectionsUtiliy;
import app.client.utilities.Constants;

/**
 * Session Bean implementation class FacadeHandler
 */
@Remote(BaseFacadeRemote.class)
@Local(BaseFacadeLocal.class)
@Stateless(mappedName = "ejb/FacadeHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class FacadeHandler implements BaseFacadeLocal, BaseFacadeRemote {

	@PersistenceContext(unitName = Constants.PERSISTENCE_NAME, name = Constants.PERSISTENCE_NAME)
	private EntityManager em;

	private FacadeHandler() {
		// This method is private for avoid class inicialization
	}

	private static <E> Query createQuery(EntityManager em, String QL, int init, int maxResult,
			Map<String, Object> params, Class<E> entityClass) {
		Query query = em.createQuery(QL, entityClass);
		if (init > 0 && maxResult > 0) {
			query.setFirstResult(maxResult * (init - 1));
		}
		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		if (!CollectionsUtiliy.isEmptyMap(params)) {
			for (Iterator<Entry<String, Object>> i = params.entrySet().iterator(); i.hasNext();) {
				Map.Entry<String, Object> e = i.next();
				query.setParameter(e.getKey(), e.getValue());
			}
		}
		return query;
	}

	@SuppressWarnings("rawtypes")
	private static <E> TypedQuery createNamedQuery(EntityManager em, String NamedQuery, int init, int maxResult,
			Map<String, Object> params, Map<String, Object> hintsParams, Class<E> entityClass) {
		TypedQuery<E> namedQuery = em.createNamedQuery(NamedQuery, entityClass);
		if (init > 0 && maxResult > 0) {
			namedQuery.setFirstResult(maxResult * (init - 1));
		}
		if (maxResult > 0) {
			namedQuery.setMaxResults(maxResult);
		}
		if (!CollectionsUtiliy.isEmptyMap(params)) {
			for (Iterator<Entry<String, Object>> i = params.entrySet().iterator(); i.hasNext();) {
				Map.Entry<String, Object> e = i.next();
				namedQuery.setParameter(e.getKey(), e.getValue());
			}
		}
		if (!CollectionsUtiliy.isEmptyMap(hintsParams)) {
			for (Iterator<Entry<String, Object>> i = hintsParams.entrySet().iterator(); i.hasNext();) {
				Map.Entry<String, Object> e = i.next();
				namedQuery.setHint(e.getKey(), e.getValue());
			}
		}
		return namedQuery;
	}

	@Override
	public <E> E findEntity(Object id, Class<E> entityClass) {
		return em.find(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> E findEntity(String QL, Class<E> entityClass) {
		return (E) createQuery(em, QL, 0, 0, null, entityClass).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> E findEntity(String QL, Map<String, Object> params, Class<E> entityClass) {
		return (E) createQuery(em, QL, 0, 0, params, entityClass).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(Class<E> entityClass) {
		CriteriaQuery<E> cq = (CriteriaQuery<E>) em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, Class<E> entityClass) {
		return createQuery(em, QL, 0, 0, null, entityClass).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, Map<String, Object> params, Class<E> entityClass) {
		return createQuery(em, QL, 0, 0, params, entityClass).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, int init, int maxResult, Class<E> entityClass) {
		return createQuery(em, QL, init, maxResult, null, entityClass).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		return createQuery(em, QL, init, maxResult, params, entityClass).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> int countEntity(Class<E> entityClass) {
		CriteriaQuery<E> cq = (CriteriaQuery<E>) em.getCriteriaBuilder().createQuery();
		Root<E> rt = cq.from(entityClass);
		cq.select((Selection<? extends E>) em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Override
	public <E> int countEntity(String QL, Class<E> entityClass) {
		return ((Number) createQuery(em, QL, 0, 0, null, entityClass).getSingleResult()).intValue();
	}

	@Override
	public <E> int countEntity(String QL, Map<String, Object> params, Class<E> entityClass) {
		return ((Number) createQuery(em, QL, 0, 0, params, entityClass).getSingleResult()).intValue();
	}

	@Override
	public <E> int countEntity(String QL, int init, int maxResult, Map<String, Object> params, Class<E> entityClass) {
		return ((Number) createQuery(em, QL, init, maxResult, params, entityClass).getSingleResult()).intValue();
	}

	@Override
	public <E> List<E> findListENQ(String namedQuery, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> findListENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> findListENQ(String namedQuery, int init, int maxResult, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> findListENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> int countENQ(String namedQuery, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <E> int countENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass) {
		return ((Number) createNamedQuery(em, namedQuery, 0, 0, params, null, entityClass).getSingleResult())
				.intValue();
	}

	@Override
	public <E> int countENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		// TODO Auto-generated method stub
		return 0;
	}

}
