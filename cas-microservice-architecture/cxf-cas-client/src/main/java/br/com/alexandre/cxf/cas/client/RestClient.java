package br.com.alexandre.cxf.cas.client;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class RestClient<T> {
	private static final long REQUEST_TIMEOUT = 2000;
	private static final long CONNECT_TIMEOUT = 3000;

	public RestClient(final String url) {
		this.url = url;
	}

	private String url;
	private Class<T> parameterizedType;
	private T resource;
	private T proxy;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected T getServiceProxy() {
		if (this.proxy == null) {
			final T service = this.getResource();
			final Class[] interfaces = service.getClass().getInterfaces();
			this.proxy = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), interfaces, new DefaultInvocationHandler<T>(service));
		}
		return this.proxy;		
	}

	@SuppressWarnings("unchecked")
	private T getResource() {
		if (this.resource == null) {
			final JAXRSClientFactoryBean factory = new JAXRSClientFactoryBean();
			factory.setAddress(url);
			factory.setServiceClass(this.getParameterizedType());
			configureProvider(factory);
			configureInterceptors(factory);

			this.resource = (T) factory.create();

			final Client client = WebClient.client(resource);

			final ClientConfiguration config = WebClient.getConfig(client);

			final HTTPClientPolicy policy = config.getHttpConduit().getClient();
			policy.setConnectionTimeout(CONNECT_TIMEOUT);
			policy.setReceiveTimeout(REQUEST_TIMEOUT);

			final Map<String, Object> requestContext = config.getRequestContext();
			requestContext.put(org.apache.cxf.message.Message.MAINTAIN_SESSION, Boolean.TRUE);
		}
		return this.resource;
	}

	protected void configureProvider(final JAXRSClientFactoryBean factory) {
		factory.setProvider(new JacksonJaxbJsonProvider());
	}

	protected void configureInterceptors(final InterceptorProvider provider) {
		provider.getOutInterceptors().add(new ProxyTicketInterceptor());
	}

	@SuppressWarnings("unchecked")
	private Class<T> getParameterizedType() {
		if (this.parameterizedType == null) {
			this.parameterizedType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
		return this.parameterizedType;
	}
}
