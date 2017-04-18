package br.com.alexandre.cxf.cas.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * http://cxf.apache.org/docs/jax-rs-filters.html
 */
public class ProxyTicketInterceptor extends AbstractPhaseInterceptor<Message> {

	private static final String TICKET_PARAM_NAME = "ticket";
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ProxyTicketInterceptor() {
		super(Phase.PREPARE_SEND);
	}

	@Override
	public void handleMessage(final Message message) {
		final String endpointAddress = (String)message.get(Message.ENDPOINT_ADDRESS);
		String ticket;
		try {
			ticket = URLEncoder.encode(getTicketFor(endpointAddress), DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		final String newRequestUri = appendQueryParam(endpointAddress, TICKET_PARAM_NAME, ticket);
		logger.info("New Request URI: " + newRequestUri);
		
		message.put(Message.ENDPOINT_ADDRESS, newRequestUri);
	}

	private String appendQueryParam(String endpointAddress, final String ticketParamName, final String ticket) {
		return endpointAddress + ((endpointAddress.contains("?"))? "&": "?") + ticketParamName + "=" + ticket;
	}

	protected String getTicketFor(final String service) {
        final CasAuthenticationToken casAuthenticationToken = (CasAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        final String proxyTicket = casAuthenticationToken.getAssertion().getPrincipal().getProxyTicketFor(service);
        return proxyTicket;
	}

}