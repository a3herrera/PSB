package app.core.base;

import java.util.Collections;
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

	private static final Logger log = LogManager.getLogger(FacadeHandler.class.getName());

	private EntityManager em;

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
	 * @param jpql
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
	 * @param entityManager
	 *            EntityManager
	 * @return
	 */
	private static <E> Query createQuery(EntityManager entityManager, String jpql, int init, int maxResult,
			Map<String, Object> params, Class<E> entityClass) {
		Query query = entityManager.createQuery(jpql, entityClass);
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
	private static <E> TypedQuery createNamedQuery(EntityManager em, String namedJpql, int init, int maxResult,
			Map<String, Object> params, Map<String, Object> hintsParams, Class<E> entityClass) {
		TypedQuery<E> namedQuery = em.createNamedQuery(namedJpql, entityClass);
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

	private EntityManager getEntityManager(boolean clearEntityManager) {
		if (em == null) {
			em = emf.createEntityManager();
		}
		if (!em.isOpen()) {
			em = emf.createEntityManager();
		}
		
		if(clearEntityManager) {
			em.clear();
		}

		return em;
	}

	private <E> E requestHandler(E entity, boolean isNew) throws RuntimeException {
		if (entity == null) {
			return null;
		}

		EntityManager entityManager = getEntityManager(false);
		try {
			entityManager.getTransaction().begin();
			if (isNew) {
				entityManager.persist(entity);
			} else {
				entityManager.merge(entity);
			}
			entityManager.flush();
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			throw new RuntimeException("Fail Transaction");
		} finally {
			entityManager.close();
			entityManager = null;
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
		EntityManager entityManager = getEntityManager(false);
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			log.error(ex);
			return false;
		} finally {
			entityManager.close();
			entityManager = null;
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
		EntityManager entityManager = getEntityManager(false);
		try {
			E entity = findEntity(id, entityClass);
			if (entity != null) {
				entityManager.getTransaction().begin();
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
			} else {
				return true;
			}
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
			entityManager = null;
		}

		return false;
	}

	/**
	 * @see FacadeHandlerLocal#findEntity(Object, Class)
	 */
	@Override
	public <E> E findEntity(Object id, Class<E> entityClass) {
		return getEntityManager(false).find(entityClass, id);
	}

	/**
	 * @see FacadeHandlerLocal#findEntity(String, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> E findEntity(String jpql, Class<E> entityClass) {
		return (E) createQuery(getEntityManager(false), jpql, 0, 0, Collections.emptyMap(), entityClass).getSingleResult();
	}

	/**
	 * @see FacadeHandlerLocal#findEntity(String, Map, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> E findEntity(String jpql, Map<String, Object> params, Class<E> entityClass) {
		return (E) createQuery(getEntityManager(false), jpql, 0, 0, params, entityClass).getSingleResult();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(Class<E> entityClass) {
		EntityManager entityManager = getEntityManager(true);
		CriteriaQuery<E> cq = (CriteriaQuery<E>) entityManager.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return entityManager.createQuery(cq).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String jpql, Class<E> entityClass) {
		return createQuery(getEntityManager(true), jpql, 0, 0, Collections.emptyMap(), entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, Map, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String jpql, Map<String, Object> params, Class<E> entityClass) {
		return createQuery(getEntityManager(true), jpql, 0, 0, params, entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, int, int, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String jqpl, int init, int maxResult, Class<E> entityClass) {
		return createQuery(getEntityManager(true), jqpl, init, maxResult, Collections.emptyMap(), entityClass)
				.getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#findListEntity(String, int, int, Map, Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> findListEntity(String jpql, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		return createQuery(getEntityManager(true), jpql, init, maxResult, params, entityClass).getResultList();
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <E> int countEntity(Class<E> entityClass) {
		EntityManager entityManager = getEntityManager(true);
		CriteriaQuery<E> cq = (CriteriaQuery<E>) entityManager.getCriteriaBuilder().createQuery();
		Root<E> rt = cq.from(entityClass);
		cq.select((Selection<? extends E>) entityManager.getCriteriaBuilder().count(rt));
		Query q = entityManager.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(String, Class)
	 */
	@Override
	public <E> int countEntity(String jqpl, Class<E> entityClass) {
		int result = 0;
		try {
			result = ((Number) createQuery(getEntityManager(true), jqpl, 0, 0, Collections.emptyMap(), entityClass)
					.getSingleResult()).intValue();
		} catch (NoResultException ex) {
			return 0;
		}
		return result;
	}

	/**
	 * @see FacadeHandlerLocal#countEntity(String, Map, Class)
	 */
	@Override
	public <E> int countEntity(String jqpl, Map<String, Object> params, Class<E> entityClass) {
		int result = 0;
		try {
			result = ((Number) createQuery(getEntityManager(true), jqpl, 0, 0, params, entityClass).getSingleResult())
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
	public <E> int countEntity(String jpql, int init, int maxResult, Map<String, Object> params, Class<E> entityClass) {
		int result = 0;
		try {
			result = ((Number) createQuery(getEntityManager(true), jpql, init, maxResult, params, entityClass)
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
		return Collections.emptyList();
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, Map, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass) {
		return Collections.emptyList();
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, int, int, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, int init, int maxResult, Class<E> entityClass) {
		return Collections.emptyList();
	}

	/**
	 * @see FacadeHandlerLocal#findListENQ(String, int, int, Map, Class)
	 */
	@Override
	public <E> List<E> findListENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		return Collections.emptyList();
	}

	/**
	 * @see FacadeHandlerLocal#countENQ(String, Class)
	 */
	@Override
	public <E> int countENQ(String namedQuery, Class<E> entityClass) {
		return ((Number) createNamedQuery(getEntityManager(true), namedQuery, 0, 0, Collections.emptyMap(),
				Collections.emptyMap(), entityClass).getSingleResult()).intValue();
	}

	/**
	 * @see FacadeHandlerLocal#countENQ(String, Map, Class)
	 */
	@Override
	public <E> int countENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass) {
		return ((Number) createNamedQuery(getEntityManager(true), namedQuery, 0, 0, params, Collections.emptyMap(),
				entityClass).getSingleResult()).intValue();
	}

	/**
	 * @see FacadeHandlerLocal#countENQ(String, int, int, Map, Class)
	 */
	@Override
	public <E> int countENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass) {
		return ((Number) createNamedQuery(getEntityManager(true), namedQuery, init, maxResult, params,
				Collections.emptyMap(), entityClass).getSingleResult()).intValue();
	}

}
