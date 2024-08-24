package com.ti2cc;
import java.util.Scanner;

public class Main {
    private static void listStudents(DAO dao) {
        System.out.println("*** Listagem de Alunos ***");
        
        Student[] students = dao.getStudents();
        
        if (students != null && students.length > 0) {
            for (Student student : students) {
                System.out.println(student);
            }
        } else {
            System.out.println("Nenhum aluno encontrado.");
        }
        
        System.out.println(); // Adds a blank line to separate from the next menu
    }

    private static void insertStudent(DAO dao, Scanner scanner) {
        System.out.println("*** Inserir Aluno ***");
        
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String phoneNumber = scanner.nextLine();
        
        System.out.print("Data de Matrícula (YYYY-MM-DD): ");
        String enrollmentDate = scanner.nextLine();
        
        System.out.print("Curso: ");
        String course = scanner.nextLine();

        Student student = new Student(name, email, phoneNumber, enrollmentDate, course);
        
        if (dao.insertStudent(student)) {
            System.out.println("Aluno inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir aluno.");
        }
        
        System.out.println(); // Adds a blank line to separate from the next menu
    }

    private static void deleteStudent(DAO dao, Scanner scanner) {
        System.out.println("*** Excluir Aluno ***");
        
        System.out.print("ID do Aluno: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the new line left by nextInt()

        if (dao.deleteStudent(id)) {
            System.out.println("Aluno excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir aluno. Verifique se o ID está correto.");
        }
        
        System.out.println(); // Adds a blank line to separate from the next menu
    }

    private static void updateStudent(DAO dao, Scanner scanner) {
        System.out.println("*** Atualizar Aluno ***");
        
        System.out.print("ID do Aluno: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the new line left by nextInt()

        System.out.print("Novo Nome: ");
        String name = scanner.nextLine();
        
        System.out.print("Novo Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Novo Telefone: ");
        String phoneNumber = scanner.nextLine();
        
        System.out.print("Nova Data de Matrícula (YYYY-MM-DD): ");
        String enrollmentDate = scanner.nextLine();
        
        System.out.print("Novo Curso: ");
        String course = scanner.nextLine();

        Student student = new Student(id, name, email, phoneNumber, enrollmentDate, course);
        
        if (dao.updateStudent(student)) {
            System.out.println("Aluno atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar aluno. Verifique se o ID está correto.");
        }
        
        System.out.println(); // Adds a blank line to separate from the next menu
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        dao.openConnection();

        while (running) {
            System.out.println("*** Sistema de Gerenciamento de Alunos ***");
            System.out.println("1 - Listar Alunos");
            System.out.println("2 - Inserir Aluno");
            System.out.println("3 - Excluir Aluno");
            System.out.println("4 - Atualizar Aluno");
            System.out.println("5 - Sair");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the new line left by nextInt()
            
            System.out.println(); // Adds a blank line after the choice

            switch (option) {
                case 1:
                    listStudents(dao);
                    break;
                case 2:
                    insertStudent(dao, scanner);
                    break;
                case 3:
                    deleteStudent(dao, scanner);
                    break;
                case 4:
                    updateStudent(dao, scanner);
                    break;
                case 5:
                    running = false;
                    System.out.println("Finalizando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    System.out.println(); // Adds a blank line to separate from the next menu
                    break;
            }
        }

        dao.closeConnection();
        scanner.close();
    }
}