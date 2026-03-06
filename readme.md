
# Agendador de Notifcações

Projeto de desafio técnico visando apresentar as habilidades de desenvolvimento java backend.


## Rodando localmente

Clone o project

```bash
  git clone https://github.com/ThgCastro/agendamento-notificacao-api.git
```

Entre no diretório do projeto

```bash
    docker-compose up --build

```

## Documentação da API


#### Cadastra a notificação

```http
  POST /agendamento
```

| Body | Tipo     | Descrição                |
| :-------- | :------- | :------------------------- |
| `emailDestinatario` | `string` | **Obrigatório**. Email do destinatário |
| `telefoneDestinatario` | `string` | **Obrigatório**. Telefone do destinatário |
| `mensagem` | `string` | **Obrigatório**. Mensagem da notificação |
| `dataHoraEnvio` | `LocalDateTime` | **Obrigatório**. Data hora do evento no formado dd-MM-yyyy HH:mm:ss |


#### Retorna uma notificação por id

```http
  GET /agendamento/${id}
```

| Prarâmetro | Tipo     | Descrição                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Obrigatório**. Id da mensagem procurada |

#### Cancela a notificação por id

```http
  DELETE /agendamento/${id}
```

| Prarâmetro | Tipo     | Descrição                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Obrigatório**. Id da mensagem que você quer cancelar |



## Rodando os testes

Para executar os testes, execute o seguinte comando.

```bash
  .\gradlew test
```

