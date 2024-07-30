--drop table usuario;
create table usuario (
	id serial primary key,
	nome text not null,
	email text not null,
	senha text not null,
	validado boolean not null default false,
	ativo boolean not null default true,
	criado_em timestamp(6) not null default CURRENT_TIMESTAMP,
	validado_em timestamp(6) null default CURRENT_TIMESTAMP,
	criado_por_id bigint null references usuario(id)
);
insert into usuario(nome, email, senha, validado, criado_em, validado_em)
values('Ricardo', 'ricardo.poncio@outlook.com.br', '123', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--drop table cliente;
create table cliente (
	id serial primary key,
	razao_social text not null,
	nome_fantasia text null,
	cnpj varchar(14) not null,
	email text not null,
	validado boolean not null default false,
	senha_visualizacao text null,
	token_validacao text not null,
	ativo boolean not null default true,
	criado_em timestamp(6) not null default CURRENT_TIMESTAMP,
	validado_em timestamp(6) null,
	criado_por_id bigint null references usuario(id)
);
insert into cliente(razao_social, cnpj, email, validado, criado_por_id)
values('Teste Razao', '00000000000101', 'teste@teste.com', true, 1);

--drop table projeto;
-- Status: A Aberto, C Concluido
-- Estado: F Na Fila, B Bloqueado, A Em Andamento, R Em Revisão, P Aguardando Pagamento, G Finalizado
create table projeto (
	id serial primary key,
	nome text not null,
	descricao text not null,
	valor numeric(14,4) null,
	data_limite timestamp(6) null,
	status varchar(1) not null default 'A',
	sub_status varchar(1) not null default 'F',
	criado_em timestamp(6) not null default current_timestamp,
	criado_por_id bigint not null references usuario(id),
	cliente_id bigint not null references cliente(id)
);
insert into projeto(nome, descricao, valor, data_limite, criado_por_id, cliente_id)
values('Projeto 1', 'Projeto Desc', 100, '2024-11-30 23:59:59', 1, 1);

--drop table projeto_atualizacao_email;
--drop table projeto_atualizacao;
create table projeto_atualizacao (
	id serial primary key,
	titulo text not null,
	descricao text not null,
	status varchar(1) not null,
	sub_status varchar(1) not null,
	criado_em timestamp(6) not null default current_timestamp,
	token_view text not null,
	criado_por_id bigint not null references usuario(id),
	projeto_id bigint not null references projeto(id)
);

-- Resultado: S Enviado com Sucesso, F Falha no envio
--drop table projeto_atualizacao_email;
create table projeto_atualizacao_email (
	id serial primary key,
	email_destino text not null,
	assunto text not null,
	corpo text not null,
	envio_solicitado_em timestamp(6) not null default current_timestamp,
	envio_processado_em timestamp(6) null default current_timestamp,
	resultado varchar(1) null,
	mensagem_erro text null,
	projeto_atualizacao_id bigint not null references projeto_atualizacao(id)
);
