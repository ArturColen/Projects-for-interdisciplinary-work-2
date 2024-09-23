# Exercícios Avaliativos - Trabalho Interdisciplinar 2 (TI2)
Este repositório foi criado para armazenar o código dos exercícios avaliativos desenvolvidos durante a disciplina de Trabalho Interdisciplinar 2 (TI2), do segundo período do curso de Ciência da Computação.

## 🔨 Funcionalidade dos projetos

### 01 - Somar dois números
Este é o primeiro projeto desenvolvido durante a disciplina, com o objetivo de introduzir os conceitos básicos da linguagem Java e aprofundar o conhecimento sobre a integração da IDE Eclipse com o GitHub. O código simples realiza a soma de dois números inteiros e exibe o resultado ao usuário.

### 02 - Integração com PostgreSQL
Este projeto foi criado com o intuito de treinar os conhecimentos adquiridos até o momento sobre Java, IDE Eclipse, Maven e PostgreSQL, por meio de um sistema simples de cadastro de alunos. Com ele, é possível realizar operações CRUD (criar, listar, atualizar e excluir) de alunos.

### 03 - Integração com Spark
Este projeto foi desenvolvido para aplicar e consolidar os conhecimentos adquiridos sobre Java, IDE Eclipse, Maven, PostgreSQL e Spark. O principal objetivo é fixar conceitos relacionados à criação de APIs utilizando o framework Spark. O sistema implementado permite realizar operações CRUD (criar, listar, atualizar e excluir) de alunos em um banco de dados.

## 💻 Tecnologias usadas
* [Java](https://docs.oracle.com/en/java/): linguagem de programação orientada a objetos, de alto nível, projetada para ser robusta, segura e portável
* [Spark](https://spark.apache.org/docs/latest/): framework de processamento de dados distribuído, de código aberto, projetado para realizar computação em grande escala de forma rápida e eficiente
* [Maven](https://maven.apache.org/guides/index.html): ferramenta de automação de build e gerenciamento de dependências para projetos Java, que simplifica o processo de compilação, teste, e distribuição
* [PostgreSQL](https://www.postgresql.org/docs/current/): sistema de gerenciamento de banco de dados relacional, de código aberto, conhecido por sua robustez, conformidade com padrões SQL
* [Eclipse](https://www.eclipse.org/documentation/): é uma IDE popular e extensível, principalmente utilizada para desenvolvimento em Java

## 📁 Acesso ao Projeto
### 1. Clone [este repositório](https://github.com/ArturColen/Projects-for-interdisciplinary-work-2.git) em sua máquina
* Crie uma pasta no seu computador para este programa.
* Abra o `terminal` dentro dessa pasta.
* Copie a [URL](https://github.com/ArturColen/Projects-for-interdisciplinary-work-2.git) do repositório.
* Digite `git clone <URL copiada>` e pressione `enter`.

### 2. Abra o projeto no Eclipse
* Abra o Eclipse e importe o projeto da pasta onde você clonou o repositório.

### 3. Configure o banco de dados no pgAdmin
* Abra o pgAdmin e crie um banco de dados chamado `school`.

### 4. Crie a tabela `students`
* No pgAdmin, execute o seguinte comando SQL para criar a tabela necessária:
```sql
  CREATE TABLE students (
      id SERIAL PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      email VARCHAR(100) NOT NULL,
      phone_number VARCHAR(15),
      enrollment_date DATE NOT NULL,
      course VARCHAR(50) NOT NULL
  );
```

### 5. Verifique as credenciais do banco de dados
* Certifique-se de que o nome de usuário e a senha do PostgreSQL no pgAdmin correspondem aos valores no arquivo `DAO.java`:
```java
  String username = "ti2cc";
  String password = "ti@cc";
```

### 6. Execute o programa no Eclipse
* Execute o programa no Eclipse e teste as funcionalidades.

**Observação:** No projeto 03, se não for possível rodar o projeto e acessá-lo via navegador no endereço `http://localhost:6789`, pode ser que a porta `6789` já esteja em uso. Nesse caso, é necessário parar a aplicação que está utilizando essa porta e, em seguida, executar o projeto novamente.