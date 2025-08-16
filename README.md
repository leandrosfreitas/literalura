# 📚 Literalura

O **Literalura** é uma aplicação em **Java com Spring Boot** que permite consultar livros e autores diretamente da [API Gutendex](https://gutendex.com/) e armazená-los em um banco de dados **PostgreSQL**.  
A aplicação possui um menu interativo em console, onde o usuário pode buscar livros, listar autores e aplicar filtros de idioma e ano.

---

## 🚀 Descrição do Projeto

Este projeto foi desenvolvido como prática de **Spring Boot, JPA/Hibernate e consumo de APIs REST**.  
Com ele é possível:

- 🔍 Buscar livros pelo título na API Gutendex (com persistência automática no banco).
- 📖 Listar todos os livros registrados no banco de dados.
- ✍️ Listar todos os autores e suas respectivas obras.
- ⏳ Listar autores que estavam vivos em determinado ano.
- 🌎 Listar livros filtrados por idioma (Português, Inglês, Francês, Espanhol).

---

## ⚙️ Como instalar e executar

### Pré-requisitos
- **Java 21+**
- **Maven**
- **PostgreSQL** (criar um banco chamado `literalura`)
- **IDE** (IntelliJ, Eclipse, VSCode, etc)

### Passos para executar
1. **Clonar o repositório**
   ```bash
   git clone https://github.com/seu-usuario/literalura.git
   cd literalura
   ```

2. **Configurar o banco de dados**  
   Edite o arquivo `application.properties` em `src/main/resources` com suas credenciais do PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Compilar e executar**
   ```bash
   mvn spring-boot:run
   ```

4. O sistema será iniciado e exibirá o **menu interativo** no console.

---

## 🖥️ Exemplo de Uso

Ao rodar a aplicação, o menu principal será exibido:

```
=== MENU LITERALURA ===
1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em um determinado ano
5 - Listar livros em um determinado idioma
0 - Sair
Escolha uma opção:
```

Exemplo: Buscar o livro **Dom Casmurro**  
```
Digite o título do livro: Dom Casmurro

--- LIVRO ---
Título: Dom Casmurro
Autor(es): Machado de Assis
Idioma: pt
Downloads: 5324
-------------
```

---

## 🔮 Contribuições Futuras

Algumas melhorias planejadas para próximas versões:

- [ ] Criar interface gráfica com **Spring Web** (API REST).
- [ ] Implementar testes automatizados com **JUnit e Mockito**.
- [ ] Melhorar tratamento de erros e mensagens ao usuário.
- [ ] Implementar cache para buscas repetidas.
- [ ] Adicionar suporte a mais idiomas e filtros avançados.

---
