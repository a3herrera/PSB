package app.interfaces.base;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Provides the description of a basic operation using JPA and interaction with
 * data base
 * </p>
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
public interface FacadeHandlerBase {

	/**
	 * <p>
	 * Create a new register in database in base a the entity send like
	 * parameter
	 * </p>
	 */
	<E> E createEntity(E entity) throws Exception;

	/**
	 * <p>
	 * Update the information of a register in database in base a the entity
	 * send like parameter
	 * </p>
	 */
	<E> E updateEntity(E entity) throws Exception;

	/**
	 * <p>
	 * Remove the register in database in base a the entity send like parameter
	 * </p>
	 */
	<E> boolean removeEntity(E entity) throws Exception;

	/**
	 * <p>
	 * Remove the register in database in base a the ID object of the class
	 * sending like parameter
	 * </p>
	 */
	<E> boolean removeEntity(Object id, Class<E> entityClass) throws Exception;

	/**
	 * @param id
	 *            identificador por el cual se estara realizando la busqueda de
	 *            la entidad
	 * @return
	 */
	<E> E findEntity(Object id, Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JQPL a utilizarse para realizar la busqueda de la entidad
	 * @return La primer entidad encontrada del JPQL enviado como parametro
	 * @throws Exception
	 */
	<E> E findEntity(String QL, Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizar para realizar la busqueda de la entidad
	 * @param params
	 *            Mapa de Parametros que solicita el QL
	 * @return La primera entidad encontrada que cumpla las condiciones del JQPL
	 *         enviado como parametro al método.
	 * @throws Exception
	 */
	<E> E findEntity(String QL, Map<String, Object> params, Class<E> entityClass);

	/**
	 * 
	 * @param em
	 *            EntityManager
	 * @return Retorna el listado de los registros que se encuentran en la BD
	 *         pertenecientes a la entidad
	 * @throws Exception
	 */
	<E> List<E> findListEntity(Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizarse para realizar la busqueda en Base de Datos
	 *            del listado de registros que pertenecen a la entidad que
	 *            extiende la Clase
	 * @param em
	 *            EntityManager
	 * @return El listado de registros en Base de Datos que cumplen con el JPQL
	 *         enviado como parametro
	 * @throws Exception
	 */
	<E> List<E> findListEntity(String QL, Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizarse para realizar la consulta en Base de Datos,
	 *            del listado de registros que pertenecen a la entidad que
	 *            extiende la clase
	 * @param params
	 *            Parametros que solicita el JPQL enviado como parametro al
	 *            método
	 * @param em
	 *            EntityManager
	 * @return el listado de registros en Base de Datos que cumplen con el JPQL
	 * @throws Exception
	 */
	<E> List<E> findListEntity(String QL, Map<String, Object> params, Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JQPL a utilizarse para realizar la consulta en Base de datos
	 *            del listado de registro
	 * @param init
	 *            número por el cual se va a estar retornando los valores de la
	 *            consulta
	 * @param maxResult
	 *            máxima cantidad de resultador a retonarna de la consulta
	 * @param em
	 *            EntityManager
	 * @return Retorna un listado de registros en Base de Datos que cumplen con
	 *         el JPQL, limitados por una cantidad máxima de resultados y un
	 *         resultado inicial.
	 * @throws Exception
	 */
	<E> List<E> findListEntity(String QL, int init, int maxResult, Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizarse para realizar la consulta en Base de Datos
	 *            del listado de registros que pertenecen a la entidad que
	 *            extiende la clase
	 * @param init
	 *            indica en que posicion se van a estar retornando los registros
	 *            que cumplen con el JPQL que enviado como parametro al método
	 * @param maxResult
	 *            indica la cantidad máxima de resultados a retornar en la
	 *            consulta realizada
	 * @param params
	 *            Parametros que solicitada el JPQL
	 * @param em
	 * @return
	 */
	<E> List<E> findListEntity(String QL, int init, int maxResult, Map<String, Object> params, Class<E> entityClass);

	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	<E> int countEntity(Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizarse para realizar la consulta en Base de Datos
	 *            del listado de registros que pertenecen a la entidad que
	 *            extiende la clase
	 * @param em
	 *            EntityManager
	 * @return
	 * @throws Exception
	 */
	<E> int countEntity(String QL, Class<E> entityClass);

	/**
	 * 
	 * @param QL
	 *            JPQL a utilizarse para realizar la consulta en Base de Datos
	 *            del listado de registros que pertenecen a la entidad que
	 *            extiende la clase
	 * 
	 * @param params
	 *            Parametros que solicitada el JPQL
	 * @param em
	 *            EntityManager
	 * @return
	 * @throws Exception
	 */
	<E> int countEntity(String QL, Map<String, Object> params, Class<E> entityClass);

	/**
	 * <p>
	 * Realiza un conteo de registros de la entidad
	 * </p>
	 * 
	 * @param QL
	 *            jpql a usar para la busqueda
	 * @param init
	 * @param maxResult
	 *            maximos de resultados a realizar en la busqueda
	 * @param params
	 *            parametros solicitados en el JPQL
	 * @param em
	 *            EntityManager
	 * @return conteo devuelto de la busqueda realizada de la entidad
	 */
	<E> int countEntity(String QL, int init, int maxResult, Map<String, Object> params, Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param entityClass
	 * @return
	 */
	<E> List<E> findListENQ(String namedQuery, Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param params
	 * @param entityClass
	 * @return
	 */
	<E> List<E> findListENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param init
	 * @param maxResult
	 * @param entityClass
	 * @return
	 */
	<E> List<E> findListENQ(String namedQuery, int init, int maxResult, Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param init
	 * @param maxResult
	 * @param params
	 * @param entityClass
	 * @return
	 */
	<E> List<E> findListENQ(String namedQuery, int init, int maxResult, Map<String, Object> params,
			Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param entityClass
	 * @return
	 */
	<E> int countENQ(String namedQuery, Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param params
	 * @param entityClass
	 * @return
	 */
	<E> int countENQ(String namedQuery, Map<String, Object> params, Class<E> entityClass);

	/**
	 * 
	 * @param namedQuery
	 * @param init
	 * @param maxResult
	 * @param params
	 * @param entityClass
	 * @return
	 */
	<E> int countENQ(String namedQuery, int init, int maxResult, Map<String, Object> params, Class<E> entityClass);

}
