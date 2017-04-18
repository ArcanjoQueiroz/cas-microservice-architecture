package br.com.alexandre.cxf.cas.client;

import java.util.UUID;

import br.com.alexandre.cxf.cas.client.ProxyTicketInterceptor;

public class DummyProxyTicketInterceptor extends ProxyTicketInterceptor {

	@Override
	protected String getTicketFor(String service) {
		return UUID.randomUUID().toString();
	}
	
}
