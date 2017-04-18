package br.com.alexandre.cxf.cas.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultInvocationHandler<T> implements InvocationHandler {
	private T resource;

	public DefaultInvocationHandler(T element) {
		this.resource = element;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {		
		try {
			return method.invoke(resource, args);
		} catch (InvocationTargetException e) {
			Throwable t = e;
			while (t.getCause() != null)
				t = t.getCause();

			throw t;
		}
	}
}