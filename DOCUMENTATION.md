# Documentação



Documentação gerada automaticamente pelo Postman https://documenter.getpostman.com/view/16544315/UVXdMda9

Todos os endpoints da API são protegidos, exceto o responsável por salvar o usuário e realizar o login.

#### Salvar Usuário

``` 
POST /api/v1/users
```

Body

```json
{
    "name": "Foo",
    "email": "foo@bar.com",
    "password": "1234",
    "balance": 100
}
```

Response

* 400 BAD REQUEST 
  * Se ouver erros na requisição ou o email já estiver sendo utilizado por outro usuário

#### Login

```
POST /login
```

Body

```jso
{
	"email": "foo@bar.com",
    "password": "1234"
}
```

Response 

* Sucesso 200 - OK
  * Token JWT que será usado em outras requisições
* Falha 403 Forbidden

#### Adicionar Chave Pix

```
PATCH api/v1/users/{id}
```

Header

```
AUTHORIZATION Bearer token
```

Body

* Tipo 1  - Array de keys

```json
{
    "keys": [
        {
            "pixTypes": "RANDOM",
        	"key": "12345910"
        }
    ]
}
```

* Tipo 2 - Key única

```json
"key": {
      "pixTypes": "RANDOM",
      "key": "12345910"
}
```

Response

* 400 Bad Request
  * Em caso de requisições com erros de escrita, sem os atributos(key,keys) ou com ambos
  * Caso a chave já exista
* 401 Unauthorized 
  * Caso o id passado na URL seja diferente do usuário válidado para o token atual

#### Status da chave cadastrada

#### 

```
GET api/v1/users/{id}/keys/key1,key2,key3
```

Header

```
AUTHORIZATION Bearer token
```



Response

* 400 Bad Request
  * Em caso de requisições com erros 
* 200 OK
  * Será retornado o status de cada chave

#### Criar uma transação (transaferência)

#### 

```
PATCH api/v1/users/{id}
```

Header

```
AUTHORIZATION Bearer token
```

Body

```json
{
	"keyPixSender":"1234567",
	"value": 10,
	"keyPixReceiver":"abdcef"
}
```

Response

Response

* 400 Bad Request
  * Em caso de requisições com erros 
* 401 Unauthorized
  * Caso a chave enviada não pertença ao usuário logado 
* 200 OK
  * Será retornado o status da transação
