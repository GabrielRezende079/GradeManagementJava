import java.io.*;
import java.util.*;

class Aluno {
    String matricula, nome;
    int idade;

    public Aluno(String matricula, String nome, int idade) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }

    public String toFile() {
        return matricula + ";" + nome + ";" + idade;
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

    public String toFile() {
        return codigo + ";" + nome + ";" + notaMinima;
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

    public String toFile() {
        return matriculaAluno + ";" + codigoDisciplina + ";" + nota1 + ";" + nota2;
    }
}

public class Main {
    private static final String CAMINHO_ALUNOS = "C:\\Users\\Pichau\\Documents\\MeusProj\\GradeManagementJava\\Arquivos\\Alunos.txt";
    private static final String CAMINHO_DISCIPLINAS = "C:\\Users\\Pichau\\Documents\\MeusProj\\GradeManagementJava\\Arquivos\\Disciplinas.txt";
    private static final String CAMINHO_NOTAS = "C:\\Users\\Pichau\\Documents\\MeusProj\\GradeManagementJava\\Arquivos\\Notas.txt";

    private static List<Aluno> alunos = new ArrayList<>();
    private static List<Disciplina> disciplinas = new ArrayList<>();
    private static List<Nota> notas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        carregarAlunos();
        carregarDisciplinas();
        carregarNotas();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Buscar dados");
            System.out.println("2. Editar dados");
            System.out.println("3. Inserir dados");
            System.out.println("4. Remover dados");
            System.out.println("5. Sair");
            System.out.print("Escolha: ");

            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha == 1) buscarResultados(scanner);
            else if (escolha == 2) editarDados(scanner);
            else if (escolha == 3) inserirDados(scanner);
            else if (escolha == 4) removerDados(scanner);
            else if (escolha == 5) break;
        }
    }

    private static void carregarAlunos() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ALUNOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                alunos.add(new Aluno(dados[0], dados[1], Integer.parseInt(dados[2].trim())));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar alunos.");
        }
    }

    private static void carregarDisciplinas() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_DISCIPLINAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                disciplinas.add(new Disciplina(dados[0], dados[1], Double.parseDouble(dados[2].trim())));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas.");
        }
    }

    private static void carregarNotas() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_NOTAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                notas.add(new Nota(dados[0], dados[1], Double.parseDouble(dados[2].trim()), Double.parseDouble(dados[3].trim())));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar notas.");
        }
    }

    private static void buscarResultados(Scanner scanner) {
        System.out.println("Buscar por:\n1. Aluno\n2. Disciplina");
        int opcao = Integer.parseInt(scanner.nextLine());
        if (opcao == 1) {
            System.out.print("Digite a matrícula do aluno: ");
            String matricula = scanner.nextLine();
            for (Aluno a : alunos) {
                if (a.matricula.equals(matricula)) {
                    System.out.println("Aluno: " + a.nome);
                    for (Nota n : notas) {
                        if (n.matriculaAluno.equals(matricula)) {
                            Disciplina d = disciplinas.stream().filter(di -> di.codigo.equals(n.codigoDisciplina)).findFirst().orElse(null);
                            if (d != null) {
                                System.out.printf("%s: %.2f e %.2f | Média: %.2f | %s\n",
                                        d.nome, n.nota1, n.nota2, n.calcularMedia(),
                                        n.calcularMedia() >= d.notaMinima ? "Aprovado" : "Reprovado");
                            }
                        }
                    }
                    return;
                }
            }
            System.out.println("Aluno não encontrado.");
        } else if (opcao == 2) {
            System.out.print("Digite o código da disciplina: ");
            String codigo = scanner.nextLine();
            for (Disciplina d : disciplinas) {
                if (d.codigo.equals(codigo)) {
                    System.out.println("Disciplina: " + d.nome);
                    for (Nota n : notas) {
                        if (n.codigoDisciplina.equals(codigo)) {
                            Aluno a = alunos.stream().filter(al -> al.matricula.equals(n.matriculaAluno)).findFirst().orElse(null);
                            if (a != null) {
                                System.out.printf("%s: %.2f e %.2f | Média: %.2f | %s\n",
                                        a.nome, n.nota1, n.nota2, n.calcularMedia(),
                                        n.calcularMedia() >= d.notaMinima ? "Aprovado" : "Reprovado");
                            }
                        }
                    }
                    return;
                }
            }
            System.out.println("Disciplina não encontrada.");
        }
    }

    private static void editarDados(Scanner scanner) {
        System.out.println("1. Editar aluno\n2. Editar disciplina\n3. Editar nota");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Matrícula do aluno: ");
            String mat = scanner.nextLine();
            for (Aluno a : alunos) {
                if (a.matricula.equals(mat)) {
                    System.out.print("Novo nome: ");
                    a.nome = scanner.nextLine();
                    System.out.print("Nova idade: ");
                    a.idade = Integer.parseInt(scanner.nextLine());
                    salvarLista(alunos, CAMINHO_ALUNOS);
                    return;
                }
            }
            System.out.println("Aluno não encontrado.");
        } else if (opcao == 2) {
            System.out.print("Código da disciplina: ");
            String cod = scanner.nextLine();
            for (Disciplina d : disciplinas) {
                if (d.codigo.equals(cod)) {
                    System.out.print("Novo nome: ");
                    d.nome = scanner.nextLine();
                    System.out.print("Nova nota mínima: ");
                    d.notaMinima = Double.parseDouble(scanner.nextLine());
                    salvarLista(disciplinas, CAMINHO_DISCIPLINAS);
                    return;
                }
            }
            System.out.println("Disciplina não encontrada.");
        } else if (opcao == 3) {
            System.out.print("Matrícula do aluno: ");
            String mat = scanner.nextLine();
            System.out.print("Código da disciplina: ");
            String cod = scanner.nextLine();
            for (Nota n : notas) {
                if (n.matriculaAluno.equals(mat) && n.codigoDisciplina.equals(cod)) {
                    System.out.print("Nova nota 1: ");
                    n.nota1 = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nova nota 2: ");
                    n.nota2 = Double.parseDouble(scanner.nextLine());
                    salvarLista(notas, CAMINHO_NOTAS);
                    return;
                }
            }
            System.out.println("Nota não encontrada.");
        }
    }

    private static void inserirDados(Scanner scanner) {
        System.out.println("1. Inserir aluno\n2. Inserir disciplina\n3. Inserir nota");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Matrícula: ");
            String mat = scanner.nextLine();
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Idade: ");
            int idade = Integer.parseInt(scanner.nextLine());
            alunos.add(new Aluno(mat, nome, idade));
            salvarLista(alunos, CAMINHO_ALUNOS);
        } else if (opcao == 2) {
            System.out.print("Código: ");
            String cod = scanner.nextLine();
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Nota mínima: ");
            double notaMin = Double.parseDouble(scanner.nextLine());
            disciplinas.add(new Disciplina(cod, nome, notaMin));
            salvarLista(disciplinas, CAMINHO_DISCIPLINAS);
        } else if (opcao == 3) {
            System.out.print("Matrícula do aluno: ");
            String mat = scanner.nextLine();
            System.out.print("Código da disciplina: ");
            String cod = scanner.nextLine();
            System.out.print("Nota 1: ");
            double n1 = Double.parseDouble(scanner.nextLine());
            System.out.print("Nota 2: ");
            double n2 = Double.parseDouble(scanner.nextLine());
            notas.add(new Nota(mat, cod, n1, n2));
            salvarLista(notas, CAMINHO_NOTAS);
        }
    }

    private static void removerDados(Scanner scanner) {
        System.out.println("1. Remover aluno\n2. Remover disciplina\n3. Remover nota");
        int opcao = Integer.parseInt(scanner.nextLine());

        if (opcao == 1) {
            System.out.print("Matrícula: ");
            String mat = scanner.nextLine();
            alunos.removeIf(a -> a.matricula.equals(mat));
            salvarLista(alunos, CAMINHO_ALUNOS);
        } else if (opcao == 2) {
            System.out.print("Código: ");
            String cod = scanner.nextLine();
            disciplinas.removeIf(d -> d.codigo.equals(cod));
            salvarLista(disciplinas, CAMINHO_DISCIPLINAS);
        } else if (opcao == 3) {
            System.out.print("Matrícula: ");
            String mat = scanner.nextLine();
            System.out.print("Código da disciplina: ");
            String cod = scanner.nextLine();
            notas.removeIf(n -> n.matriculaAluno.equals(mat) && n.codigoDisciplina.equals(cod));
            salvarLista(notas, CAMINHO_NOTAS);
        }
    }

    private static <T> void salvarLista(List<T> lista, String caminho) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (T item : lista) {
                // Cast seguro para chamar o método toFile()
                if (item instanceof Aluno) {
                    bw.write(((Aluno) item).toFile());
                } else if (item instanceof Disciplina) {
                    bw.write(((Disciplina) item).toFile());
                } else if (item instanceof Nota) {
                    bw.write(((Nota) item).toFile());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + caminho);
        }
    }
}
