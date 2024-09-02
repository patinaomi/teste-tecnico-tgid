
# Projeto de Sistema de Transações entre Empresas e Clientes

## 📋 Descrição

Este projeto foi desenvolvido como parte de um **teste técnico** para a empresa **TGID**. O sistema implementa transações entre **Empresas** e **Clientes**, permitindo que os clientes façam **depósitos** e **saques** em **Empresas**, com o controle de taxas e envio de notificações sobre as transações. O projeto foi construído utilizando **Spring Boot** e segue os princípios de **Clean Code**, **manutenibilidade** de código, e **desacoplamento de componentes**.

## 🚀 Objetivos do Projeto

1. **Usuários:** Implementar dois tipos de usuários: **Empresa** e **Cliente**.
2. **Validações:** 
   - Validação de **CPF** para Clientes e **CNPJ** para Empresas utilizando **annotations** de validação do Spring.
3. **Taxas:**
   - Cada Empresa possui um tipo de taxa de sistema, que é aplicada no momento do **saque**.
4. **Controle de Saldo:**
   - Empresas devem ter um saldo que é atualizado com base nos depósitos e saques realizados pelos Clientes.
   - As transações consideram o desconto das taxas de administração.
5. **Depósitos e Saques:**
   - Os Clientes podem realizar depósitos e saques através das Empresas, considerando os saldos disponíveis.
6. **Notificações e Callback:**
   - Enviar um **callback** para a Empresa informando sobre a transação realizada.
   - Enviar uma **notificação** para o Cliente  via e-mail sobre o status da transação.

## 🛠️ Pontos Principais

- **Lógica para Regras de Negócio:** Implementação de regras de negócio para transações entre Empresas e Clientes, incluindo validações de saldo e taxas.
- **Modelagem de Dados:** Estruturação das entidades de dados, como Cliente, Empresa e Transação, utilizando **JPA**.
- **Clean Code:** Código escrito seguindo os princípios de Clean Code para legibilidade e facilidade de manutenção.
- **Manutenibilidade de Código:** Estrutura modular que facilita a manutenção e extensão do código.
- **Tratamento de Erros:** Tratamento robusto de erros com exceções personalizadas e resposta apropriada para cada cenário.
- **Desacoplamento de Componentes:** Uso de interfaces e injeção de dependência para desacoplamento de componentes e facilidade de teste.

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3**
- **Spring Data JPA** para persistência de dados.
- **H2 Database** para o banco de dados em memória.
- **Lombok** para reduzir o código boilerplate.
- **RestTemplate** para comunicação HTTP e envio de callbacks.
- **Mockito e JUnit 5** para testes unitários.
- **Swagger** para documentação da API.
- **Spring Boot Starter Mail** para envio de notificações por e-mail.
- **Webhook.site** para simulação de callbacks.

## 📑 Documentação da API

A documentação da API foi gerada utilizando o **Swagger**. Para acessar a documentação completa da API e testar os endpoints:

- Acesse: `http://localhost:8080/swagger-ui.html`

## 📊 Estrutura do Projeto

### 1. **Entidades Principais**

- **Cliente:** Representa um usuário que pode realizar depósitos e saques em Empresas. Inclui validação de CPF utilizando **annotations** do Spring.
- **Empresa:** Representa um usuário que recebe depósitos e processa saques de Clientes. Inclui validação de CNPJ utilizando **annotations** do Spring.
- **Transacao:** Representa uma transação entre um Cliente e uma Empresa, seja um depósito ou saque, considerando as taxas de administração aplicadas no **saque**.

### 2. **Serviços**

- **ClienteService:** Serviço que gerencia operações relacionadas a Clientes.
- **EmpresaService:** Serviço que gerencia operações relacionadas a Empresas.
- **TransacaoService:** Serviço que gerencia operações de transações entre Clientes e Empresas, incluindo regras de negócio para taxas e controle de saldo.

### 3. **Controladores**

- **ClienteController:** Exposição de endpoints para operações de CRUD de Clientes e transações.
- **EmpresaController:** Exposição de endpoints para operações de CRUD de Empresas.
- **TransacaoController:** Exposição de endpoints para operações de depósitos e saques.

### 4. **Tratamento de Exceções**

- **GlobalExceptionHandler:** Manejo centralizado de exceções para captura e resposta adequada de erros comuns e personalizados.

## 🚦 Como Executar o Projeto

1. **Execute o projeto:** Utilize o Maven ou a sua IDE favorita (IntelliJ, Eclipse, etc.) para rodar o projeto.

2. **Acesse a aplicação:**
   - Acesse `http://localhost:8080` para utilizar a API.
   - Utilize a documentação Swagger para testar os endpoints.

## 🧪 Testes Unitários

Os testes unitários foram escritos utilizando **JUnit 5** e **Mockito** para garantir a cobertura e a confiabilidade do código.

- **Cobertura de Testes:**
  - Testes para validação de regras de negócio (e.g., saldo insuficiente, taxas de transação).
  - Testes para os serviços de transação, incluindo callbacks e envio de notificações.
  - Testes para tratamento de exceções.

## 🌟 Diferenciais Implementados

- **Spring Boot:** Utilizado para agilizar o desenvolvimento e fornecer uma base robusta para a aplicação.
- **Documentação com Swagger:** Documentação clara e interativa dos endpoints da API.
- **Propostas de Arquitetura:** Estrutura modular, uso de boas práticas de código e separação clara de responsabilidades.

## 📬 Contato

- **Patricia Naomi**
- [LinkedIn](https://www.linkedin.com/in/patinaomi)

