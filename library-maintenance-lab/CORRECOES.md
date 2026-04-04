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