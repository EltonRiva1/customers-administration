## 📌 Customers Administration

![Badge](https://img.shields.io/badge/Status-%20Concluído-green) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.9-brightgreen)

Um sistema de administração de clientes desenvolvido com Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 8**

- **Spring Boot**

- **Spring Data JPA**

- **Hibernate**

- **MySQL**

- **Maven**

## 📂 Estrutura do Projeto

customers-administration/

│── src/

│   ├── main/

│   │   ├── java/br/com/klayrocha/

│   │   ├── resources/

│   │   │   ├── application.properties

│   │   │   ├── static/

│   │   │   ├── templates/

│   ├── test/

│── pom.xml

│── README.md

---

## ⚙️ Configuração e Execução

## 📌 Pré-requisitos

**JDK 8**

**Maven**

**MySQL (ou outro banco configurado no application.properties)**

## ▶ Passos para rodar o projeto
1. Clone o repositório:

  **git clone https://github.com/EltonRiva1/customers-administration.git**
  
  **cd customers-administration**

2. Configure o banco de dados no application.properties:

  **spring.datasource.url=jdbc:mysql://localhost:3306/customers-administration?useTimezone=true&serverTimezone=UTC**
  
  **spring.datasource.username=root**
  
  **spring.datasource.password=root**

3. Execute o projeto via Maven:

   **mvn spring-boot:run**

4. Acesse no navegador:

   **http://localhost:8080**

## 🛠 Possíveis Melhorias

**Implementação de autenticação JWT**

**Integração com frontend (React, Angular, etc.)**

**Implementação de testes automatizados**

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Fork o repositório

2. Crie uma branch (git checkout -b minha-feature)

3. Faça suas alterações e commit (git commit -m 'Minha nova feature')

4. Envie para o GitHub (git push origin minha-feature)

5. Abra um Pull Request

---

🔹 Desenvolvido por [Elton Riva](https://github.com/EltonRiva1) 🚀
