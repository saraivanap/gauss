import java.util.Scanner;

public class GaussElimination {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicita o número de variáveis
        System.out.print("Digite o número de variáveis: ");
        int n = scanner.nextInt();

        // Matriz aumentada para armazenar os coeficientes e termos independentes
        double[][] matriz = new double[n][n + 1];

        // Entrada da matriz aumentada
        System.out.println("Digite os coeficientes e os termos independentes:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                matriz[i][j] = scanner.nextDouble();
            }
        }

        // Aplica o método de eliminação de Gauss
        if (!gaussElimination(matriz, n)) {
            System.out.println("O sistema não possui uma solução única.");
            return; // Encerra o programa se não for possível resolver
        }

        // Realiza a substituição retroativa para encontrar a solução
        double[] solucao = retroSubstitution(matriz, n);

        // Exibe a solução
        System.out.println("Soluções:");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %.4f%n", (i + 1), solucao[i]);
        }

        scanner.close();
    }

    // Função para aplicar a eliminação de Gauss
    public static boolean gaussElimination(double[][] matriz, int n) {
        for (int i = 0; i < n; i++) {
            // Verifica se o pivô é zero; em caso positivo, tenta trocar de linha
            if (Math.abs(matriz[i][i]) < 1e-10) {
                boolean isSwapped = false;
                for (int k = i + 1; k < n; k++) {
                    if (Math.abs(matriz[k][i]) > 1e-10) {
                        swapRows(matriz, i, k);
                        isSwapped = true;
                        break;
                    }
                }
                if (!isSwapped) {
                    return false; // Sistema não possui solução única
                }
            }

            // Normaliza a linha atual dividindo pelo pivô
            double pivot = matriz[i][i];
            for (int j = 0; j <= n; j++) {
                matriz[i][j] /= pivot;
            }

            // Elimina os elementos abaixo e acima do pivô
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = matriz[k][i];
                    for (int j = 0; j <= n; j++) {
                        matriz[k][j] -= factor * matriz[i][j];
                    }
                }
            }
        }
        return true; // Retorna verdadeiro se o sistema puder ser resolvido
    }

    // Função para realizar a troca de linhas
    public static void swapRows(double[][] matriz, int row1, int row2) {
        double[] temp = matriz[row1];
        matriz[row1] = matriz[row2];
        matriz[row2] = temp;
    }

    // Função para realizar a substituição retroativa e encontrar as soluções
    public static double[] retroSubstitution(double[][] matriz, int n) {
        double[] solucao = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solucao[i] = matriz[i][n];
            for (int j = i + 1; j < n; j++) {
                solucao[i] -= matriz[i][j] * solucao[j];
            }
        }
        return solucao;
    }
}
