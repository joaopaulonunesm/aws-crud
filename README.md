# PROJETO: aws-crud

## Sobre

Aplicação que prove serviços de CRUD de um Funcionário com objetivo de demonstrar um ambiente AWS.

## Infraestrutura

Essa aplicação está disponível na Amazon EC2 se comunicando com banco de dados DynamoDB por meio de uma imagem no Docker Hub.

###### Docker
Foi criado um repositório no Docker Hub para essa aplicação
```
docker pull joaopaulonunesm/aws-crud:latest
```

###### EC2
Foi criado uma máquina Ubuntu 20.14 onde fizemos a instalção do docker.

Após isso passamos o arquivo docker-compose.yml do projeto para esse máquina.

Com o docker e o docker-compose.yml em mãos, executamos o seguinte comando para subir a aplicação na máquina:
```
sudo docker-compose up
```

###### DynamoDB
A aplicação conta com um banco de dados DynamoDB.

Para isso, foi criado um usuário e um grupo de acesso onde vinculamos esse usuário para realizar as operações no Banco de Dados.

Obs: Foi criado um ApplicationRunner na aplicação para automatizar a criação das tabelas.

## Como subir local:

- Instalar o [Docker](https://docs.docker.com/get-docker/) (docker-compose)

- Fazer o clone do projeto
```
git clone https://github.com/joaopaulonunesm/aws-crud.git
```

- Executar o seguinte comando na pasta raiz do projeto para subir a aplicação
```
sudo docker-compose up
```

## Tecnologias

###### Aplicação
- Java 11
- Spring Boot
- Spring Web
- Spring Devtools
- Spring Validation
- Lombok
- Springfox Swagger
- Spring Data DynamoDB
- Rest Assured

###### Infra
- Docker
- Docker Compose
- EC2
- IAM
- DynamoDB

## Frontend

Foi feito um frontend também para utilizar o serviço S3 da AWS

[Acessar o projeto Frontend](https://github.com/joaopaulonunesm/angular-crud)
