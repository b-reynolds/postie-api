create table file_types
(
	id serial
		constraint file_types_pk
			primary key,
	name text not null,
	created_at timestamptz default now() not null
);

create unique index file_types_name_uindex
	on file_types (name);

insert into file_types (id, name, created_at) values (default, 'text', default);

create table files
(
    id serial
        constraint files_pk
            primary key,
    name text not null,
    file_type_id int not null
        constraint files_file_types_id_fk
            references file_types,
    contents text not null,
    created_at timestamptz default now() not null,
    expires_at timestamptz
);