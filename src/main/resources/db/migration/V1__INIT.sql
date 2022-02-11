create table files
(
	id varchar(255) not null
		constraint files_pkey
			primary key,
	content bytea,
	content_type varchar(255),
	create_date timestamp not null,
	last_modified_date timestamp not null,
	name varchar(255) not null,
	size bigint not null,
	uploader varchar(255),
	version integer not null
);
