import java.io.*;
import java.util.Scanner;

class Aluno {
    String matricula, nome;
    int idade;

    public Aluno(String matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }
}

class Disciplina {
    String codigo, nome;
    double notaMinima;

    public Disciplina(String codigo, String nome, double notaMinima) {
        this.codigo = codigo;
        this.nome = nome;
        this.notaMinima = notaMinima;
    }
}

class Nota {
    String matriculaAluno, codigoDisciplina;
    double nota1, nota2;

    public Nota(String matriculaAluno, String codigoDisciplina, double nota1, double nota2) {
        this.matriculaAluno = matriculaAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public double calcularMedia() {
        return (nota1 + nota2) / 2;
    }
}

public class Main {
    private static Aluno[] alunos = new Aluno[30];
    private static Disciplina[] disciplinas = new Disciplina[10];
    private static Nota[] notas = new Nota[60];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        carregarAlunos("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Alunos.txt");
        carregarDisciplinas("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Disciplinas.txt");
        carregarNotas("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Notas.txt");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Buscar resultados");
            System.out.println("2. Sair");
            System.out.print("Escolha: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha == 1) buscarResultados(scanner);
            else if (escolha == 2) break;
        }
    }

    private static void carregarAlunos(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int index = 0;
            while ((linha = br.readLine()) != null && index < alunos.length) {
                String[] dados = linha.split(";");
                alunos[index++] = new Aluno(dados[0], dados[1], Integer.parseInt(dados[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + arquivo);
        }
    }

    private static void carregarDisciplinas(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int index = 0;
            while ((linha = br.readLine()) != null && index < disciplinas.length) {
                String[] dados = linha.split(";");
                disciplinas[index++] = new Disciplina(dados[0], dados[1], Double.parseDouble(dados[2].trim()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + arquivo);
        }
    }

    private static void carregarNotas(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            int index = 0;
            while ((linha = br.readLine()) != null && index < notas.length) {
                String[] dados = linha.split(";");
                notas[index++] = new Nota(dados[0], dados[1], Double.parseDouble(dados[2].trim()), Double.parseDouble(dados[3].trim()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + arquivo);
        }
    }

    private static void buscarResultados(Scanner scanner) {
        System.out.println("1. Por Aluno");
        System.out.println("2. Por Disciplina");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) buscarPorAluno(scanner);
        else if (escolha == 2) buscarPorDisciplina(scanner);
    }

    private static void buscarPorAluno(Scanner scanner) {
        System.out.print("Informe o nome ou matrícula do aluno: ");
        String busca = scanner.nextLine();

        for (Aluno aluno : alunos) {
            if (aluno != null && (aluno.matricula.equalsIgnoreCase(busca) || aluno.nome.equalsIgnoreCase(busca))) {
                StringBuilder resultado = new StringBuilder("Resultados para o aluno: " + aluno.nome + "\n");
                for (Nota nota : notas) {
                    if (nota != null && nota.matriculaAluno.equals(aluno.matricula)) {
                        Disciplina disciplina = encontrarDisciplina(nota.codigoDisciplina);
                        if (disciplina != null) {
                            double media = nota.calcularMedia();
                            resultado.append("Disciplina: ").append(disciplina.nome)
                                    .append(" - Nota 1: ").append(nota.nota1)
                                    .append(" - Nota 2: ").append(nota.nota2)
                                    .append(" - Média: ").append(media)
                                    .append(" - Resultado: ")
                                    .append(media >= disciplina.notaMinima ? "Aprovado" : "Reprovado")
                                    .append("\n");
                        }
                    }
                }
                salvarEmArquivo(aluno.matricula + ".txt", resultado.toString());
                System.out.println(resultado);
                return;
            }
        }
        System.out.println("Aluno não encontrado.");
    }

    private static void buscarPorDisciplina(Scanner scanner) {
        System.out.print("Informe o nome ou código da disciplina: ");
        String busca = scanner.nextLine();

        for (Disciplina disciplina : disciplinas) {
            if (disciplina != null && (disciplina.codigo.equalsIgnoreCase(busca) || disciplina.nome.equalsIgnoreCase(busca))) {
                StringBuilder resultado = new StringBuilder("Resultados para a disciplina: " + disciplina.nome + "\n");
                for (Nota nota : notas) {
                    if (nota != null && nota.codigoDisciplina.equals(disciplina.codigo)) {
                        Aluno aluno = encontrarAluno(nota.matriculaAluno);
                        if (aluno != null) {
                            double media = nota.calcularMedia();
                            resultado.append("Aluno: ").append(aluno.nome)
                                    .append(" - Nota 1: ").append(nota.nota1)
                                    .append(" - Nota 2: ").append(nota.nota2)
                                    .append(" - Média: ").append(media)
                                    .append(" - Resultado: ")
                                    .append(media >= disciplina.notaMinima ? "Aprovado" : "Reprovado")
                                    .append("\n");
                        }
                    }
                }
                salvarEmArquivo(disciplina.codigo + ".txt", resultado.toString());
                System.out.println(resultado);
                return;
            }
        }
        System.out.println("Disciplina não encontrada.");
    }

    private static Aluno encontrarAluno(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno != null && aluno.matricula.equals(matricula)) return aluno;
        }
        return null;
    }

    private static Disciplina encontrarDisciplina(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina != null && disciplina.codigo.equals(codigo)) return disciplina;
        }
        return null;
    }

    private static void salvarEmArquivo(String nomeArquivo, String conteudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write(conteudo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo " + nomeArquivo);
        }
    }
}
