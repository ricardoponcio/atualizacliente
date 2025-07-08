# Atualiza Cliente

This is an open-source platform for registering clients and projects, tracking their updates over time, and notifying clients by email.

The implementation currently provides only the MVP of the product, not covering all possible validations and features, but allowing forks for further development and self-hosted deployments.

Although there is an endpoint for user registration, the system does not visually support more than one user, but the database is prepared for this. The system is designed for a single context and does not support multiple companies/people managing their own clients/projects in isolation, unlike SaaS solutions.

## Solution Technology

- **Backend:** Written in Java 17 with Spring Boot, using PostgreSQL by default. No native queries are used, so switching to another relational database only requires migrating the initialization script.  
  See [`atualizacliente-api`](./atualizacliente-api/README.md) for backend details.

- **Frontend:** Built with React.js, without complex frameworks.  
  See [`atualizacliente-front`](./atualizacliente-front/README.md) for frontend details.

The recommended deployment is to point your DNS A record to an NGINX server with a public IP, which routes HTTPS requests (with SSL engine + Certbot) to backend/frontend servers over HTTP. Using a VPN is also recommended for logical resource separation and security, especially to keep the database off the public IP machine.

![Diagram](images/Diagrama.png)

## Available Features

### 1. User Setup

![User setup](images/usuario/setup_usuario.png)

Register the first user when starting the system with a new database.

### 2. Login

![Login](images/usuario/login.png)

Authenticate using the credentials configured in the setup step.

### 3. Home Screen

![Home](images/home.png)

View project metrics and the next ten upcoming projects.

### 4. Email Configuration

Required for client notifications.

*For security, configurations cannot be updated (to avoid exposing credentials). To update, remove the record and create a new one.*

#### 4.1 List Configurations

![Email config list](images/config_email/lista_config_email.png)

*Only one configuration is allowed to simplify selection when sending notifications.*

#### 4.2 Create New Configuration

![Create email config](images/config_email/nova_config_email.png)

#### 4.3 List Sent Emails

![Sent emails](images/config_email/lista_envios_email.png)

*Allows analysis of SMTP server errors.*

### 5. Storage Configuration

Required for saving attachments, uses S3-compatible storage.

*For security, configurations cannot be updated. To update, remove and create a new one.*

#### 5.1 List Configurations

![Storage config list](images/config_s3/lista_config_s3.png)

*Only one configuration is allowed to simplify document attachment.*

#### 5.2 Create New Configuration

![Create storage config](images/config_s3/nova_config_s3.png)

### 6. Clients

#### 6.1 List Clients

![Client list](images/clientes/lista_clientes.png)

#### 6.2 Create New Client

![Create client](images/clientes/novo_cliente.png)

#### 6.3 Edit Client

![Edit client](images/clientes/modifica_cliente.png)

#### 6.4 Validate Client

![Validate client](images/clientes/validar_cliente.png)

*When registered, the client receives an email with a link to this screen, where they must set a security password to view updates and prevent data leaks.*

#### 6.5 Remove Client

![Remove client](images/clientes/remove_cliente.png)

### 7. Projects

#### 7.1 List Projects

![Project list](images/projetos/lista_projetos.png)

#### 7.2 Create New Project

![Create project](images/projetos/novo_projeto.png)

#### 7.3 Edit Project

![Edit project](images/projetos/modifica_projeto.png)

*Editing a project does not notify the client; this is an administrative action to help organize.*

#### 7.4 Remove Project

![Remove project](images/projetos/remove_projeto.png)

#### 7.5 Project Details

![Project details](images/projetos/detalhe_projeto.png)

#### 7.6 Project Updates

##### 7.6.1 List Updates

Updates can be listed by viewing project details (see 7.5).

##### 7.6.2 Create New Update

![Create update](images/projetos/atualizacao/nova_atualizacao_projeto.png)

*The update starts with the current project status/substatus. Changing these values when creating an update also updates the project itself.*

*When saving an update (description and attachments), the client is notified irreversibly.*

##### 7.6.4 View Update Details

![Update details](images/projetos/atualizacao/detalhe_projeto_atualizacao.png)

##### 7.6.5 Public Update Consultation

![Public update consultation](images/projetos/atualizacao/atualizacao_publica_consulta.png)

*This screen is loaded from a link sent to the user. The client must enter their security password to view the update.*

![Public update result](images/projetos/atualizacao/atualizacao_publica_resultado.png)

---

## Submodules

- [`atualizacliente-api`](./atualizacliente-api/README.md): Backend API (Java/Spring Boot)
- [`atualizacliente-front`](./atualizacliente-front/README.md): Frontend (React.js)