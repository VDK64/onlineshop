package net.thumtack.onlineshop.services.impl;

import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CartRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dao.ProductRepository;
import net.thumtack.onlineshop.dao.PurchaseRepository;
import net.thumtack.onlineshop.dto.Response.ResponseEmptyDto;
import net.thumtack.onlineshop.services.interfaces.DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebugServiceImpl implements DebugService {
    private ClientRepository clientRepository;
    private AdministratorRepository administratorRepository;
    private CartRepository cartRepository;
    private CookieDataRepository cookieDataRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public DebugServiceImpl(ClientRepository clientRepository, AdministratorRepository administratorRepository,
                            CartRepository cartRepository, CookieDataRepository cookieDataRepository,
                            CategoryRepository categoryRepository, ProductRepository productRepository,
                            PurchaseRepository purchaseRepository) {
        this.clientRepository = clientRepository;
        this.administratorRepository = administratorRepository;
        this.cartRepository = cartRepository;
        this.cookieDataRepository = cookieDataRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public String clearDB() {
        cartRepository.deleteAll();
        purchaseRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        clientRepository.deleteAll();
        administratorRepository.deleteAll();
        cookieDataRepository.deleteAll();
        return ResponseEmptyDto.voidResponse;
    }
}
