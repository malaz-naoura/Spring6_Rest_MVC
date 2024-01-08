
    drop table if exists customer;

    drop table if exists juice;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table juice (
        juice_style tinyint check (juice_style between 0 and 2),
        price decimal(38,2),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        juice_name varchar(25) not null,
        id varchar(36) not null,
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;

    drop table if exists customer;

    drop table if exists juice;

    create table customer (
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table juice (
        juice_style tinyint check (juice_style between 0 and 2),
        price decimal(38,2),
        quantity_on_hand integer,
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        juice_name varchar(25) not null,
        id varchar(36) not null,
        upc varchar(255),
        primary key (id)
    ) engine=InnoDB;
