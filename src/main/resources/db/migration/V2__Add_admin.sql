insert into usr (id, username, password, active)
values (1,'admin','$2a$08$XgDCXGGfuoBeHlBIGeutKe9FLJ8mOPvFShiKt7kywzSGxtQp1DT5G',true);

insert into user_role (user_id, roles)
values (1,'USER'), (1,'ADMIN');