package br.com.alexandre.customer.api;

import br.com.alexandre.cxf.cas.client.RestClient;

public class CustomerRestClient extends RestClient<CustomerResource> implements CustomerResource {

	public CustomerRestClient(final String url) {
		super(url);
	}

	@Override
	public Customer find() {
		return getServiceProxy().find();
	}

}
