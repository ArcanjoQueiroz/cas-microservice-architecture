#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import ${groupId}.cxf.cas.client.RestClient;

public class CustomerRestClient extends RestClient<CustomerResource> implements CustomerResource {

	public CustomerRestClient(final String url) {
		super(url);
	}

	@Override
	public Customer findById(final Long id) {
		return getServiceProxy().findById(id);
	}

}
