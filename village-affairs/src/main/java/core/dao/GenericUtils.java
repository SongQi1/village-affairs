package core.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtils {
	
	/**
	 * Get the first generic class type
	 * 
	 * @param clazz the class to introspect
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> clazz) {
		return getGenericClass(clazz, 0);
	}

	/**
	 * Get the generic class type by index.
	 * 
	 * @param clazz the class to introspect
	 * @param index index of generic arguments on class, start from 0
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> clazz, int index) {
		Type type  = clazz.getGenericSuperclass();
		if (!(type instanceof ParameterizedType)) {
			return Object.class;
		}
		
		Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
		if (index < 0 || index >= genericTypes.length)
			throw new IndexOutOfBoundsException("Index " + index + " out of array bounds :" + genericTypes.length);
		
		return (Class<?>) genericTypes[index];
	}
}
