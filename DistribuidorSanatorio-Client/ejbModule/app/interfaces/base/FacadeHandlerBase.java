package app.interfaces.base;

import java.util.List;
import java.util.Map;

public interface FacadeHandlerBase {

	<E> E createEntity(E entity) throws Exception;

	<E> E updateEntity(E entity) throws Exception;

	<E> boolean removeEntity(E entity) throws Exception;

	<E> E findEntity(Object id, Class<E> entityClass);

	<E> E findEntity(String QL, Class<E> entityClass);

	<E> E findEntity(String QL, Map<String, Object> params, Class<E> entityClass);

	<E> List<E> findListEntity(Class<E> entityClass);

	<E> List<E> findListEntity(String QL, Class<E> entityClass);

	<E> List<E> findListEntity(String QL, Map<String, Object> params, Class<E> entityClass);

	<E> List<E> findListEntity(String QL, int init, int maxResult, Class<E> entityClass);

	<E> List<E> findListEntity(String QL, int init, int maxResult, Map<String, Object> params, Class<E> entityClass);

	<E> int countEntity(Class<E> entityClass);

	<E> int countEntity(String QL, Class<E> entityClass);

	<E> int countEntity(String QL, Map<String, Object> params, Class<E> entityClass);

	<E> int countEntity(String QL, int init, int maxResult, Map<String, Object> params, Class<E> entityClass);

	<E> List<E> findListENQ(String namedQuery, Class<E> entityClass);

	<E> List<E> findListENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass);

	<E> List<E> findListENQ(String namedQuery, int init, int maxResult, Class<E> entityClass);

	<E> List<E> findListENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass);

	<E> int countENQ(String namedQuery, Class<E> entityClass);

	<E> int countENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass);

	<E> int countENQ(String namedQuery, int init, int maxResult, Map<String, Object> params, Class<E> entityClass);

}
