package br.com.alexandre.cxf.cas.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;


public class RestClientTest {
	private static final int PORT = 8082;
	
	private static final String HOST = "localhost";
	
	private static final String TARGET_SERVICE = "http://" + HOST + ":" + PORT + "/rest";
		
	private static MockWebServer server;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		server = new MockWebServer();		
		server.enqueue(new MockResponse()
						.addHeader("Content-Type", "application/json")
						.setResponseCode(200)
						.setBody("{ \"id\": \"1\", \"name\": \"The Witcher 3: Wild Hunt\", \"year\": 2015 }"));
		
		server.enqueue(new MockResponse()
				.addHeader("Content-Type", "application/json")
				.setResponseCode(200)
				.setBody("{ \"id\": \"2\", \"name\": \"Street Fighter V\", \"year\": 2016 }"));
		
		server.enqueue(new MockResponse()
				.addHeader("Content-Type", "application/json")
				.setResponseCode(200)
				.setBody("{ \"id\": \"3\", \"name\": \"The King of Fighters XIV\", \"year\": 2016 }"));		

		server.enqueue(new MockResponse()
				.addHeader("Content-Type", "application/json")
				.setResponseCode(200)
				.setBody("{ \"id\": \"4\", \"name\": \"Final Fantasy XV\", \"year\": 2016 }"));			
		
		server.start(PORT);
	}

	@Test
	public void test() {
		final GameRestClient client = new GameRestClient(TARGET_SERVICE);

		final Game game1 = client.findById("1L");
		
		assertNotNull(game1);
		assertEquals("1", game1.getId());
		assertEquals("The Witcher 3: Wild Hunt", game1.getName());
		assertEquals(new Integer(2015), game1.getYear());

		final Game game2 = client.findById("2L");
		
		assertNotNull(game2);
		assertEquals("2", game2.getId());
		assertEquals("Street Fighter V", game2.getName());
		assertEquals(new Integer(2016), game2.getYear());
		
		final Game game3 = client.findById("3L");
		
		assertNotNull(game3);
		assertEquals("3", game3.getId());
		assertEquals("The King of Fighters XIV", game3.getName());
		assertEquals(new Integer(2016), game3.getYear());
		
		final Game game4 = client.findById("4L");
		
		assertNotNull(game4);
		assertEquals("4", game4.getId());
		assertEquals("Final Fantasy XV", game4.getName());
		assertEquals(new Integer(2016), game4.getYear());
	}

	@AfterClass
	public static void tearDownAfterClass() throws IOException {
		server.shutdown();
	}
}