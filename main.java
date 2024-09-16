import java.util.Scanner;

public class GaussElimination {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Receber o número de variáveis
        System.out.println("Digite o número de variáveis: ");
        int n = scanner.nextInt();

        // Criar a matriz aumentada
        double[][] matriz = new double[n][n + 1];

        // Receber a matriz aumentada (coeficientes e termos independentes)
        System.out.println("Digite os coeficientes e os termos independentes (linha por linha): ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                matriz[i][j] = scanner.nextDouble();
            }
        }

        // Aplicar o método de eliminação de Gauss
        gaussElimination(matriz, n);

        // Resolver o sistema por substituição retroativa
        double[] solucao = retroSubstitution(matriz, n);

        // Exibir a solução
        System.out.println("Soluções: ");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %.4f%n", (i + 1), solucao[i]);
        }

        scanner.close();
    }

    // Função para aplicar a eliminação de Gauss
    public static void gaussElimination(double[][] matriz, int n) {
        for (int i = 0; i < n; i++) {
            // Verifica se o pivô é zero, se for troca a linha
            if (matriz[i][i] == 0) {
                boolean isSwapped = false;
                for (int k = i + 1; k < n; k++) {
                    if (matriz[k][i] != 0) {
                        swapRows(matriz, i, k);
                        isSwapped = true;
                        break;
                    }
                }
                if (!isSwapped) {
                    throw new ArithmeticException("Sistema sem solução ou com infinitas soluções.");
                }
            }

            // Normalizar a linha do pivô
            double pivot = matriz[i][i];
            for (int j = 0; j <= n; j++) {
                matriz[i][j] /= pivot;
            }

            // Eliminar as outras linhas
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = matriz[k][i];
                    for (int j = 0; j <= n; j++) {
                        matriz[k][j] -= factor * matriz[i][j];
                    }
                }
            }
        }
    }

    // Função para trocar duas linhas da matriz
    public static void swapRows(double[][] matriz, int row1, int row2) {
        double[] temp = matriz[row1];
        matriz[row1] = matriz[row2];
        matriz[row2] = temp;
    }

    // Função para resolver o sistema usando substituição retroativa
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
