package net.thumtack.onlineshop;

import net.thumtack.onlineshop.dao.CartRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineshopApplicationTests {

	@Autowired
	private CookieDataRepository cookRepo;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CartRepository cartRepository;

	@Test
	public void contextLoads() throws Exception {
	}

}
