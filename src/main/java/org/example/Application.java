package org.example;

import org.example.controller.UserController;
import org.example.controller.UserControllerImpl;
import org.example.entity.User;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Application {
    static Scanner scanner = new Scanner(System.in);
    static UserController userController = new UserControllerImpl();

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в консольное приложение базы данных User!");

        while (true) {
            printMenu();
            String choice = scanner.nextLine();
            handleChoice(choice);
        }
    }

    public static void printMenu(){
        System.out.println("1 - Добавить пользователя");
        System.out.println("2 - Прочитать данные пользователя");
        System.out.println("3 - Обновить данные пользователя (возраст)");
        System.out.println("4 - Удалить пользователя");
        System.out.println("0 - Выход из приложения");
        System.out.print("Выберите действие: ");
    }

    public static void handleChoice(String choice){
        switch (choice){
            case "1": createUser(); break;
            case "2": readUser(); break;
            case "3": updateUser(); break;
            case "4": deleteUser(); break;
            case "0": {
                System.out.println("До свидания!");
                scanner.close();
                System.exit(0);
            }
            default: System.out.println("Неверный выбор! Попробуйте снова.");
        }
    }

    public static void createUser(){
        System.out.println("Добавление пользователя");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        try {
            User user = userController.createUser(name, email, age, LocalDateTime.now());
            System.out.println("Пользователь создан! ID: " + user.getId());
        } catch (Exception e) {
            System.out.println("Ошибка при создании: " + e.getMessage());
        }
    }

    public static void readUser(){
        System.out.println("Поиск пользователя");
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            User user = userController.readUser(id);
            if (user != null) {
                System.out.println("Найден пользователь:");
                System.out.println("ID: " + user.getId());
                System.out.println("Имя: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Возраст: " + user.getAge());
                System.out.println("Создан: " + user.getCreatedAt());
            } else {
                System.out.println("Пользователь с ID " + id + " не найден!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске: " + e.getMessage());
        }
    }

    public static void updateUser(){
        System.out.println("Обновление возраста");
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Введите новый возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        try {
            userController.updateUser(id, age);
            System.out.println("Возраст обновлен!");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении: " + e.getMessage());
        }
    }

    public static void deleteUser(){
        System.out.println("Удаление пользователя");
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            userController.deleteUser(id);
            System.out.println("Пользователь удален!");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }
}