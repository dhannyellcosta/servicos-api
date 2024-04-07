create schema geral;

create table if not exists geral.usuario(
	id bigserial not null primary key,
	username varchar(100) not null,
	password varchar(255) not null,
	dt_cadastro timestamp not null,
	dt_alteracao timestamp
);

create table if not exists geral.cliente(
	id bigserial not null primary key,
	nome varchar(100) not null,
	cpf varchar(11) not null,
	dt_cadastro timestamp not null,
	dt_alteracao timestamp
);

create table if not exists geral.servico_prestado(
	id bigserial not null primary key,
	descricao varchar(150) not null,
	valor decimal not null,
	data timestamp not null,
	dt_cadastro timestamp not null,
    dt_alteracao timestamp
	id_cliente bigint references geral.cliente(id)
);


