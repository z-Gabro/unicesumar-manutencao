# Atividade 2 - Correções

### Bug 1 - BookManager.listBooksSimple()

Local:
src/BookManager.java → método listBooksSimple()

Problema identificado:
Ao tentar listar livros quando a base estava vazia, o sistema lançava uma exceção (IndexOutOfBoundsException).

Causa:
O código tentava acessar um elemento inexistente da lista:

```java
if (temp.size() == 0) {
    System.out.println(temp.get(0)); // ERRO
}
```

Correção aplicada:
Foi adicionada uma verificação adequada para lista vazia, evitando o acesso inválido e informando o usuário corretamente.

```java
if (temp.isEmpty()) {
    System.out.println("No books registered.");
    return;
}
```

Resultado:

- O sistema não quebra mais ao listar livros sem registros
- Melhor feedback para o usuário
- Fluxo preservado sem impacto nas outras funcionalidades

Validação da Correção

Antes:
- Executar listagem sem livros → erro em tempo de execução

Depois:
- Executar listagem sem livros →

```bash
No books registered.
```
### Bug 2 - LoanManager.borrowBook()

Local:
src/LoanManager.java → método borrowBook()

Problema identificado:
Ao realizar um empréstimo com o canal "sms", o sistema criava empréstimos duplicados para o mesmo usuário e livro.

Causa:
Existia um trecho de código legado que adicionava um segundo empréstimo apenas para sincronização com integrações antigas:

```java
if ("sms".equals(channel)) {
    LegacyDatabase.addLoanData(bookId, userId, borrowDate, dueDate, "", "OPEN",
            0.0,
            "loan-created-sync");
}
```

Esse comportamento gerava inconsistência de estado, pois criava dois registros de empréstimo abertos para a mesma operação.

Correção aplicada:
Remoção da criação duplicada de empréstimos, mantendo apenas um registro por operação.

```java
// REMOVIDO: criação duplicada para canal SMS
// if ("sms".equals(channel)) {
//     LegacyDatabase.addLoanData(...);
// }
```
Resultado:

- Cada operação de empréstimo gera apenas um único registro
- Eliminação de inconsistências na base de dados
- Contagem correta de empréstimos por usuário e por livro
- Sistema mais previsível e estável

Validação da Correção
Antes:

- Empréstimo com canal "sms" → 2 registros criados

Depois:

- Empréstimo com canal "sms" → apenas 1 registro criado


## Implementação

### Histórico de Empréstimos por Usuário

Descrição:
Foi implementada uma funcionalidade que permite visualizar o histórico de empréstimos de um usuário específico.

Como funciona:

- O usuário informa o ID
- O sistema lista todos os empréstimos associados

Local da implementação:

- LoanManager.java → lógica de busca
- LibrarySystem.java → integração com CLI

Impacto no sistema:

- Nenhuma alteração nos fluxos existentes
- Apenas leitura de dados já existentes
- Baixo acoplamento

Exemplo de uso:

```bash
10 - User loan history
User ID: 1
```