package br.com.alexandre.customer.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.alexandre.api.WishListResource;
import br.com.alexandre.customer.api.Customer;
import br.com.alexandre.customer.api.CustomerResource;

@Component("customerResource")
public class CustomerResourceImpl implements CustomerResource {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private WishListResource wishListRestClient;
	
	@Autowired
	public CustomerResourceImpl(@Qualifier("wishListRestClient") final WishListResource whishListRestClient) {
		this.wishListRestClient  = whishListRestClient;
	}
	
	public Customer find() {
		final List<String> whishList = getMyWishList();

		final Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("Alexandre");
		customer.setUsername(getUserName());
		customer.setWhishList(whishList);
		
		logger.debug("id: " + customer.getId() + ", name: " + customer.getName());
		
		return customer;
	}

	private List<String> getMyWishList() {
		try {
			return wishListRestClient.getMyWishList()
					.stream()
					.map( p -> p.getName())
					.collect(Collectors.toList());
		} catch (final Exception e) {
			return new ArrayList<>();
		}
	}
	
	private String getUserName() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final CasAuthenticationToken token = (CasAuthenticationToken) authentication;
		return token.getUserDetails().getUsername();
	}

}
