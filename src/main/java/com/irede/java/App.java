package com.irede.java;

import java.util.Scanner;

import com.irede.java.controllers.TaskController;
import com.irede.java.models.TaskModel;

public class App 
{
    static Scanner sc = new Scanner(System.in);
    static TaskController taskController = new TaskController();
    public static void main( String[] args )
    {
        System.out.println("------------Bem vindo ao seu task manager-----------");
        System.out.print("Para iniciar digite seu nome de usuario: ");
        String name = sc.next();
        
        System.out.println("Bem vindo " + name);

        while (true) {
            System.out.println("-----------------------------");
            System.out.println("Escolha uma opção: ");
            System.out.println("1. criar tarefa");
            System.out.println("2. ver tarefas");
            System.out.println("3. buscar tarefa por nome");
            System.out.println("4. atualizar tarefa");
            System.out.println("0. sair");
            System.out.print("opção: ");
        
            int option = sc.nextInt();
            sc.nextLine();
            try {
                switch (option) {
                    case 1 -> createTask(name);
                    case 2 -> getAllTasks();
                    case 3 -> getTaskByTitle();
                    case 4 -> updateTaskByTitle();
                    case 0 -> { return; }
                    default -> { break; }
                }
            } catch (RuntimeException e) {
                System.out.println("***Erro***: " + e.getMessage());
            }
            
        }
    }

    private static void updateTaskByTitle(){
        System.out.print("Nome da tarefa que irá atualizar: ");
        String title = sc.nextLine();

        System.out.println("O que deseja atualizar");
        System.out.println("1. descricao");
        System.out.println("2. status");

        System.out.print("opcao: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> updateDescriptionByTitle(title);
            case 2 -> updateStatusByTitle(title);
            default -> { break; }
        }
        
    }

    private static void updateDescriptionByTitle(String title) {
        System.out.println("Qual a nova descrição para a tarefa? ");
        String newDescription = sc.nextLine();
        taskController.updateDescription(title, newDescription);
    }

    private static void updateStatusByTitle(String title) {
        System.out.println("Atualizar para que opção: ");
        System.out.println("1. Em andamento");
        System.out.println("2. Concluída");
        System.out.print("opção: ");
        int choice = sc.nextInt();
        sc.nextLine();

        taskController.updateStatus(title, choice);
    }

    private static void getTaskByTitle() {
        System.out.print("Buscar tarefa de título: ");
        String title = sc.nextLine();

        TaskModel task = taskController.getTaskByTitle(title);
        System.out.println(task.toString());
    }

    private static void getAllTasks() {
        taskController.getAllTasks();
    }

    private static void createTask(String name) {
        System.out.print("Título da tarefa a ser criada: ");
        String title = sc.nextLine();

        System.out.print("Insira uma descrição para a tarefa: ");
        String description = sc.nextLine();

        TaskModel task = taskController.createTask(name, title, description);

        System.out.println("Tarefa " + task.toString() + " criada");
    }
}
