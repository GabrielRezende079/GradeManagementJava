# 📚 Sistema de Gerenciamento de Notas

## 📖 Sobre o Projeto
O **Sistema de Gerenciamento de Notas** é uma aplicação em Java que permite gerenciar notas de alunos, disciplinas e cursos de forma simples e eficiente. Ele lê e processa arquivos `.txt`, armazenando e analisando as informações de alunos, disciplinas e suas respectivas notas.

## 🔥 Funcionalidades
- 📂 **Carregamento de Dados:** Lê informações de alunos, disciplinas e cursos a partir de arquivos `.txt`.
- 🔍 **Consulta de Resultados:** Permite buscar notas por aluno ou por disciplina.
- 📊 **Cálculo de Média:** Calcula a média das notas e indica se o aluno foi aprovado ou reprovado com base na nota mínima da disciplina.
- 💾 **Armazenamento de Resultados:** Salva os resultados das buscas em arquivos `.txt` para referência futura.

## 📁 Estrutura dos Arquivos
Os dados são armazenados e lidos a partir de arquivos `.txt`, com os seguintes formatos:

### **Alunos.txt**
```
MATRICULA;NOME;IDADE
12345;João Silva;20
67890;Maria Oliveira;22
```

### **Disciplinas.txt**
```
CODIGO;NOME;NOTA_MINIMA
MAT101;Matemática;7.0
POR102;Português;5.0
```

### **Notas.txt**
```
MATRICULA;CODIGO_DISCIPLINA;NOTA1;NOTA2
12345;MAT101;8.5;7.0
67890;POR102;6.0;5.5
```

## 🛠 Como Usar
1. **Clone o repositório**:
   ```sh
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```
2. **Compile o projeto**:
   ```sh
   javac Main.java
   ```
3. **Execute o programa**:
   ```sh
   java Main
   ```
4. **Interaja com o menu** e faça consultas por aluno ou disciplina.

## 👨‍💻 Tecnologias Utilizadas
- **Java** 🟡
- **Programação Orientada a Objetos (POO)** 🏛️
- **Leitura e Escrita de Arquivos** 📂


 
