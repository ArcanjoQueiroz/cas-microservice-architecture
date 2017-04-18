# CAS Microservice Architecture

Exemplo de arquitetura de microservices utilizando CAS Server, Spring Framework, Spring Security, CAS Client, CAS Proxy Authentication, Nginx, HTTPS/SSL e artefatos entregues como Docker Containers.

### Estrutura de diretórios

##### Infraestrutura

**ssl**: Contém script para a criação e configuração automática de certificado auto-assinado (Self-signed Certificate).
**nginx**: Docker Container referente ao Nginx (HTTP(s) Server/Reverse Proxy) do exemplo.

##### Microserviços

**product-service**: Microservice (CAS Service) referente a entidade do domínio Produto. Executa em um Docker Container com Jetty.
**customer-service**: Microservice (CAS Service) referente a entidade do domínio Customer. Executa em um Docker Container com Jetty.
**cas-server**: CAS Server do projeto. Executa em um Docker Container com Jetty.

##### Bibliotecas

**cxf-cas-client**: Biblioteca utilizada como HTTP(s) Client do projeto permitindo Marshalling e Unmarshalling do conteúdo HTTP(s) e comunicação Server-side entre CAS Services utilizando o conceito de Proxy Authentication.

##### Utilitários

**archetype**: Contém um Maven Archetype de um projeto utilizando Spring Framework, Spring Security, CAS Client e as configurações necessárias.

### Scripts

**$PROJECT_DIR/build.sh**: Efetua o build e execução do projeto como um todo, com criação de certificados auto assinados e execução dos containers. O usuário e senha do exemplo é o padrão do CAS Server, Usuário: **casuser**, senha: **Mellon**.

**$PROJECT_DIR/archetype/install.sh**: Instala no .m2 do usuário um Maven Archetype de um projeto com Spring Framework, Spring Security, CAS Client e Apache CXF.

**$PROJECT_DIR/archetype/create_project.sh**: Exemplo de criação de um projeto utilizando o Maven Archetype com o comando mvn archetype:generate.

### Configurações
Esse projeto utiliza os domínios **api.alexandre.com.br** e **portal.alexandre.com.br** (domínios de *.alexandre.com.br do Self-Signed Certificate gerado pelo exemplo) no Nginx portanto é necessário configurar a resolução de nomes. Ficaria desse modo no /etc/hosts do GNU/Linux:

```sh
127.0.0.1       api.alexandre.com.br
127.0.0.1       portal.alexandre.com.br
```


### URLS

[Customer Service](https://api.alexandre.com.br/customer-service/customer)
[Product Service](https://api.alexandre.com.br/product-service/wishList)
