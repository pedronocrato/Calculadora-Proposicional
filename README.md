# Calculadora de Lógica Proposicional em Java

Bem-vindo ao meu repositório da **Calculadora de Lógica Proposicional em Java**! 🚀 Este projeto é dedicado à criação de uma calculadora com interface gráfica (GUI) capaz de avaliar fórmulas da lógica proposicional e gerar suas respectivas tabelas-verdade.

## Sobre

O objetivo deste repositório é demonstrar a implementação de uma ferramenta funcional para a lógica proposicional, utilizando a biblioteca Swing para a interface gráfica. O projeto abrange desde a criação da interface do usuário até a implementação de um algoritmo para analisar e avaliar expressões lógicas complexas, servindo como um recurso prático e educacional.

## Funcionalidades

- **Interface Gráfica Intuitiva**: Interface simples para inserir proposições (A, B, C, D) e operadores lógicos.
- **Operadores Lógicos**: Suporte para os principais conectivos lógicos:
    - `¬` (Negação)
    - `^` (Conjunção - E)
    - `v` (Disjunção - OU)
    - `→` (Condicional)
    - `↔` (Bicondicional)
- **Editor de Fórmulas**: Botões para `Limpar` o campo de entrada e `Apagar` o último caractere.
- **Geração de Tabela-Verdade**: Funcionalidade principal que, ao clicar em "Verificar", analisa a fórmula, extrai as variáveis e gera uma tabela-verdade completa em uma nova janela.
- **Avaliação de Expressões**: Utiliza um algoritmo baseado em pilhas (semelhante ao Shunting-yard) para avaliar o resultado lógico de qualquer combinação de valores-verdade.

## Estrutura do Código

O projeto está contido em uma única classe `CalculadoraProposicional` que herda de `JFrame`, gerenciando tanto a interface gráfica quanto a lógica de avaliação.

### Componentes Principais
- **Interface Gráfica (`JFrame`, `JPanel`, `JButton`)**: Construção da janela e dos botões da calculadora.
- **Manipulação de Eventos (`ActionListener`)**: Captura das ações do usuário, como cliques nos botões.
- **Lógica de Avaliação**:
    - `gerarTabelaVerdade()`: Orquestra a criação da tabela-verdade.
    - `extrairVariaveis()`: Identifica as proposições únicas na fórmula.
    - `avaliarExpressaoBooleana()`: Avalia o valor lógico da expressão usando pilhas (`Deque`).
    - `precedencia()` e `executarOperador()`: Métodos auxiliares para a avaliação da expressão.

##  Ferramentas e Tecnologias

- **Java 11 ou superior**: Linguagem de programação utilizada.
- **Java Swing**: Biblioteca padrão do Java para a criação da interface gráfica.
- IDEs recomendadas: [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/).
