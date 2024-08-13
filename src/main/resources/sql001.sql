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

create table projeto (
	id serial primary key,
	nome text not null,
	descricao text not null,
	valor numeric(14,4) null,
	data_limite timestamp(6) null,
	status text not null default 'A',
	sub_status text not null default 'F',
	criado_em timestamp(6) not null default current_timestamp,
	criado_por_id bigint not null references usuario(id),
	cliente_id bigint not null references cliente(id)
);

create table projeto_atualizacao (
	id serial primary key,
	titulo text not null,
	descricao text not null,
	status text not null,
	sub_status text not null,
	criado_em timestamp(6) not null default current_timestamp,
	token_view text not null,
	criado_por_id bigint not null references usuario(id),
	projeto_id bigint not null references projeto(id)
);

create table configuracao_email (
	id serial primary key,
	smtp_host text not null,
	smtp_port bigint not null,
	smtp_ssl boolean not null,
	smtp_tls boolean not null,
	smtp_auth boolean not null,
	smtp_user text null,
	smtp_password text null,
	enviar_de text not null,
	criado_em timestamp(6) not null default current_timestamp,
	criado_por_id bigint not null references usuario(id),
	ultimo_uso_sucesso timestamp(6) null
);

create table envio_email (
	id serial primary key,
	email_destino text not null,
	assunto text not null,
	corpo text not null,
	envio_solicitado_em timestamp(6) not null default current_timestamp,
	envio_processado_em timestamp(6) null default current_timestamp,
	resultado text null,
	mensagem_erro text null,
	smtp_host text null,
	smtp_port bigint null,
	smtp_ssl boolean null,
	smtp_tls boolean null,
	smtp_auth boolean null,
	enviado_de text null,
	tipo_email text not null,
	projeto_id bigint null,
	projeto_atualizacao_id bigint null,
	cliente_id bigint null,
	usuario_id bigint null,
	configuracao_email_id bigint null
);

create table configuracao_armazenamento_s3 (
	id serial primary key,
	s3_service_endpoint text not null,
	s3_region text not null,
	s3_access_key text not null,
	s3_secret_key text not null,
	s3_bucket_name text not null,
	prefixo_base text null,
	criado_em timestamp(6) not null default current_timestamp,
	criado_por_id bigint not null references usuario(id),
	ultimo_uso_sucesso timestamp(6) null
);

create table arquivo_s3 (
	id serial primary key,
	arquivo_nome text not null,
	arquivo_nome_upload text not null,
	arquivo_caminho_completo text not null,
	nome_bucket text not null,
	tamanho bigint not null,
	tipo text not null,
	url_completa text not null,
	criado_em timestamp(6) not null default current_timestamp,
	criado_por_id bigint not null references usuario(id),
	configuracao_s3_id bigint null
);

create table projeto_atualizacao_anexo (
	id serial primary key,
	projeto_atualizacao_id bigint not null references projeto_atualizacao(id),
	arquivo_s3_id bigint not null references arquivo_s3(id)
);