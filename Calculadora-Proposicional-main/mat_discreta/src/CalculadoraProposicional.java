import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CalculadoraProposicional extends JFrame implements ActionListener {

    // Campo de texto para exibir a fórmula digitada pelo usuário
    private JTextField display;
    private String formula = ""; // Variável para armazenar a fórmula atual digitada

    // Conjuntos para armazenar os símbolos válidos e operadores lógicos
    private static final Set<Character> simbolosValidos = new HashSet<>();
    private static final Set<Character> operadores = new HashSet<>();

    // Inicialização dos operadores e símbolos lógicos
    static {
        simbolosValidos.add('¬'); // Negação
        simbolosValidos.add('^');  // Conjunção
        simbolosValidos.add('v');  // Disjunção
        simbolosValidos.add('→');  // Condicional
        simbolosValidos.add('↔');  // Bi-condicional
        simbolosValidos.add('(');  // Parênteses para agrupar expressões
        simbolosValidos.add(')');  // Parênteses para agrupar expressões

        operadores.add('¬');
        operadores.add('^');
        operadores.add('v');
        operadores.add('→');
        operadores.add('↔');
    }

    // Construtor da calculadora proposicional, onde a interface gráfica é configurada
    public CalculadoraProposicional() {
        setTitle("Calculadora Lógica"); // Título da janela
        setSize(450, 600); // Dimensão da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Define o comportamento ao fechar
        setLayout(new BorderLayout());

        // Campo de texto que exibe a fórmula digitada
        display = new JTextField();
        display.setEditable(false); // Não permite edição manual
        display.setFont(new Font("Arial", Font.BOLD, 24)); // Define fonte
        add(display, BorderLayout.NORTH); // Adiciona o campo de texto ao topo

        // Painel onde os botões da calculadora serão posicionados
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10)); // Layout de grade para os botões
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espaçamento

        // Lista dos textos que aparecerão nos botões da interface
        String[] buttons = {
                "A", "B", "C", "D",
                "¬", "^", "v", "→",
                "↔", "(", ")", "Limpar",
                "Verificar", "", "", "Apagar"
        };

        // Adiciona os botões ao painel
        for (String text : buttons) {
            if (!text.equals("")) {
                JButton button = new JButton(text);
                button.setFont(new Font("Arial", Font.BOLD, 18)); // Fonte dos botões
                button.setBackground(new Color(220, 220, 220)); // Cor de fundo
                button.addActionListener(this); // Define o evento de clique
                panel.add(button);
            } else {
                panel.add(new JLabel("")); // Espaço vazio
            }
        }

        add(panel, BorderLayout.CENTER); // Adiciona o painel ao centro da janela

        // Configuração do botão "Verificar" que gera a tabela verdade
        JButton verificarButton = new JButton("Verificar");
        verificarButton.setFont(new Font("Arial", Font.BOLD, 20));
        verificarButton.setBackground(new Color(100, 149, 237));
        verificarButton.setForeground(Color.WHITE);
        verificarButton.addActionListener(this);
        add(verificarButton, BorderLayout.SOUTH); // Adiciona o botão na parte inferior
    }

    // Método principal que inicializa a calculadora
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraProposicional calc = new CalculadoraProposicional();
            calc.setVisible(true); // Torna a janela visível
        });
    }

    // Método que trata os eventos dos botões
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); // Pega o comando do botão pressionado

        if (command.equals("Limpar")) {
            formula = ""; // Limpa a fórmula
            display.setText(formula);
        } else if (command.equals("Apagar")) {
            if (formula.length() > 0) {
                formula = formula.substring(0, formula.length() - 1); // Apaga o último caractere
                display.setText(formula);
            }
        } else if (command.equals("Verificar")) {
            gerarTabelaVerdade(formula); // Gera a tabela verdade
        } else {
            formula += command; // Adiciona o texto do botão à fórmula
            display.setText(formula);
        }
    }

    // Método que gera a tabela verdade para a fórmula inserida
    private static void gerarTabelaVerdade(String formula) {
        List<String> variaveis = extrairVariaveis(formula); // Extrai variáveis da fórmula
        int numVariaveis = variaveis.size();
        int numCombinacoes = 1 << numVariaveis; // Calcula 2^n combinações

        StringBuilder tabela = new StringBuilder();
        for (String variavel : variaveis) {
            tabela.append(variavel).append("\t"); // Adiciona variáveis na primeira linha
        }
        tabela.append("Resultado\n");

        // Para cada combinação de valores de verdade
        for (int i = 0; i < numCombinacoes; i++) {
            String formulaAvaliada = formula;
            // Substitui variáveis pelos valores verdadeiros ou falsos
            for (int j = 0; j < variaveis.size(); j++) {
                String variavel = variaveis.get(j);
                boolean valor = (i & (1 << j)) != 0;
                formulaAvaliada = formulaAvaliada.replace(variavel, valor ? "true" : "false");
                tabela.append(valor ? "V" : "F").append("\t");
            }

            // Avalia a fórmula e adiciona o resultado à tabela
            boolean resultado = avaliarExpressaoBooleana(formulaAvaliada);
            tabela.append(resultado ? "V" : "F").append("\n");
        }

        // Exibe a tabela verdade em uma nova janela
        JTextArea textArea = new JTextArea(tabela.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Tabela Verdade", JOptionPane.INFORMATION_MESSAGE);
    }

    // Extrai variáveis da fórmula (A, B, C, etc.)
    private static List<String> extrairVariaveis(String formula) {
        List<String> variaveis = new ArrayList<>();
        for (char c : formula.toCharArray()) {
            if (Character.isLetter(c) && !variaveis.contains(String.valueOf(c)) && !operadores.contains(c)) {
                variaveis.add(String.valueOf(c)); // Adiciona apenas variáveis únicas
            }
        }
        return variaveis;
    }

    // Avalia a expressão booleana da fórmula
    private static boolean avaliarExpressaoBooleana(String formula) {
        Deque<Boolean> valores = new LinkedList<>(); // Pilha para valores booleanos
        Deque<Character> operadores = new LinkedList<>(); // Pilha para operadores

        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);

            if (c == ' ') continue;

            if (c == 't') {
                valores.push(true); // Encontra 'true' e empilha
                i += 3; // Pula os caracteres "true"
            } else if (c == 'f') {
                valores.push(false); // Encontra 'false' e empilha
                i += 4; // Pula os caracteres "false"
            } else if (c == '(') {
                operadores.push(c); // Empilha '('
            } else if (c == ')') {
                // Desempilha operadores até encontrar '('
                while (operadores.peek() != '(') {
                    valores.push(executarOperador(operadores.pop(), valores));
                }
                operadores.pop(); // Remove '('
            } else if (simbolosValidos.contains(c)) {
                // Desempilha operadores com maior ou igual precedência
                while (!operadores.isEmpty() && precedencia(operadores.peek()) >= precedencia(c)) {
                    valores.push(executarOperador(operadores.pop(), valores));
                }
                operadores.push(c); // Empilha o operador atual
            }
        }

        // Executa os operadores restantes
        while (!operadores.isEmpty()) {
            valores.push(executarOperador(operadores.pop(), valores));
        }

        return valores.pop(); // Retorna o valor final
    }

    // Define a precedência dos operadores
    private static int precedencia(char operador) {
        switch (operador) {
            case '¬': return 5;
            case '^': return 4;
            case 'v': return 3;
            case '→': return 2;
            case '↔': return 1;
            default: return 0;
        }
    }

    // Executa as operações lógicas com base nos operadores
    private static boolean executarOperador(char operador, Deque<Boolean> valores) {
        switch (operador) {
            case '¬':
                return !valores.pop(); // Negação
            case '^': {
                boolean b = valores.pop();
                boolean a = valores.pop();
                return a && b; // Conjunção
            }
            case 'v': {
                boolean b = valores.pop();
                boolean a = valores.pop();
                return a || b; // Disjunção
            }
            case '→': {
                boolean b = valores.pop();
                boolean a = valores.pop();
                return !a || b; // Condicional
            }
            case '↔': {
                boolean b = valores.pop();
                boolean a = valores.pop();
                return a == b; // Bi-condicional
            }
            default:
                return false;
        }
    }
}
