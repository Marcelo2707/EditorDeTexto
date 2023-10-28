import java.io.*;
import java.util.Scanner;

public class App {
    private static final int tamanhoVetor = 100;
    private static final String nomeArquivo = "texto.txt";
    private static String[] textoVetor = new String[tamanhoVetor];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarVetor();
        processoInput();
    }

    private static void inicializarVetor() {
        File file = new File(nomeArquivo);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
                String line;
                int index = 0;
                while ((line = br.readLine()) != null && index < tamanhoVetor) {
                    textoVetor[index] = line;
                    index++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            textoVetor = new String[tamanhoVetor];
        }
    }

    private static void processoInput() {
        while (true) {
            System.out.println("\nComandos disponíveis:");
            System.out.println("\\sv - Salvar");
            System.out.println("\\q  - Sair");
            System.out.println("\\ed # - Editar linha");
            System.out.println("Digite um texto: ");

            String input = scanner.nextLine();
            if (input.equals("\\sv")) {
                salvar();
                System.out.println("Arquivo salvo.");
            } else if (input.equals("\\q")) {
                System.out.println("Saindo do programa.");
                break;
            } else if (input.startsWith("\\ed")) {
                try {
                    int lineNumber = Integer.parseInt(input.substring(4).trim());
                    editar(lineNumber);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.out.println("Comando inválido. Use \\ed #, onde # é o número da linha.");
                }
            } else {
                adicionar(input);
            }
        }
    }

    private static void salvar() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            for (String line : textoVetor) {
                if (line != null) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void adicionar(String texto) {
        for (int i = 0; i < tamanhoVetor; i++) {
            if (textoVetor[i] == null) {
                textoVetor[i] = texto;
                break;
            }
        }
    }

    private static void editar(int linhaNumero) {
        if (linhaNumero < 0 || linhaNumero >= tamanhoVetor) {
            System.out.println("Linha inválida.");
            return;
        }
        System.out.println("Digite o novo texto para a linha " + linhaNumero + ":");
        String textoNovo = scanner.nextLine();
        textoVetor[linhaNumero - 1] = textoNovo;
    }
}
