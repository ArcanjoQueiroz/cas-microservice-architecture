#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ${package}.api.Customer;
import ${package}.api.CustomerResource;

@Component("customerResource")
public class CustomerResourceImpl implements CustomerResource {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Customer findById(final Long id) {
		final Customer customer = new Customer();
		customer.setId(id);
		customer.setName("Alexandre");
		
		logger.debug("id: " + customer.getId() + ", name: " + customer.getName());
		
		return customer;
	}

}
