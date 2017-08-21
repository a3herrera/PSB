package app.schema.utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
public class Utilities {

	/**
	 * <p>
	 * This class constructor is private for avoid class initialization
	 * </p>
	 */
	private Utilities() {
		super();
	}

	public static Object clone(Object cloned, boolean wantId) throws Exception {
		return clone(cloned, wantId, false);
	}

	public static Object clone(Object cloned, boolean wantId, boolean clonedParent) throws Exception {
		Object result = cloned.getClass().newInstance();
		Class<?> clonedClass = cloned.getClass();
		List<Field> fieldList = new ArrayList<Field>(Arrays.asList(clonedClass.getDeclaredFields()));
		if (clonedParent) {
			Class<?> clonedSupperClass = clonedClass.getSuperclass();
			while (clonedSupperClass != null) {
				fieldList.addAll(Arrays.asList(clonedSupperClass.getDeclaredFields()));
				clonedSupperClass = clonedSupperClass.getSuperclass();
			}
		}
		boolean cont = false;
		Field[] fields = new Field[fieldList.size()];
		fields = fieldList.toArray(fields);
		String fieldName = "";
		for (int l = 0; l < fields.length; l++) {
			if (fields[l].getModifiers() != Modifier.PRIVATE)
				continue;
			if (wantId) {
				cont = false;
			} else {
				if (fields[l].getName().equalsIgnoreCase("id")) {
					cont = true;
				} else {
					cont = false;
				}
			}

			if (cont) {
				continue;
			} else {
				try {
					Method getMethod = null;
					Method setMethod = null;
					if (!fields[l].getType().equals(boolean.class)) {
						fieldName = fields[l].getName().substring(0, 1).toUpperCase()
								+ fields[l].getName().substring(1, fields[l].getName().length());
						getMethod = clonedClass.getMethod("get" + fieldName, new Class[] {});
						setMethod = clonedClass.getMethod("set" + fieldName, new Class[] { fields[l].getType() });
					} else {

						fieldName = fields[l].getName().substring(0, 1).toUpperCase()
								+ fields[l].getName().substring(1, fields[l].getName().length());
						getMethod = clonedClass.getMethod("is" + fieldName, new Class[] {});
						setMethod = clonedClass.getMethod("set" + fieldName, new Class[] { fields[l].getType() });

					}
					if (getMethod != null) {
						if (!fields[l].getType().equals(List.class)) {
							setMethod.invoke(result, new Object[] { getMethod.invoke(cloned, new Object[] {}) });
						} else {
							continue;

						}
					}
				} catch (Throwable e) {
					continue;
				}

			}

		}

		return result;
	}

}
