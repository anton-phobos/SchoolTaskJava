package org.example;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // Подключение к базе данных
            Database.connectDB();

            // Создание таблицы
            Database.createTable();

            // Путь к CSV-файлу
            String csvFilePath = "D:/csv_data/school_data.csv";

            // Парсинг CSV-файла
            CsvParser.parseCsv(csvFilePath).forEach(record -> {
                try {
                    Database.addSchoolData(record[0], record[1], record[2], record[3], record[4], Integer.parseInt(record[5]),
                            Double.parseDouble(record[6]), Double.parseDouble(record[7]), Double.parseDouble(record[8]),
                            Integer.parseInt(record[9]), Double.parseDouble(record[10]), Double.parseDouble(record[11]),
                            Double.parseDouble(record[12]), Double.parseDouble(record[13]), Double.parseDouble(record[14]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            // Задача 1: Построить график по среднему количеству студентов в 10 странах
            Map<String, Integer> avgStudentsData = Database.getAverageStudentsByCountry();
            SwingUtilities.invokeLater(() -> {
                Graph graph = new Graph(avgStudentsData, "Среднее количество студентов", "Страна", "Среднее количество студентов");
                graph.setVisible(true);
            });

            // Задача 2: Вывести среднее количество расходов
            double averageExpenditure = Database.getAverageExpenditureForCounties();
            System.out.println("Среднее количество расходов в Fresno, Contra Costa, El Dorado и Glenn: " + averageExpenditure);

            // Задача 3: Вывести учебное заведение с максимальным баллом по математике
            School topSchoolLow = Database.getTopMathSchoolLow();
            School topSchoolHigh = Database.getTopMathSchoolHigh();
            if (topSchoolLow != null & topSchoolHigh!=null) {
                System.out.println("Школа с самым высоким баллом по математике с количеством студентов равному от 5000 до 7500: " + topSchoolLow);
                System.out.println("Школа с самым высоким баллом по математикес количеством студентов равному от 10000 до 11000: " + topSchoolHigh);
            } else {
                System.out.println("Нет школ, подходящих под заданные критерии.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрытие подключения к базе данных
            Database.closeDB();
        }
    }
}
