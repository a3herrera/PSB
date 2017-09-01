package app.core.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.client.utilities.CollectionsUtiliy;
import app.client.utilities.Constants;
import app.interfaces.base.FacadeHandlerLocal;

/**
 * Session Bean implementation class FacadeHandler
 */
@Local(FacadeHandlerLocal.class)
@Stateless(mappedName = "ejb/FacadeHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class FacadeHandler implements FacadeHandlerLocal {

	private EntityManager em;

	private static final Logger logger = LogManager.getLogger(FacadeHandler.class.getName());

	@PersistenceUnit(unitName = Constants.PERSISTENCE_NAME, name = Constants.PERSISTENCE_NAME)
	private EntityManagerFactory emf;

	/**
	 * <p>
	 * This class constructor is private for avoid class initialization
	 * </p>
	 */
	private FacadeHandler() {
		super();
	}

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizarse para realizar la consulta en Base de Datos del
	 *            listado de registros que pertenecen a la entidad que extiende la
	 *            clase
	 * @param init
	 *            indica en que posicion se van a estar retornando los registros que
	 *            cumplen con el JPQL que enviado como parametro al método
	 * @param maxResult
	 *            indica la cantidad máxima de resultados a retornar en la consulta
	 *            realizada
	 * @param params
	 *            Parametros que solicitada el JPQL
	 * @param em
	 *            EntityManager
	 * @return
	 */
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

	/**
	 * 
	 * @param QL
	 *            NamedQuery a utilizarse para realizar la consulta en Base de Datos
	 *            del listado de registros que pertenecen a la entidad que extiende
	 *            la clase
	 * @param init
	 *            indica en que posicion se van a estar retornando los registros que
	 *            cumplen con el JPQL que enviado como parametro al método
	 * @param maxResult
	 *            indica la cantidad máxima de resultados a retornar en la consulta
	 *            realizada
	 * @param params
	 *            Parametros que solicitada el JPQL
	 * @param em
	 *            EntityManager
	 * @return
	 */
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

	private EntityManager getEntityManager() {
		if (em == null) {
			em = emf.createEntityManager();
		}
		if (!em.isOpen()) {
			em = emf.createEntityManager();
		}
		return em;
	}

	private <E> E requestHandler(E entity, boolean isNew) throws Exception {
		if (entity == null) {
			return null;
		}

		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			if (isNew) {
				em.persist(entity);
			} else {
				em.merge(entity);
			}
			em.flush();
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error(ex);
			throw new RuntimeException("Fail Transaction");
		} finally {
			em.close();
			em = null;
		}
		return entity;
	}

	/**
	 * @see FacadeHandlerLocal#createEntity(Object)
	 */
	@Override
	public <E> E createEntity(E entity) throws Exception {
		return requestHandler(entity, true);
	}

	/**
	 * @see FacadeHandlerLocal#updateEntity(Object)
	 */
	@Override
	public <E> E updateEntity(E entity) throws Exception {
		return requestHandler(entity, false);
	}

	/**
	 * @see FacadeHandlerLocal#removeEntity(Object)
	 */
	@Override
	public <E> boolean removeEntity(E entity) throws Exception {
		if (entity == null) {
			return true;
		}
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
			em = null;
		}

		return true;
	}

	/**
	 * @see FacadeHandlerLocal#removeEntity(Object, Class)
	 */
	@Override
	public <E> boolean removeEntity(Object id, Class<E> entityClass) throws Exception {
		if (id == null || entityClass == null) {
			return true;
		}
		EntityManager em = getEntityManager();
		try {
			E entity = findEntity(id, entityClass);
			if (entity != null) {
				em.getTransaction().begin();
				em.remove(entity);
				em.getTransaction().commit();
			} else {
				return true;
			}
		} catch (Exception ex) {
			em.getTransaction().rollback();
		} finally {
			em.close();
			em = null;
		}

		return false;
	}

	/**
	 * @see FacadeHandlerLocal#findEntity(Object, Class)
	 */
	@Override
	public <E> E findEntity(Object id, Class<E> entityClass) {
		return em.find(entityClass, id);
	}

	/**
	 * @see FacadeHandlerLocal#findEntity(String, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> E findEntity(String QL, Class<E> entityClass) {
		return (E) createQuery(getEntityManager(), QL, 0, 0, null, entityClass).getSingleResult();
	}

	/**
	 * @see FacadeHandlerLocal#findEntity(String, Map, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> E findEntity(String QL, Map<String, Object> params, Class<E> entityClass) {
		return (E) createQuery(getEntityManager(), QL, 0, 0, params, entityClass).getSingleResult();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(Class<E> entityClass) {
		EntityManager em = getEntityManager();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, Class<E> entityClass) {
		return createQuery(getEntityManager(), QL, 0, 0, null, entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, Map, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, Map<String, Object> params, Class<E> entityClass) {
		return createQuery(getEntityManager(), QL, 0, 0, params, entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, int, int, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, int init, int maxResult, Class<E> entityClass) {
		return createQuery(getEntityManager(), QL, init, maxResult, null, entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, int, int, Map, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String QL, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		return createQuery(getEntityManager(), QL, init, maxResult, params, entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> int countEntity(Class<E> entityClass) {
		EntityManager em = getEntityManager();
		CriteriaQuery<E> cq = (CriteriaQuery<E>) em.getCriteriaBuilder().createQuery();
		Root<E> rt = cq.from(entityClass);
		cq.select((Selection<? extends E>) em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(String, Class)
	 */
	@Override
	public <E> int countEntity(String QL, Class<E> entityClass) {
		int result = 0;
		try {
			result = ((Number) createQuery(getEntityManager(), QL, 0, 0, null, entityClass).getSingleResult())
					.intValue();
		} catch (NoResultException ex) {
			return 0;
		}
		return result;
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(String, Map, Class)
	 */
	@Override
	public <E> int countEntity(String QL, Map<String, Object> params, Class<E> entityClass) {
		int result = 0;
		try {
			result = ((Number) createQuery(getEntityManager(), QL, 0, 0, params, entityClass).getSingleResult())
					.intValue();
		} catch (NoResultException ex) {
			return 0;
		}
		return result;
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(String, int, int, Map, Class)
	 */
	@Override
	public <E> int countEntity(String QL, int init, int maxResult, Map<String, Object> params, Class<E> entityClass) {
		int result = 0;
		try {
			result = ((Number) createQuery(getEntityManager(), QL, init, maxResult, params, entityClass)
					.getSingleResult()).intValue();

		} catch (NoResultException ex) {
			return 0;
		}
		return result;
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, Map, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, int, int, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, int init, int maxResult, Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, int, int, Map, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see FacadeHandlerLocal#countENQ(String, Class)
	 */
	@Override
	public <E> int countENQ(String namedQuery, Class<E> entityClass) {
		return ((Number) createNamedQuery(getEntityManager(), namedQuery, 0, 0, null, null, entityClass)
				.getSingleResult()).intValue();
	}

	/**
	 * @see FacadeHandlerLocal#countENQ(String, Map, Class)
	 */
	@Override
	public <E> int countENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass) {
		return ((Number) createNamedQuery(getEntityManager(), namedQuery, 0, 0, params, null, entityClass)
				.getSingleResult()).intValue();
	}

	/**
	 * @see FacadeHandlerLocal#countENQ(String, int, int, Map, Class)
	 */
	@Override
	public <E> int countENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		return ((Number) createNamedQuery(getEntityManager(), namedQuery, init, maxResult, params, null, entityClass)
				.getSingleResult()).intValue();
	}

}
