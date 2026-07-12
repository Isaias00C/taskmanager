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
        System.out.println("Bem vindo ao seu task manager:");
        System.out.println("Para iniciar digite seu nome de usuario: ");
        String name = sc.next();
        
        System.out.println("Bem vindo " + name);

        while (true) {
            System.out.println("Escolha uma opcao: ");
            System.out.println("1. criar tarefa");
            System.out.println("2. ver tarefas");
            System.out.println("3. buscar tarefa por nome");
            System.out.println("4. marcar tarefa como concluida (nome da tarefa)");
            System.out.println("0. sair");
        
            int option = sc.nextInt();
            switch (option) {
                case 1 -> createTask();
                case 2 -> getAllTasks();
                case 3 -> getTaskByTitle();
                case 4 -> finishTask();
                case 0 -> { return; }
                default -> { break; }
            }
            
        }
    }
    private static void finishTask() {
 
    }

    private static void getTaskByTitle() {
        System.out.println("Buscar tafera com titulo: ");
        String title = sc.next();
        TaskModel task = taskController.getTask(title);
        System.out.println(task);
    }

    private static void getAllTasks() {
        System.out.println("");
    }
    
    private static void createTask() {
        System.out.println("Titulo da tarefa a ser criada: ");
        String title = sc.next();
        System.out.println("Insira uma descricao ta tarefa");
        String description = sc.next();

        TaskModel task = taskController.createTask(title, description);

        System.out.println("Tarefa " + task.toString() + " criada");
    }
}
