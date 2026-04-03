# Correções Realizadas

# Problemas encontrados:

| Arquivo            | Método               | Code Smell             | Descrição                                                  |
| ------------------ | -------------------- | ---------------------- | ---------------------------------------------------------- |
| LibrarySystem.java | handleDebugArea()    | Deep Nesting           | Uso excessivo de `if/else` aninhados, dificultando leitura |
| LibrarySystem.java | handleDebugArea()    | Long Method            | Método muito extenso com múltiplas responsabilidades       |
| LibrarySystem.java | startCli()           | Mixed Responsibilities | Mistura controle de fluxo, input e execução                |
| LibrarySystem.java | handleRegisterBook() | Duplicate Code         | Validações duplicadas já existentes em outra camada        |
| LibrarySystem.java | handleBorrowBook()   | Primitive Obsession    | Uso de valores mágicos como "email" e "main"               |
| LibrarySystem.java | handleReturnBook()   | Primitive Obsession    | Uso de valores mágicos como "email", "main" e "handle"     |

# Refatorações Realizadas

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

