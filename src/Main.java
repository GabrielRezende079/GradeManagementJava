import java.io.*; //escrever dados em arquivos
import java.util.*; //ler diferentes tipos de entrada
import java.io.FileWriter; //Escrever
import java.io.IOException; //resposta para o catch

class Aluno { //Classe que Absosrve os dados de Aluno.txt
    String matricula;
    String nome;
    int idade;

    public Aluno(String matricula, String nome, int idade) { //Construtor da classe. Serve para inicializar os atributos do objeto com os valores passados como parâmetros.
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }
}

class Disciplina { //Absorve de Disciplinas.txt
    String codigo;
    String nome;
    double notaMinima;
    double nota1;
    double nota2;
    double media;

    public Disciplina(String codigo, String nome, double notaMinima) { //Contrutor Também
        this.codigo = codigo;
        this.nome = nome;
        this.notaMinima = notaMinima;
        this.nota1 = 0.0;
        this.nota2 = 0.0;
        this.media = 0.0;
    }

    public void calcularMedia() { //Aqui é pra ter uma função que calcula média
        this.media = (nota1 + nota2) / 2;
    }
}

class Curso {
    String codigo;
    String nome;

    public Curso(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
}

class Nota {
    String matriculaAluno;
    String codigoDisciplina;
    double nota1;
    double nota2;

    public Nota(String matriculaAluno, String codigoDisciplina, double nota1, double nota2) {
        this.matriculaAluno = matriculaAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public double calcularMedia() {
        return (nota1 + nota2) / 2; // Tem que retornar o valor de algum lugar né chefe
    }
}

public class Main { //Esta é a classe do programa em si
    //Aqui estamos puxando as classes la de cima para trazer sentido pro programa
    private static List<Aluno> alunos = new ArrayList<>();//Criando ArrayList para cada informação dos txt, assim podemos armazenar quantos dados eu quiser
    private static List<Disciplina> disciplinas = new ArrayList<>();
    private static List<Curso> cursos = new ArrayList<>();
    private static List<Nota> notas = new ArrayList<>();

    public static void main(String[] args) { // incluindo o caminho para os txt/banco de dados light
        carregarDados("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Alunos.txt", alunos, "aluno");
        carregarDados("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Disciplinas.txt", disciplinas, "disciplina");
        carregarDados("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Cursos.txt", cursos, "curso");
        carregarNotas("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Notas.txt");

        Scanner scanner = new Scanner(System.in); // criando o escaneador ou input ou cara que Lê do terminal
        while (true) { // "Enquanto for verdade apresnete...
            System.out.println("\nMenu:");
            System.out.println("1. Buscar resultados");
            System.out.println("2. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); //pula pra proxima linha

            //Melhor coisa da programação If e Else
            if (opcao == 2) break;
            if (opcao == 1) buscarResultados(scanner); // "Se escolher opção 1 Caminhar para BuscarReseuldos
        }
    }

    //Aqui é o "historico" irá salvar os dados em um novo Txt
    private static void salvarEmArquivo(String nomeArquivo, String conteudo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) { //Aqui é onde se cria o arquivo em si
            writer.write(conteudo);
            System.out.println("Arquivo " + nomeArquivo + " salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }


    //Uma das partes mais importantes. Recolher os dados de Disciplinas.txt em arrays, trazendo um sentido verdadeiro para o código
    private static void buscarPorDisciplina(Scanner scanner) { //O metodo recebe o scanner
        System.out.print("Informe o nome ou código da disciplina: ");
        String busca = scanner.nextLine(); // lê a variavel "Busca" que ta na proxima linha

        for (Disciplina disciplina : disciplinas) { //vai atras da lista Diciplinas
            if (disciplina.codigo.equalsIgnoreCase(busca) || disciplina.nome.equalsIgnoreCase(busca)) { //Compara a variavel busca com o codigo da disciplina escrito no txt
                StringBuilder resultado = new StringBuilder("Resultados para a disciplina: " + disciplina.nome + "\n"); //StringBuilder facilita a concatenação "+" de txtos

                for (Nota nota : notas) {
                    if (nota.codigoDisciplina.equals(disciplina.codigo)) {// Verifica se cada nota ta em disciplina
                        Aluno aluno = alunos.stream()
                                .filter(a -> a.matricula.equals(nota.matriculaAluno)) //filtra os alunos procurando quem tem a nota
                                .findFirst().orElse(null);
                        if (aluno != null) {
                            double media = nota.calcularMedia(); //se aluno foi encontrado faz a média
                                    resultado.append("Aluno: ").append(aluno.nome) //cosntroí a linha de resultadado
                                    .append(" - Nota 1: ").append(nota.nota1)
                                    .append(" - Nota 2: ").append(nota.nota2)
                                    .append(" - Média: ").append(media)
                                    .append(" - Resultado: ")
                                    .append(media >= disciplina.notaMinima ? "Aprovado" : "Reprovado")
                                    .append("\n");
                        }
                    }
                }
                salvarEmArquivo(disciplina.codigo + ".txt", resultado.toString()); // e finalmente salve num novo txt
                System.out.println(resultado); //retorna exibindo tudo
                return;
            }
        }
        System.out.println("Disciplina não encontrada."); // se não encotrar, é pq não encontrou
    }

    //Aqui ele é a função dos If la de cima
    private static void buscarResultados(Scanner scanner) {
        System.out.println("1. Por Aluno");
        System.out.println("2. Por Disciplina");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) buscarPorAluno(scanner); //leva para essa respetiva Função
        else if (escolha == 2) buscarPorDisciplina(scanner); //leva para essa respetiva Função
    }

    // Segue a mesma logica de BuscarPorDisciplina
    private static void buscarPorAluno(Scanner scanner) {
        System.out.print("Informe o nome ou matrícula do aluno: ");
        String busca = scanner.nextLine();

        for (Aluno aluno : alunos) {
            if (aluno.matricula.equalsIgnoreCase(busca) || aluno.nome.equalsIgnoreCase(busca)) {
                StringBuilder resultado = new StringBuilder("Resultados para: " + aluno.nome + " (Idade: " + aluno.idade + ")\n");
                for (Nota nota : notas) {
                    if (nota.matriculaAluno.equals(aluno.matricula)) {
                        Disciplina disciplina = disciplinas.stream()
                                .filter(d -> d.codigo.equals(nota.codigoDisciplina))
                                .findFirst().orElse(null);
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
                System.out.println(resultado); //exibe tudo
                return;
            }
        }
        System.out.println("Aluno não encontrado.");
    }

    //carrega os dados dos txt
    private static void carregarDados(String arquivo, List lista, String tipo) { //list lista é onde os dados são armazenados, String tipo é o pertencimento deles

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {// Abertura e Leitura
            String linha;
            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(";"); // Ele vai entender a separação por ";" ou CSV
                //Cria o Objeto
                if (tipo.equals("aluno")) lista.add(new Aluno(dados[0], dados[1], Integer.parseInt(dados[2]))); //Se tipo for "aluno" cria dados para aluno
                else if (tipo.equals("disciplina")) lista.add(new Disciplina(dados[0], dados[1], Double.parseDouble(dados[2])));
                else if (tipo.equals("curso")) lista.add(new Curso(dados[0], dados[1]));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + arquivo); //se der tudo errado
        }
    }

    //Esse metodo Lê e lista dados na lista Notas
    private static void carregarNotas(String arquivo) {
        //Abre o arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(";");// Separa ";"

                notas.add(new Nota(dados[0], dados[1], Double.parseDouble(dados[2]), Double.parseDouble(dados[3]))); // cria o objeto da nota
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + arquivo); 
        }
    }
}