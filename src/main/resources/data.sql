insert into client (id, first_Name, patronymic, last_Name, email, address, phone, login, password, deposit)
values (1, 'Николай', 'Петрович', 'Сидоров', 'sidor@mail.ru', 'Rostov',  '89515563965', 'Sidor228', 'Penzagorodok164', 0);

insert into client (id, first_Name, patronymic, last_Name, email, address, phone, login, password, deposit)
values (2, 'Андрей', 'Эдуардович', 'Спрыгин', 'prizhok@mail.ru', 'Kazan',  '+79110356195', 'MirrorAs4', 'parolgorod135', 0);

insert into client (id, first_Name, patronymic, last_Name, email, address, phone, login, password, deposit)
values (3, 'Василий', 'Васильевич', 'Васильев', 'vasiliy@mail.ru', 'Penza',  '+79656345165', 'fist541', 'Penzagorodok164', 0);

insert into client (id, first_Name, patronymic, last_Name, email, address, phone, login, password, deposit)
values (4, 'Николай', 'Адексеевич', 'Виталин', 'ispanec@gmail.ru', 'Saratov',  '89613568971', 'client45', 'Sarcity64', 700000);

insert into client (id, first_Name, patronymic, last_Name, email, address, phone, login, password, deposit)
values (5, 'Виталий', 'Алексеевич', 'Панкратов', 'RestTestclient@gmail.ru', 'Saratov',  '89613568971',
 'testClient111', 'Passwordasd153', 3000);

insert into administrator (id, first_name, patronymic, last_name, login, password, position)
values (1, 'Иван', 'Петрович', 'Алексеев-Шульгин', 'admin41', 'Passloginword123', 'topManager');

insert into cookie_data (token, login, status) values ('proverochniutoken64', 'admin41', 'admin');
insert into cookie_data (token, login, status) values ('testClient2365', 'client45', 'client');
insert into cookie_data (token, login, status) values ('TokenTestClient123', 'testClient111', 'client');

insert into category (id, name, parent_id) values (1, 'Good products', 0);
insert into category (id, name, parent_id) values (2, 'Bad products', 0);
insert into category (id, name, parent_id) values (3, 'Products', 0);
insert into category (id, name, parent_id) values (4, 'Sok', 3);
insert into category (id, name, parent_id) values (5, 'Fruits', 1);
insert into category (id, name, parent_id) values (6, 'Apricots', 3);

insert into product (id, count, name, price) values (1, 100, 'сок добрый', 300);
insert into product (id, count, name, price) values (2, 20, 'китайские ботинки', 2000);
insert into product (id, count, name, price) values (3, 50, 'Adidas shoes', 7000);
insert into product (id, count, name, price) values (4, 75, 'Tapki', 300);
insert into product (id, count, name, price) values (5, 75, 'Super Socks', 3000);

insert into product_category values (1, 4);
insert into product_category values (2, 2);
insert into product_category values (3,1);
insert into product_category values (1,3);
insert into product_category values (2,3);
insert into product_category values (3,3);

insert into cart values (1, 10, 4, 1);
insert into cart values (2, 15, 4, 2);
insert into cart values (3, 60, 4, 3);
insert into cart values (4, 50, 4, 4);
insert into cart values (5, 50, 5, 2);

insert into purchase values(1, 40, 12000, 5, 1);
insert into purchase values(2, 20, 140000, 5, 3);
insert into purchase values(3, 15, 4500, 3, 1);
insert into purchase values(4, 20, 140000, 3, 3);
insert into purchase values(5, 40, 80000, 2, 2);
insert into purchase values(6, 20, 6000, 1, 4);
insert into purchase values(7, 80, 240000, 3, 5);
insert into purchase values(8, 20, 140000, 4, 3);