# tqi_evolution_backend_2021
Projeto desenvolvido para simular um sistema de pagamento semelhante ao PIX utilizando microsserviços e comunicação assíncrona. 

## Sobre o Projeto

​	A arquitetura é dividida em dois microsserviços(transfer e api-banco). **Api-banco** simula o backend de um sistema bancário sendo responsável por gerenciar uma API REST que cria e consulta transações, transferência de uma chave pix para outra e salva usuários. A API conta também com um sistema de autorização e autenticação baseados em token JWT.

​	**Transfer** simula um serviço centralizado que gerencia a troca de chaves para uma transação ocorrer, troca de valores entre chaves pix, e registras todas as transações em banco de dados próprio, de modo a permitir futura auditoria.

​	A integridade dos dados é feita através  do Schema Registry que fornece uma camada de serviço para os metadados das mensagens. As mensagens são registradas como o formato Apache Avro. 

## Arquitetura 

A arquitetura do projeto é dividida em uma ou mais instâncias de API-banco, uma instância de Transfer e um serviço Apache Kafka executando com o Schema Registry

![img01](https://media.discordapp.net/attachments/928309890420736074/928317804686479370/Diagrama_em_branco_1.png?width=960&height=416)


## Tecnologias Utilizadas

* Java 16
  * Spring Clould Stream
  * Spring Data
  * Spring Security
* PostgreSQL
* MongoDB
* Git
* Docker
* Apache Kafka
  - Schema Registry
  - Apache Avro

## Documentação

Documentação da API está disponível [aqui](https://github.com/afonsoneto121/-tqi_evolution_backend_2021/blob/main/DOCUMENTATION.md)

## Como executar o projeto

 Para executar o projeto é necessário ter instalado localmente o docker, docker-compose e Java na versão 16. 

* O primeiro passo é baixar o repositório 

 ```sh
 git clone https://github.com/afonsoneto121/-tqi_evolution_backend_2021.git
 ```

* Iniciar os contêineres

```sh
chmod +x init.sh && ./init.sh
```

:arrow_right: Esse comando pode levar alguns minutos para ser concluído

* Compilar os serviços

```sh
cd build && \
chmod +x build.sh init_app_1.sh init_app_2.sh init_transfer.sh && \
./build.sh
```

Antes de iniciar os serviços é necessário verificar se todos os contêineres subiram corretamente. Para isso abra o navegador e entre em  http://localhost:9021/ para monitorar o Apache Kafka, a pagina deverá ser carregada com 1 Healthy clusters. Em seguida http://localhost:8082/ para abrir o mongo express e monitorar a API-Banco o login e senha padrão são respectivamente afneto e admin. Por último http://localhost:54321/ para abrir o pgadmin e monitorar o serviço tansfer o login e senha padrão são respectivamente afonsoneto121@gmail.com e admin.

Após a verificação dos contêineres iniciar os três serviços( 2 instâncias de API-Banco e 1 instância de transfer). Em três terminais diferentes navegue até o diretório tqi_evolution_backend_2021/build e digite em cada terminal.

```sh
./init_app_1.sh
```

```sh
./init_app_2.sh
```

```sh
./init_transfer.sh
```

Para uma demostração de funcionamento veja a [documentação](https://github.com/afonsoneto121/-tqi_evolution_backend_2021/blob/main/DOCUMENTATION.md) ou [exemplos](https://github.com/afonsoneto121/-tqi_evolution_backend_2021/blob/main/EXAMPLE.md).

## Em Desenvolvimento

O projeto pode apresentar falhas e funcionar diferente do esperado. Neste caso, por favor entrar em contato com o desenvolvedor afonsoneto121@gmai.com

