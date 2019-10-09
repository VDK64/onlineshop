package net.thumtack.onlineshop.exceptions;

import net.thumtack.onlineshop.config.FileConfig;

public enum ServerErrors {
    WRONG_FIRSTNAME("Wrong first name. Use only russian letters, spaces, and minus. Max length is "
    + FileConfig.maxNameLength),

    WRONG_PATRONYMIC("Wrong patronymic. Use only russian letters, spaces, and minus. Max length is "
            + FileConfig.maxNameLength),

    WRONG_LASTNAME("Wrong last name. Use only russian letters, spaces, and minus. Max length is "
    + FileConfig.maxNameLength),

    WRONG_LOGIN("Wrong login. Use only russian and latin letters and numbers. Max length is "
            + FileConfig.maxNameLength),

    WRONG_LOGIN_EXIST("Wrong login. This login is already exist!"),

    WRONG_lOGIN_ABSENT("Your are not registered yet. Please, register!"),

    WRONG_PASSWORD_LOG_IN("You write wrong password! Try again"),

    WRONG_ID("This id doesn't exist"),

    ALREADY_LOG_IN("You are already login"),

    ALREADY_LOG_OUT("You are already logiut!"),

    WRONG_EMAIL("Wrong email."),

    MULTICOOCKING("You are multiaccounter. Logout and try again"),

    WRONG_DEPOSIT("Wrond number of depo."),

    WRONG_WITHDRAW("Your deposit is already emty!"),

    WRONG_COMMAND("You enter wrong command. Please, try again. You can case from: " +
            "prodByCat;" +
            "etc..."),

    WRONG_OFFSET_LIMIT("Limit or offset can not be empty"),

    WRONG_ADDRESS("Wrong address! Address can not be empty and more then " + FileConfig.maxNameLength +
            " characters."),

    WRONG_PHONE("Wrong phone numbers. Please, write a mobile number only!"),

    WRONG_PASSWORD("Wrong password. Password can not be empty. Min length is "
            + FileConfig.minPasswordLength),

    INVALID_COOKIE("Your session has expired. Please login"),

    WRONG_PRICE("Wrong price. The price can't be less or equal zero"),

    WRONG_PRODUCT("This product doesn't exist"),

    WRONG_PURCHASES("Purchase list is empty or wrong id of purchase"),

    WRONG_ORDER("Wrong order! The order may can be only categories or product!"),

    WORNG_CATEGORY_EXIST("This category/subcategory is already exist!"),

    WRONG_CATEGORY_ABSENT("This category/subcategory is absent"),

    WRONG_CATEGORY_NAME_NULL("Category's name con not be null"),

    WRONG_UPDATE_TURN("Category can not turn into a subcategory and vice versa!"),

    WRONG_REQUEST("Request can not be empty!"),

    WRONG_PURCHASE_DEPOSIT("You haven't got enough money to buy this product!"),

    WRONG_COUNT("We haven't got enough product"),

    WRONG_PRODUCT_INFO("Invalid product info"),

    WRONG_CARTPRODUCT_DELETE("You dont' t add this product in your cart"),

    WRONG_CARTPRODUCT_EXIST("You are already add this product in your cart. Please, use 'updateProduct'" +
            "to change parameters."),

    WRONG_POSITION("Wrong position. Use only russian and latin letters. Max length is "
    + FileConfig.maxNameLength);

    private String message;

    ServerErrors(String message) { this.message = message; }

    public String getErrorMessage() { return message; }
}
