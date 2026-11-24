import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int TAMANHO = 10;
    private static final char AGUA = '~';
    private static final char NAVIO = 'N';
    private static final char ACERTO = 'X';
    private static final char ERRO = 'o';

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Random sorteio = new Random();
        char[][] tabuleiro = new char[TAMANHO][TAMANHO];

        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                tabuleiro[linha][coluna] = AGUA;
            }
        }

        colocarNavios(tabuleiro, sorteio);
        int acertos = 0;
        int totalNavios = contarNavios(tabuleiro);

        System.out.println("=== Batalha Naval ===");
        while (acertos < totalNavios) {
            mostrarTabuleiro(tabuleiro);

            int linha = lerCoordenada(teclado, "Linha (1-10): ");
            int coluna = lerCoordenada(teclado, "Coluna (1-10): ");
            linha--;
            coluna--;

            if (tabuleiro[linha][coluna] == ACERTO || tabuleiro[linha][coluna] == ERRO) {
                System.out.println("Você já tentou essa posição.");
            } else if (tabuleiro[linha][coluna] == NAVIO) {
                tabuleiro[linha][coluna] = ACERTO;
                acertos++;
                System.out.println("Acertou!");
            } else {
                tabuleiro[linha][coluna] = ERRO;
                System.out.println("Água!");
            }
        }

        mostrarTabuleiro(tabuleiro);
        System.out.println("Parabéns! Você afundou todos os navios!");
        teclado.close();
    }

    private static void colocarNavios(char[][] tabuleiro, Random sorteio) {
        int[] tamanhos = {4, 3, 3, 2, 2};

        for (int tamanho : tamanhos) {
            boolean colocado = false;
            while (!colocado) {
                boolean horizontal = sorteio.nextBoolean();
                int linha = sorteio.nextInt(TAMANHO);
                int coluna = sorteio.nextInt(TAMANHO);

                if (podeColocar(tabuleiro, linha, coluna, tamanho, horizontal)) {
                    for (int i = 0; i < tamanho; i++) {
                        if (horizontal) {
                            tabuleiro[linha][coluna + i] = NAVIO;
                        } else {
                            tabuleiro[linha + i][coluna] = NAVIO;
                        }
                    }
                    colocado = true;
                }
            }
        }
    }

    private static boolean podeColocar(char[][] tabuleiro, int linha, int coluna,
                                       int tamanho, boolean horizontal) {
        for (int i = 0; i < tamanho; i++) {
            int linhaAtual = horizontal ? linha : linha + i;
            int colunaAtual = horizontal ? coluna + i : coluna;

            if (linhaAtual >= TAMANHO || colunaAtual >= TAMANHO
                    || tabuleiro[linhaAtual][colunaAtual] != AGUA) {
                return false;
            }
        }
        return true;
    }

    private static int contarNavios(char[][] tabuleiro) {
        int total = 0;
        for (char[] linha : tabuleiro) {
            for (char casa : linha) {
                if (casa == NAVIO) {
                    total++;
                }
            }
        }
        return total;
    }

    private static int lerCoordenada(Scanner teclado, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (teclado.hasNextInt()) {
                int valor = teclado.nextInt();
                if (valor >= 1 && valor <= TAMANHO) {
                    return valor;
                }
            } else {
                teclado.next();
            }
            System.out.println("Digite um número entre 1 e 10.");
        }
    }

    private static void mostrarTabuleiro(char[][] tabuleiro) {
        System.out.print("   ");
        for (int coluna = 1; coluna <= TAMANHO; coluna++) {
            System.out.printf("%2d", coluna);
        }
        System.out.println();

        for (int linha = 0; linha < TAMANHO; linha++) {
            System.out.printf("%2d ", linha + 1);
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                char casa = tabuleiro[linha][coluna];
                System.out.print(casa == NAVIO ? " ~" : " " + casa);
            }
            System.out.println();
        }
    }
}
