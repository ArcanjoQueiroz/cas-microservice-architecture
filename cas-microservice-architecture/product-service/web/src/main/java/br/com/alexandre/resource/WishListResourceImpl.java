package br.com.alexandre.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.alexandre.api.Product;
import br.com.alexandre.api.WishListResource;

@Component("wishListResource")
public class WishListResourceImpl implements WishListResource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<Product> getMyWishList() {
		final String username = getUserName();
		
		logger.info("User Name: " + username);
		
		final List<Product> whishList = new ArrayList<>();
		whishList.add(new Product(1L, "Resident Evil 4"));
		whishList.add(new Product(2L, "Street Fighter V"));
		whishList.add(new Product(3L, "The King of Fighters XIV"));
		
		return whishList;
	}

	private String getUserName() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final CasAuthenticationToken token = (CasAuthenticationToken) authentication;
		
		return token.getUserDetails().getUsername();
	}	

}

