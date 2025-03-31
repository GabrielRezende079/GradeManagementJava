import java.io.*; // Mexer com arquivos
import java.util.Scanner; // "input"

class Aluno { //Classe que Absosrve os dados de Aluno.txt
    String matricula, nome;
    int idade;

    public Aluno(String matricula, String nome, int idade) {//Construtor da classe. Serve para inicializar os atributos do objeto com os valores passados como parâmetros.
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
    }
}

class Disciplina { //Absorve de Disciplinas.txt
    String codigo, nome;
    double notaMinima;

    public Disciplina(String codigo, String nome, double notaMinima) { //Contrutor Também
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

    public double calcularMedia() {//Aqui é pra ter uma função que calcula média
        return (nota1 + nota2) / 2; // Tem que retornar o valor de algum lugar né chefe
    }
}

public class Main { //Esta é a classe do programa em si

    //Criando Arrays para cada informação dos txt, assim podemos armazenar quantos dados eu quiser mas dentro dos limites
    private static Aluno[] alunos = new Aluno[30]; //maximo de 30 alunos
    private static Disciplina[] disciplinas = new Disciplina[10]; //max de 10 matérias
    private static Nota[] notas = new Nota[60]; // mas de 60 notas

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // criando o escaneador ou input ou cara que Lê do terminal
        // incluindo o caminho para os txt/banco de dados light
        carregarAlunos("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Alunos.txt");
        carregarDisciplinas("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Disciplinas.txt");
        carregarNotas("C:\\Users\\Pichau\\Desktop\\Exercicios coding\\JAVA\\Grade_Management\\Arquivos\\Notas.txt");

        while (true) { // "Enquanto for verdade apresnete...
            System.out.println("\nMenu:");
            System.out.println("1. Buscar resultados");
            System.out.println("2. Sair");
            System.out.print("Escolha: ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); //pula pra proxima linha

            //Melhor coisa da programação If e Else
            if (escolha == 1) buscarResultados(scanner);// "Se escolher opção 1 Caminhar para BuscarReseuldos
            else if (escolha == 2) break;
        }
    }

    private static void carregarAlunos(String arquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) { // Abertura e Leitura
            String linha; //usada para armazenar cada linha do arquivo.
            int index = 0; //controla a posição do vetor alunos[]
            while ((linha = br.readLine()) != null && index < alunos.length) { //Lê o arquivo linha por linha e impede que o código tente adicionar mais alunos do que o vetor pode armazenar.

                String[] dados = linha.split(";"); // Ele vai entender a separação por ";" ou CSV
                /*
                dados[0] = "12345";  // Matrícula
                dados[1] = "João Silva"; // Nome
                dados[2] = "22";  // Idade (ainda como String)
                 */

                alunos[index++] = new Aluno(dados[0], dados[1], Integer.parseInt(dados[2].trim())); //Converte e armazena no vetor
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
                disciplinas[index++] = new Disciplina(dados[0], dados[1], Double.parseDouble(dados[2].trim())); //Se tipo for "Disciplina" cria dados para disciplina
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
                notas[index++] = new Nota(dados[0], dados[1], Double.parseDouble(dados[2].trim()), Double.parseDouble(dados[3].trim()));//Se tipo for "Nota" cria dados para nota
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar " + arquivo);
        }
    }

        //Aqui ele é a função dos If la de cima
    private static void buscarResultados(Scanner scanner) {
        System.out.println("1. Por Aluno");
        System.out.println("2. Por Disciplina");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) buscarPorAluno(scanner);//leva para essa respetiva Função
        else if (escolha == 2) buscarPorDisciplina(scanner);//leva para essa respetiva Função
    }

