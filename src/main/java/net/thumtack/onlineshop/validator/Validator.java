package net.thumtack.onlineshop.validator;

import net.thumtack.onlineshop.config.FileConfig;
import net.thumtack.onlineshop.dao.AdministratorRepository;
import net.thumtack.onlineshop.dao.CategoryRepository;
import net.thumtack.onlineshop.dao.ClientRepository;
import net.thumtack.onlineshop.dao.CookieDataRepository;
import net.thumtack.onlineshop.dto.Request.RequestCategoryDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassAdminDto;
import net.thumtack.onlineshop.dto.Request.RequestChangePassClientDto;
import net.thumtack.onlineshop.dto.Request.RequestProductBuyDto;
import net.thumtack.onlineshop.entities.Administrator;
import net.thumtack.onlineshop.entities.Category;
import net.thumtack.onlineshop.entities.Client;
import net.thumtack.onlineshop.entities.Product;
import net.thumtack.onlineshop.entities.abstractClass.User;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerErrors;
import net.thumtack.onlineshop.exceptions.ServerExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class Validator {

    private final int maxNameLength = FileConfig.maxNameLength;
    private final int minPasswordLength = FileConfig.minPasswordLength;
    private static final String name = "^(?!\\s*$)[-а-яА-Я ]*$";
    private static final String login = "^[a-zA-Zа-яА-Я0-9]+$";
    private AdministratorRepository adminRepo;
    private CookieDataRepository cookieRepo;
    private ClientRepository clientRepo;
    private List<RespError> errorList;
    private CategoryRepository categoryRepository;

    @Autowired
    public Validator(AdministratorRepository adminRepo, CookieDataRepository cookieRepo, ClientRepository clientRepo,
                     CategoryRepository categoryRepository) {
        this.adminRepo = adminRepo;
        this.cookieRepo = cookieRepo;
        this.clientRepo = clientRepo;
        this.categoryRepository = categoryRepository;
    }

    public List<RespError> regCheckAdmin(Administrator admin) {
        errorList = new ArrayList<>();
        if (!admin.getFirstName().matches(name) || admin.getFirstName().equalsIgnoreCase("")
                || admin.getFirstName() == null || admin.getFirstName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_FIRSTNAME, "firstName",
                    ServerErrors.WRONG_FIRSTNAME.getErrorMessage()));
        }

        if (admin.getPatronymic() != null && !admin.getPatronymic().equalsIgnoreCase("")) {
            if (!admin.getPatronymic().matches(name) || admin.getPatronymic().length() > maxNameLength) {
                errorList.add(new RespError(ServerErrors.WRONG_PATRONYMIC, "patronymic",
                        ServerErrors.WRONG_PATRONYMIC.getErrorMessage()));
            }
        }

        if (!admin.getLastName().matches(name) || admin.getLastName().equalsIgnoreCase("")
                || admin.getLastName() == null || admin.getLastName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_LASTNAME, "lasName",
                    ServerErrors.WRONG_LASTNAME.getErrorMessage()));
        }

        if (!admin.getLogin().matches(login) || admin.getLogin().equalsIgnoreCase("")
                || admin.getLogin() == null || admin.getLogin().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_LOGIN, "login",
                    ServerErrors.WRONG_LOGIN.getErrorMessage()));
        }

        if (admin.getPosition().length() > maxNameLength || admin.getPosition().equalsIgnoreCase("")
                || admin.getPosition() == null) {
            errorList.add(new RespError(ServerErrors.WRONG_POSITION, "position",
                    ServerErrors.WRONG_POSITION.getErrorMessage()));
        }

        if (admin.getPassword().equalsIgnoreCase("") || admin.getPassword() == null
                || admin.getPassword().length() > maxNameLength || admin.getPassword().length() < minPasswordLength) {
            errorList.add(new RespError(ServerErrors.WRONG_PASSWORD, "password",
                    ServerErrors.WRONG_PASSWORD.getErrorMessage()));
        }
        return errorList;
    }

    public void checkPassword(String password) throws ServerExceptions {
        if (password == null || password.equalsIgnoreCase("") || password.length() > maxNameLength ||
                password.length() < minPasswordLength) {
           throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_PASSWORD, "password",
                   ServerErrors.WRONG_PASSWORD.getErrorMessage())));
        }
    }

    public List<RespError> checkToUpdatePasswordByAdmin(RequestChangePassAdminDto request){
        errorList = new ArrayList<>();
        if (!request.getFirstName().matches(name) || request.getFirstName().equalsIgnoreCase("")
                || request.getFirstName() == null || request.getFirstName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_FIRSTNAME, "firstName",
                    ServerErrors.WRONG_FIRSTNAME.getErrorMessage()));
        }

        if (request.getPatronymic() != null && !request.getPatronymic().equalsIgnoreCase("")) {
            if (!request.getPatronymic().matches(name) || request.getPatronymic().length() > maxNameLength) {
                errorList.add(new RespError(ServerErrors.WRONG_PATRONYMIC, "patronymic",
                        ServerErrors.WRONG_PATRONYMIC.getErrorMessage()));
            }
        }

        if (!request.getLastName().matches(name) || request.getLastName().equalsIgnoreCase("")
                || request.getLastName() == null || request.getLastName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_LASTNAME, "lasName",
                    ServerErrors.WRONG_LASTNAME.getErrorMessage()));
        }

        if (request.getPosition().length() > maxNameLength || request.getPosition().equalsIgnoreCase("")
                || request.getPosition() == null) {
            errorList.add(new RespError(ServerErrors.WRONG_POSITION, "position",
                    ServerErrors.WRONG_POSITION.getErrorMessage()));
        }

        if (request.getNewPassword().equalsIgnoreCase("") || request.getNewPassword() == null
                || request.getNewPassword().length() > maxNameLength || request.getNewPassword().length() < minPasswordLength) {
            errorList.add(new RespError(ServerErrors.WRONG_PASSWORD, "password",
                    ServerErrors.WRONG_PASSWORD.getErrorMessage()));
        }
        return errorList;

    }

    public List<RespError> regCheckClient(Client client) {
        errorList = new ArrayList<>();
        if (!client.getFirstName().matches(name) || client.getFirstName().equalsIgnoreCase("")
                || client.getFirstName() == null || client.getFirstName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_FIRSTNAME, "firstName",
                    ServerErrors.WRONG_FIRSTNAME.getErrorMessage()));
        }

        if (client.getPatronymic() != null && !client.getPatronymic().equalsIgnoreCase("")) {
            if (!client.getPatronymic().matches(name) || client.getPatronymic().length() > maxNameLength) {
                errorList.add(new RespError(ServerErrors.WRONG_PATRONYMIC, "patronymic",
                        ServerErrors.WRONG_PATRONYMIC.getErrorMessage()));
            }
        }

        if (!client.getLastName().matches(name) || client.getLastName().equalsIgnoreCase("")
                || client.getLastName() == null || client.getLastName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_LASTNAME, "lasName",
                    ServerErrors.WRONG_LASTNAME.getErrorMessage()));
        }

        if (!client.getLogin().matches(login) || client.getLogin().equalsIgnoreCase("")
                || client.getLogin() == null || client.getLogin().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_LOGIN, "login",
                    ServerErrors.WRONG_LOGIN.getErrorMessage()));
        }

        if (client.getAddress().length() > maxNameLength || client.getAddress().equalsIgnoreCase("")
                || client.getAddress() == null) {
            errorList.add(new RespError(ServerErrors.WRONG_ADDRESS, "address",
                    ServerErrors.WRONG_ADDRESS.getErrorMessage()));
        }

        if (client.getPassword().equalsIgnoreCase("") || client.getPassword() == null
                || client.getPassword().length() > maxNameLength || client.getPassword().length() < minPasswordLength) {
            errorList.add(new RespError(ServerErrors.WRONG_PASSWORD, "password",
                    ServerErrors.WRONG_PASSWORD.getErrorMessage()));
        }

        client.setPhone(client.getPhone().replace("-", ""));
        client.setPhone(client.getPhone().replace("(", ""));
        client.setPhone(client.getPhone().replace(")", ""));

        String phone = "^(\\+7|8)\\d{10}$";
        if (!client.getPhone().matches(phone) || client.getPhone().equalsIgnoreCase("")
        || client.getPhone() == null || client.getPhone().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_PHONE, "phone",
                    ServerErrors.WRONG_PHONE.getErrorMessage()));
        }

        String email = "^(.+)@(.+)$";
        if (!client.getEmail().matches(email) || client.getEmail().equalsIgnoreCase("")
                || client.getEmail() == null || client.getEmail().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_EMAIL, "email",
                    ServerErrors.WRONG_EMAIL.getErrorMessage()));
        }
        return errorList;
    }

    public List<RespError> checkClientToChangePass(RequestChangePassClientDto request){
        errorList = new ArrayList<>();
        if (!request.getFirstName().matches(name) || request.getFirstName().equalsIgnoreCase("")
                || request.getFirstName() == null || request.getFirstName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_FIRSTNAME, "firstName",
                    ServerErrors.WRONG_FIRSTNAME.getErrorMessage()));
        }

        if (request.getPatronymic() != null && !request.getPatronymic().equalsIgnoreCase("")) {
            if (!request.getPatronymic().matches(name) || request.getPatronymic().length() > maxNameLength) {
                errorList.add(new RespError(ServerErrors.WRONG_PATRONYMIC, "patronymic",
                        ServerErrors.WRONG_PATRONYMIC.getErrorMessage()));
            }
        }

        if (!request.getLastName().matches(name) || request.getLastName().equalsIgnoreCase("")
                || request.getLastName() == null || request.getLastName().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_LASTNAME, "lasName",
                    ServerErrors.WRONG_LASTNAME.getErrorMessage()));
        }

        if (request.getAddress().length() > maxNameLength || request.getAddress().equalsIgnoreCase("")
                || request.getAddress() == null) {
            errorList.add(new RespError(ServerErrors.WRONG_ADDRESS, "address",
                    ServerErrors.WRONG_ADDRESS.getErrorMessage()));
        }

        if (request.getNewPassword().equalsIgnoreCase("") || request.getNewPassword() == null
                || request.getNewPassword().length() > maxNameLength || request.getNewPassword().length() < minPasswordLength) {
            errorList.add(new RespError(ServerErrors.WRONG_PASSWORD, "password",
                    ServerErrors.WRONG_PASSWORD.getErrorMessage()));
        }

        request.setPhone(request.getPhone().replace("-", ""));
        request.setPhone(request.getPhone().replace("(", ""));
        request.setPhone(request.getPhone().replace(")", ""));

        String phone = "^(\\+7|8)\\d{10}$";
        if (!request.getPhone().matches(phone) || request.getPhone().equalsIgnoreCase("")
                || request.getPhone() == null || request.getPhone().length() > maxNameLength) {
            errorList.add(new RespError(ServerErrors.WRONG_PHONE, "phone",
                    ServerErrors.WRONG_PHONE.getErrorMessage()));
        }
        return errorList;
    }

    public List<RespError> checkAdminLoginRepeat(String login, List<RespError> errorList) {
        if (adminRepo.findByLogin(login).isPresent()) {
            errorList.add(new RespError(ServerErrors.WRONG_LOGIN_EXIST, "login",
                    ServerErrors.WRONG_LOGIN_EXIST.getErrorMessage()));
        }
        return errorList;
    }

    public List<RespError> checkClientLoginRepeat(String login, List<RespError> errorList) {
        if (clientRepo.findByLogin(login).isPresent()) {
            errorList.add(new RespError(ServerErrors.WRONG_LOGIN_EXIST, "login",
                    ServerErrors.WRONG_LOGIN_EXIST.getErrorMessage()));
        }
        return errorList;
    }


    public Optional<? extends User> validLogin(String login) throws ServerExceptions {
        Optional<Administrator> optAdmin;
        Optional<Client> optClient;
        optAdmin = adminRepo.findByLogin(login);
        if (optAdmin.isPresent()) {
            return optAdmin;
        }
        optClient = clientRepo.findByLogin(login);
        if (optClient.isPresent()) {
            return optClient;
        }
        else {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_lOGIN_ABSENT, "login",
                ServerErrors.WRONG_lOGIN_ABSENT.getErrorMessage())));
        }
    }

    private void checkCookieAdmin(Cookie[] cookies) throws ServerExceptions {
        int i = 0;
        boolean boo = false;
        if (cookies == null) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        } else {
            for (Cookie cookie : cookies) {
                if (cookieRepo.findById(cookie.getValue()).isPresent() && cookie.getName().equalsIgnoreCase("admin")) {
                    boo = true;
                    ++i;
                }
            }
            if (i > 1) {
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.MULTICOOCKING, "cookie",
                        ServerErrors.MULTICOOCKING.getErrorMessage())));
            }
            if (!boo) {
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                        ServerErrors.INVALID_COOKIE.getErrorMessage())));
            }
        }
    }

    public void checkCookieClient(Cookie[] cookies) throws ServerExceptions {
        int i = 0;
        boolean boo = false;
        if (cookies == null) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        } else {
            for (Cookie cookie : cookies) {
                if (cookieRepo.findById(cookie.getValue()).isPresent() && cookie.getName().equalsIgnoreCase("client")) {
                    boo = true;
                    ++i;
                }
            }
            if (i > 1) {
               throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.MULTICOOCKING, "cookie",
                       ServerErrors.MULTICOOCKING.getErrorMessage())));
            }
            if (!boo) {
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                        ServerErrors.INVALID_COOKIE.getErrorMessage())));
            }
        }
    }

    public void isCookieNullAdmin(HttpServletRequest request) throws ServerExceptions {
        if (request.getCookies() == null) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        checkCookieAdmin(request.getCookies());
    }

    public Cookie checkCookie(Cookie[] cookies) throws ServerExceptions {
        int i = 0;
        Cookie finalCookie = null;
        if (cookies == null) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        boolean boo = false;
        for (Cookie cookie : cookies) {
            if (cookieRepo.findById(cookie.getValue()).isPresent() && cookie.getName().equalsIgnoreCase("admin")
                    || cookie.getName().equalsIgnoreCase("client")) {
                boo = true;
                ++i;
            }
        }
        if (!boo) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        }
        if (i > 1) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.MULTICOOCKING, "cookie",
                    ServerErrors.MULTICOOCKING.getErrorMessage())));
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("admin")
                    || cookie.getName().equalsIgnoreCase("client")) {
                finalCookie = cookie;
            }
        }
        return finalCookie;
    }

    public void isCookieNullCLient(HttpServletRequest request) throws ServerExceptions {
        if (request.getCookies() == null)
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.INVALID_COOKIE, "cookie",
                    ServerErrors.INVALID_COOKIE.getErrorMessage())));
        checkCookieClient(request.getCookies());
    }

    public void nullCookieLoginAdmin(HttpServletRequest request) throws ServerExceptions {
        try {
            isCookieNullAdmin(request);
        } catch (ServerExceptions ignored) { return; }
        throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.ALREADY_LOG_IN, "cookie",
                ServerErrors.ALREADY_LOG_IN.getErrorMessage())));
    }

    public void nullCookieLoginClient(HttpServletRequest request) throws ServerExceptions {
        try {
            isCookieNullCLient(request);
        } catch (ServerExceptions ignored) { return; }
        throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.ALREADY_LOG_IN, "cookie",
                ServerErrors.ALREADY_LOG_IN.getErrorMessage())));
    }

    public void validateCategories(Category category, RequestCategoryDto reqCat) throws ServerExceptions {
        if (reqCat.getName() == null && reqCat.getParentId() == null) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_REQUEST, "name, parentId",
                    ServerErrors.WRONG_REQUEST.getErrorMessage())));
        }
        if (reqCat.getName() != null && reqCat.getName().equalsIgnoreCase("") && category.getParentId().equals(reqCat.getParentId())) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_REQUEST, "name, parentId",
                    ServerErrors.WRONG_REQUEST.getErrorMessage())));
        }
        if (category.getParentId() == null || category.getParentId() == 0) {
            if (reqCat.getParentId() != null) {
                if (reqCat.getParentId() != 0) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_UPDATE_TURN, "parentId",
                            ServerErrors.WRONG_UPDATE_TURN.getErrorMessage())));
                }
            }
        }
        if (category.getId().equals(reqCat.getParentId())) {
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_UPDATE_TURN, "parentId",
                    ServerErrors.WRONG_UPDATE_TURN.getErrorMessage())));
        }

    }

    public Optional<Category> getParentCat(RequestCategoryDto requestCategoryDto, Category category) throws ServerExceptions {
        Optional<Category> parentCat = Optional.empty();
        if (requestCategoryDto.getParentId() != null) {
            if (requestCategoryDto.getParentId() == 0 && category.getParentId() != 0) {
                throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_UPDATE_TURN, "parentId",
                        ServerErrors.WRONG_UPDATE_TURN.getErrorMessage())));
            }
            if (requestCategoryDto.getParentId() != 0) {
                category.setParentId(requestCategoryDto.getParentId());
                parentCat = categoryRepository.findById(requestCategoryDto.getParentId());
                if (!parentCat.isPresent()) {
                    throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_CATEGORY_ABSENT,
                            "parentId", ServerErrors.WRONG_CATEGORY_ABSENT.getErrorMessage())));
                }
            }
        } else {
            if (category.getParentId() != null) {
                if (category.getParentId() != 0)
                    parentCat = categoryRepository.findById(category.getParentId());
            }
        }
        if (requestCategoryDto.getName() != null) {
            if (!requestCategoryDto.getName().equalsIgnoreCase(""))
                category.setName(requestCategoryDto.getName());
        }
        return parentCat;
    }

    public List<RespError> checkPrice(Integer price, List<RespError> errorList) {
        if (price <= 0) {
            errorList.add(new RespError(ServerErrors.WRONG_PRICE, "price", ServerErrors.WRONG_PRICE.getErrorMessage()));
        }
        return errorList;
    }

    public List<RespError> checkPriceUpdate(Integer price, List<RespError> errorList) {
        if (price != null)
            if (price <= 0) {
                errorList.add(new RespError(ServerErrors.WRONG_PRICE, "price", ServerErrors.WRONG_PRICE.getErrorMessage()));
            }
        return errorList;
    }

    public void negativeDepo(Integer depo) throws ServerExceptions {
        if (depo <= 0)
            throw new ServerExceptions(Collections.singletonList(new RespError(ServerErrors.WRONG_DEPOSIT, "deposit",
                    ServerErrors.WRONG_DEPOSIT.getErrorMessage())));
    }

    public List<RespError> validPurchase(Client client, Product product, RequestProductBuyDto request,
                              List<RespError> errorList) throws ServerExceptions {
        validAddCart(product, request, errorList);
        if (request.getCount() > product.getCount()) {
            errorList.add(new RespError(ServerErrors.WRONG_COUNT, "count", ServerErrors.WRONG_COUNT.getErrorMessage()));
        }
        if (client.getDeposit() < request.getCount() * request.getPrice())
            errorList.add(new RespError(ServerErrors.WRONG_PURCHASE_DEPOSIT,
                    "deposit", ServerErrors.WRONG_PURCHASE_DEPOSIT.getErrorMessage()));
        return errorList;
    }

    public List<RespError> validAddCart(Product product, RequestProductBuyDto request,
                                        List<RespError> errorList) throws ServerExceptions {
        if (request.getCount() == null) {
            request.setCount(1);
        }
        if (product == null) {
            throw new ServerExceptions(errorList);
        }
        if (!request.getName().equalsIgnoreCase(product.getName()) || !request.getPrice().equals(product.getPrice())) {
            errorList.add(new RespError(ServerErrors.WRONG_PRODUCT_INFO, "price or name",
                    ServerErrors.WRONG_PRODUCT_INFO.getErrorMessage()));
        }
        return errorList;
    }
}

