package br.com.alexandre.api;

import java.util.List;

import br.com.alexandre.cxf.cas.client.RestClient;

public class WishListRestClient extends RestClient<WishListResource> implements WishListResource {

	public WishListRestClient(final String url) {
		super(url);
	}

	@Override
	public List<Product> getMyWishList() {
		return getServiceProxy().getMyWishList();
	}


}
