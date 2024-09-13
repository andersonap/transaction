# Transaction API

Este projeto foi desenvolvido como parte de um desafio técnico. Trata-se de uma API REST que processa transações financeiras, e atualiza os saldos de acordo com as categorias de transação.

## Índice
- [Descrição](#descrição)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Instalação e Execução](#instalação-e-execução)
- [Testes Automatizados](#testes-automatizados)
- [Melhorias Futuras](#melhorias-futuras)
- [L4 - Transações simultâneas](#L4-transações-simultâneas)

## Descrição

A **Transaction API** é uma aplicação baseada em microserviços, construída com o objetivo de processar e armazenar transações financeiras. A API categoriza as transações com base no código MCC (Merchant Category Code) e atualiza os saldos de acordo com a categoria associada ao comerciante. O projeto foca em princípios como:
- Boa prática de código;
- Uso de testes unitários e de integração;
- Manutenção de consistência em operações de débito.

## Funcionalidades

- **Criação de transações**: Crie uma nova transação financeira para uma conta.
- **Atualização de saldo**: Atualiza o saldo da conta com base na categoria da transação (ex.: comida, entretenimento, etc.).
- **Categorias de transações**: As transações são categorizadas automaticamente com base no MCC informado.
- **Tratamento de erros**: Casos como saldo insuficiente são devidamente tratados e reportados ao cliente.
- **Persistência**: Todas as transações são salvas em um banco de dados relacional.

## Tecnologias Utilizadas

- **Java 22**: Linguagem principal do projeto.
- **Spring Boot**: Framework para a construção da API.
- **Spring Data JPA**: Abstração para acesso ao banco de dados.
- **H2 Database**: Banco de dados em memória utilizado para fins de teste e desenvolvimento.
- **JUnit 5**: Framework de testes unitários.
- **Mockito**: Biblioteca para criação de mocks em testes.
- **Lombok**: Redução de boilerplate no código Java.
- **Maven**: Gerenciador de dependências e build.

## Instalação e Execução

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- [JDK 22](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)

### Passo a passo

1. Clone o repositório:

    ```bash
    git clone https://github.com/andersonap/transaction.git
    ```

2. Navegue até o diretório do projeto:

    ```bash
    cd transaction
    ```

3. Compile e execute o projeto:

    ```bash
    mvn spring-boot:run
    ```

## Testes Automatizados
### Testes incluídos

- **Testes unitários**: Cobrem a lógica dos serviços, verificando o correto comportamento de cada função.
- **Testes de integração**: Verificam a interação entre as diferentes camadas da aplicação, como os repositórios e serviços.

### Como rodar os testes

Execute o seguinte comando para rodar todos os testes automatizados:

```bash
mvn test
```

## Melhorias Futuras

- **Autenticação e Autorização**: Implementar OAuth2 ou JWT para proteger os endpoints da API.
- **Integração com Mensageria**: Adicionar suporte a filas, como AWS SQS, para processamento assíncrono de transações.
- **Cache**: Implementar mecanismos de cache para otimizar consultas de saldos e transações frequentes.
- **Melhorar os logs**: Adicionar logs mais detalhados para melhor rastreamento de problemas em produção.


## L4 - Transações simultâneas

Algumas abordagens podem ser adotadas para suportar transações simultâneas

### Locks Pessimistas
Usar um lock pessimista no banco de dados, garante que um registro (transação, saldo) será alterado por um único processo. O Spring JPA dá suporte para o uso de locks (otimistas e pessimistas).

### Uso de filas (Mensagem Assíncrona)
O uso de uma fila de mensagens (como AWS SQS, Kafka ou RabbitMQ), garante que cada transação seria colocada em uma fila para processamento sequencial.

### Uso de banco de dados com suporte nativo para concorrência
O PostgreSQL é um exemplo de banco que suporta transações concorrentes de forma nativa.

### Locks distribuídos
Considerando um cenário de produção, onde existem múltiplas instâncias da aplicação rodando de forma distribuída, o uso de um lock distribuído como o Redis pode ser uma ótima solução.
