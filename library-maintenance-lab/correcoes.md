# Correções Realizadas

## Problemas encontrados:

| Arquivo            | Método               | Code Smell             | Descrição                                                  |
|--------------------|----------------------|------------------------|------------------------------------------------------------|
| LibrarySystem.java | handleDebugArea()    | Deep Nesting           | Uso excessivo de `if/else` aninhados, dificultando leitura |
| LibrarySystem.java | handleDebugArea()    | Long Method            | Método muito extenso com múltiplas responsabilidades       |
| LibrarySystem.java | startCli()           | Mixed Responsibilities | Mistura controle de fluxo, input e execução                |
| LibrarySystem.java | handleRegisterBook() | Duplicate Code         | Validações duplicadas já existentes em outra camada        |
| LibrarySystem.java | handleBorrowBook()   | Primitive Obsession    | Uso de valores mágicos como "email" e "main"               |
| LibrarySystem.java | handleReturnBook()   | Primitive Obsession    | Uso de valores mágicos como "email", "main" e "handle"     |
| BookManager.java   | registerBook()       | Long Parameter List    | Muitos parâmetros no mesmo método                          |
| BookManager.java   | registerBook()       | Validation Smell       | Método permitindo dado inválido (Causa crash)              |
| BookManager.java   | listBooksSimple()    | Edge Case mal tratado  | Tenta retornar dado de uma lista vazia (Causa crash)       |
| LoanManager.java   | borrowBook()         | Deep Nesting           | Uso excessivo de if/else aninhados                         |
| LoanManager.java   | borrowBook()         | Long Method            | Método extenso com múltiplas responsabilidades             |
| LoanManager.java   | borrowBook()         | Long Parameter List    | Muitos parâmetros no método                                |
| LoanManager.java   | (classe)             | Tight Coupling         | Dependência instanciada diretamente                        |
| LoanManager.java   | returnBook()         | IEH                    | Tratamento inconsistente de erro                           |


## Refatorações Realizadas

1. Refatoração de Deep Nesting

Substituição de múltiplos if/else alinhados por uso de early return

2. Refatoração de Long Method

Simplificação do método handleDebugArea()
Redução da complexidade interna

3. Refatoração de Mixed Responsibilities

Extração de responsabilidades do método startCli()
Criação de métodos auxiliares:
processMenu()
executeOption()

4. Remoção de Duplicate Code

Remoção das validações duplicadas no método handleRegisterBook
Delegação da validação para a camada de negócio (BookManager)

5. Refatoração de Primitive Obsession

Substituição de valores mágicos por constantes:
DEFAULT_CHANNEL
DEFAULT_SOURCE
DEFAULT_HANDLER

6. Refatoração de Long Parameter List

Substituição da lista de parâmetros por uma classe "Book".
A classe contém todos os atributos necessários para cadastrar um livro novo.

7. Refatoração de Validation Smell

Ao invés de permitir o dado inválido e substitui-lo por uma string vazia, o erro é bloqueado com um RuntimeException.

8. Refatoração de Edge Case Mal Tratado

Checa se a lista está vazia ao invés de tentar retornar o primeiro valor dela (causando o Crash).
Caso a lista esteja vazia, retorna uma mensagem dizendo que ela está sem dados.

9. Deep Nesting → Guard Clauses
Os if aninhados foram substituídos por validações diretas (fail fast), reduzindo complexidade e melhorando legibilidade.

10. Long Method → Simplificação do método
O método borrowBook() foi reorganizado, reduzindo sua complexidade interna e tornando o fluxo mais linear.

11. Long Parameter List → Parameter Object
Foi criada a classe BorrowRequest para encapsular os parâmetros do método.
Além disso, foi mantido o método original, que agora delega para o novo método, garantindo compatibilidade com o sistema.

12. Tight Coupling → Dependency Injection
A dependência NotificationService passou a ser injetada via construtor.
Foi mantido um construtor padrão para não quebrar o sistema, reduzindo o acoplamento sem alterar o comportamento externo.

13. Inconsistent Error Handling → Fail Fast
O método returnBook() foi ajustado para lançar exceções em casos inválidos, evitando falhas silenciosas.
