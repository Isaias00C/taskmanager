package com.irede.java;

import java.util.Scanner;

public class App 
{
    static Scanner sc = new Scanner(System.in);
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
                case 3 -> getTaskByName();
                case 4 -> finishTask();
                case 0 -> { return; }
                default -> { break; }
            }
            
        }
    }
    private static void finishTask() {
        System.out.println("Titulo da tarefa a ser criada: ");
        String title = sc.next();
        System.out.println("Insira uma descricao ta tarefa");
        String description = sc.next();
                
    }
    private static void getTaskByName() {

    }
    private static void getAllTasks() {

    }
    private static void createTask() {

    }
}
