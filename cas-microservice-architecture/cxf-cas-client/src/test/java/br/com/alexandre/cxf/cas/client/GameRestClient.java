package br.com.alexandre.cxf.cas.client;

import java.util.List;

import org.apache.cxf.interceptor.InterceptorProvider;

import br.com.alexandre.cxf.cas.client.RestClient;

public class GameRestClient extends RestClient<GameResource> implements GameResource {

	public GameRestClient(final String url) {
		super(url);
	}
	
	@Override
	protected void configureInterceptors(InterceptorProvider provider) {
		provider.getOutInterceptors().add(new DummyProxyTicketInterceptor());
	}

	@Override
	public void create(Game game) {
		getServiceProxy().create(game);		
	}

	@Override
	public void delete(String id) {
		getServiceProxy().delete(id);		
	}

	@Override
	public List<Game> findAll() {
		return getServiceProxy().findAll();
	}

	@Override
	public Game findById(String id) {
		return getServiceProxy().findById(id);
	}

	@Override
	public void update(Game game) {
		getServiceProxy().update(game);		
	}

}