    //Uma das partes mais importantes. Recolher os dados de Alunos.txt em arrays, trazendo um sentido verdadeiro para o código
    private static void buscarPorAluno(Scanner scanner) { //O metodo recebe o scanner
        System.out.print("Informe o nome ou matrícula do aluno: ");
        String busca = scanner.nextLine();// lê a variavel "Busca" que ta na proxima linha

        for (Aluno aluno : alunos) { //vai atras da lista Alunos
            if (aluno != null && (aluno.matricula.equalsIgnoreCase(busca) || aluno.nome.equalsIgnoreCase(busca))) { //Compara a variavel busca com o codigo do Aluno escrito no txt
                StringBuilder resultado = new StringBuilder("Resultados para o aluno: " + aluno.nome + "\n"); //StringBuilder facilita a concatenação "+" de txtos
                for (Nota nota : notas) {
                    if (nota != null && nota.matriculaAluno.equals(aluno.matricula)) { // Verifica se cada nota ta em Aluno
                        Disciplina disciplina = encontrarDisciplina(nota.codigoDisciplina); //filtra as Disciplinas procurando quem tem
                        if (disciplina != null) {
                            double media = nota.calcularMedia(); //faz a média
                            resultado.append("Disciplina: ").append(disciplina.nome) //cosntroí a linha de resultadado
                                    .append(" - Nota 1: ").append(nota.nota1)//cosntroí a linha de resultadado
                                    .append(" - Nota 2: ").append(nota.nota2)//cosntroí a linha de resultadado
                                    .append(" - Média: ").append(media)//cosntroí a linha de resultadado
                                    .append(" - Resultado: ")
                                    .append(media >= disciplina.notaMinima ? "Aprovado" : "Reprovado")
                                    .append("\n");
                        }
                    }
                }
                salvarEmArquivo(aluno.matricula + ".txt", resultado.toString());
                System.out.println(resultado);
                return;// e finalmente salve num novo txt
                //retorna exibindo tudo
            }
        }
        System.out.println("Aluno não encontrado.");
    }

    // Recolher os dados de Disciplinas.txt em arrays, trazendo um sentido verdadeiro para o código
    private static void buscarPorDisciplina(Scanner scanner) { //O metodo recebe o scanner
        System.out.print("Informe o nome ou código da disciplina: ");
        String busca = scanner.nextLine();// lê a variavel "Busca" que ta na proxima linha

        for (Disciplina disciplina : disciplinas) {  //vai atras da lista Diciplinas
            if (disciplina != null && (disciplina.codigo.equalsIgnoreCase(busca) || disciplina.nome.equalsIgnoreCase(busca))) { //Compara a variavel busca com o codigo da disciplina escrito no txt
                StringBuilder resultado = new StringBuilder("Resultados para a disciplina: " + disciplina.nome + "\n"); //StringBuilder facilita a concatenação "+" de txtos
                for (Nota nota : notas) {
                    if (nota != null && nota.codigoDisciplina.equals(disciplina.codigo)) {// Verifica se cada nota ta em disciplina
                        Aluno aluno = encontrarAluno(nota.matriculaAluno); //filtra os alunos procurando quem tem
                        if (aluno != null) {
                            double media = nota.calcularMedia();//se aluno foi encontrado faz a média
                            resultado.append("Aluno: ").append(aluno.nome)//cosntroí a linha de resultadado
                                    .append(" - Nota 1: ").append(nota.nota1)//cosntroí a linha de resultadado
                                    .append(" - Nota 2: ").append(nota.nota2)
                                    .append(" - Média: ").append(media)
                                    .append(" - Resultado: ")
                                    .append(media >= disciplina.notaMinima ? "Aprovado" : "Reprovado")
                                    .append("\n");
                        }
                    }
                }
                salvarEmArquivo(disciplina.codigo + ".txt", resultado.toString()); // e finalmente salve num novo txt
                System.out.println(resultado);
                return;//retorna exibindo tudo
            }
        }
        System.out.println("Disciplina não encontrada.");// se não encotrar, é pq não encontrou
    }

    private static Aluno encontrarAluno(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno != null && aluno.matricula.equals(matricula)) return aluno;
        }
        return null;  // processa o aluno encontrado
    }

    private static Disciplina encontrarDisciplina(String codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina != null && disciplina.codigo.equals(codigo)) return disciplina;
        }
        return null; // processa a disciplina encontrada
    }
    //Aqui é o "historico" irá salvar os dados em um novo Txt
    private static void salvarEmArquivo(String nomeArquivo, String conteudo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) { //Aqui é onde se cria o arquivo em si
            writer.write(conteudo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo " + nomeArquivo);
        }
    }
}
