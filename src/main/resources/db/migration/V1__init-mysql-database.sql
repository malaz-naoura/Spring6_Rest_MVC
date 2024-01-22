create table category
(
    version      integer,
    created_date datetime(6),
    update_date  datetime(6),
    id           varchar(36) not null,
    description  varchar(255),
    primary key (id)
) engine = InnoDB;
create table category_products
(
    categories_id varchar(36) not null,
    products_id   varchar(36) not null
) engine = InnoDB;
create table customer
(
    version      integer,
    created_date datetime(6),
    update_date  datetime(6),
    id           varchar(36) not null,
    email        varchar(255),
    name         varchar(255),
    primary key (id)
) engine = InnoDB;
create table juice
(
    juice_style tinyint check (juice_style between 0 and 2),
    id          varchar(36) not null,
    upc         varchar(255),
    primary key (id)
) engine = InnoDB;
create table order_line
(
    order_quantity integer,
    version        integer,
    created_date   datetime(6),
    update_date    datetime(6),
    id             varchar(36) not null,
    order_id       varchar(36),
    primary key (id)
) engine = InnoDB;
create table order_shipment
(
    version         integer,
    created_date    datetime(6),
    update_date     datetime(6),
    id              varchar(36) not null,
    order_id        varchar(36),
    tracking_number varchar(255),
    primary key (id)
) engine = InnoDB;
create table order_table
(
    version           integer,
    created_date      datetime(6),
    update_date       datetime(6),
    customer_id       varchar(36),
    id                varchar(36) not null,
    order_shipment_id varchar(36),
    customer_ref      varchar(255),
    primary key (id)
) engine = InnoDB;
create table product
(
    price            decimal(38, 2),
    quantity_on_hand integer,
    version          integer,
    created_date     datetime(6),
    update_date      datetime(6),
    name             varchar(25) not null,
    id               varchar(36) not null,
    primary key (id)
) engine = InnoDB;
alter table order_shipment
    add constraint UK_42jb2uyhxaj6iokfkhoxgc9fy unique (order_id);
alter table order_table
    add constraint UK_6w742nbkvmm8qoxfej1gcjubv unique (order_shipment_id);
alter table category_products
    add constraint FKe9irm5a62pmolhvr468cip3v3 foreign key (products_id) references product (id);
alter table category_products
    add constraint FKt6hhwypmhqewuadwp2yin9i7e foreign key (categories_id) references category (id);
alter table juice
    add constraint FKdkbqwt3bhq1wiijd0acsfsgyv foreign key (id) references product (id);
alter table order_line
    add constraint FK4u57nuq82pyy50ecxsf8dtll2 foreign key (order_id) references order_table (id);
alter table order_shipment
    add constraint FKp3hgxnyflwvhmmu2jsfsnxrji foreign key (order_id) references order_table (id);
alter table order_table
    add constraint FKdit09e676nqbguvt1k1w8mlj2 foreign key (customer_id) references customer (id);
alter table order_table
    add constraint FKj9ukt6vdrnhb94u844md4uar0 foreign key (order_shipment_id) references order_shipment (id);
