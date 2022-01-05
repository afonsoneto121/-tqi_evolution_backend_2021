# tqi_evolution_backend_2021
Projeto desenvolvido para simular um sistema de pagamento semelhando ao PIX utilizando microsserviços e comunicação assíncrona. 

## Sobre o Projeto

​	A arquitetura é dividida em dois microsserviços(transfer e api-banco). **Api-banco** simula o backend de um sistema bancário sendo responsável por gerenciar uma API REST que cria e consulta transações, transferência de uma chave pix para outra e salva usuários. A API conta também com um sistema de autorização e autenticação baseados em token JWT.

​	**Transfer** simula um serviço centralizado que gerencia a troca de chaves para uma transação ocorrer, troca de valores entre chaves pix, e registras todas as transações em banco de dados próprio, de modo a permitir futura auditoria.

​	A integridade dos dados é feita através  do Schema Registry que fornece uma camada de serviço para os metadados das mensagens. As mensagens são registradas como o formato Apache Avro. 

## Arquitetura 

A arquitetura do projeto é dividida em uma ou mais instâncias de API-banco, uma instância de Transfer e um serviço Apache Kafka executando com o Schema Registry



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

Documentação da API está disponível [aqui]()

## Como executar o projeto



## Em Desenvolvimento

O projeto pode apresentar falhas e funcionar diferente do esperado. Neste caso, por favor entrar em contato com o desenvolvedor afonsoneto121@gmai.com

