package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Connection connection;
    private static Statement statement;

    public static void connectDB() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/schools.db");
        statement = connection.createStatement();
    }

    public static void createTable() throws SQLException {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS school (
                    id INTEGER PRIMARY KEY,
                    district TEXT NOT NULL,
                    school TEXT NOT NULL,
                    county TEXT NOT NULL,
                    grades TEXT NOT NULL,
                    students INTEGER,
                    teachers REAL,
                    calworks REAL,
                    lunch REAL,
                    computer INTEGER,
                    expenditure REAL,
                    income REAL,
                    english REAL,
                    read REAL,
                    math REAL
                )
                """;
        statement.execute(createTableSQL);
    }

    public static void addSchoolData(String id, String district, String school, String county, String grades,
                                     int students, double teachers, double calworks, double lunch,
                                     int computer, double expenditure, double income,
                                     double english, double read, double math) throws SQLException {

        // Проверяем, существует ли уже запись с таким ID
        String checkExistenceQuery = "SELECT COUNT(*) FROM school WHERE id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkExistenceQuery)) {
            checkStmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Запись с ID " + id + " уже существует. Пропускаем вставку.");
                return; // Пропускаем вставку, если запись с таким ID уже существует
            }
        }

        // Если записи с таким ID нет, выполняем вставку
        String insertSQL = "INSERT INTO school (id, district, school, county, grades, students, teachers, calworks, " +
                "lunch, computer, expenditure, income, english, read, math) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, district);
            preparedStatement.setString(3, school);
            preparedStatement.setString(4, county);
            preparedStatement.setString(5, grades);
            preparedStatement.setInt(6, students);
            preparedStatement.setDouble(7, teachers);
            preparedStatement.setDouble(8, calworks);
            preparedStatement.setDouble(9, lunch);
            preparedStatement.setInt(10, computer);
            preparedStatement.setDouble(11, expenditure);
            preparedStatement.setDouble(12, income);
            preparedStatement.setDouble(13, english);
            preparedStatement.setDouble(14, read);
            preparedStatement.setDouble(15, math);

            preparedStatement.executeUpdate();
        }
    }


    // Задача 1: Среднее количество студентов в 10 странах
    public static Map<String, Integer> getAverageStudentsByCountry() throws SQLException {
        String query = "SELECT county, AVG(students) as avg_students FROM school GROUP BY county LIMIT 10";
        Map<String, Integer> data = new HashMap<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String county = resultSet.getString("county");
                int avgStudents = resultSet.getInt("avg_students");
                data.put(county, avgStudents);
            }
        }
        return data;
    }

    // Задача 2: Среднее количество расходов для указанных округов
    public static double getAverageExpenditureForCounties() throws SQLException {
        String query = "SELECT AVG(expenditure) FROM school WHERE county IN ('Fresno', 'Contra Costa', 'El Dorado', 'Glenn') AND expenditure > 10";
        double averageExpenditure = 0;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                averageExpenditure = resultSet.getDouble(1);
            }
        }
        return averageExpenditure;
    }

    // Задача 3: Учебные заведения с количеством студентов от 5000 до 7500 и от 10000 до 11000 с максимальным баллом по математике
    public static School getTopMathSchoolHigh() throws SQLException {
        String query = """
            SELECT district, school, county, grades, students, teachers, calworks, lunch, computer, expenditure, 
                   income, english, read, math 
            FROM school
            WHERE (students BETWEEN 10000 AND 11000)
            ORDER BY math DESC
            LIMIT 1
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                // Создаем объект School из данных запроса
                return new School(
                        resultSet.getString("district"),
                        resultSet.getString("school"),
                        resultSet.getString("county"),
                        resultSet.getString("grades"),
                        resultSet.getInt("students"),
                        resultSet.getDouble("teachers"),
                        resultSet.getDouble("calworks"),
                        resultSet.getDouble("lunch"),
                        resultSet.getInt("computer"),
                        resultSet.getDouble("expenditure"),
                        resultSet.getDouble("income"),
                        resultSet.getDouble("english"),
                        resultSet.getDouble("read"),
                        resultSet.getDouble("math")
                );
            }
        }
        return null; // Если подходящая школа не найдена
    }
    public static School getTopMathSchoolLow() throws SQLException {
        String query = """
            SELECT district, school, county, grades, students, teachers, calworks, lunch, computer, expenditure, 
                   income, english, read, math 
            FROM school
            WHERE (students BETWEEN 5000 AND 7500)
            ORDER BY math DESC
            LIMIT 1
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                // Создаем объект School из данных запроса
                return new School(
                        resultSet.getString("district"),
                        resultSet.getString("school"),
                        resultSet.getString("county"),
                        resultSet.getString("grades"),
                        resultSet.getInt("students"),
                        resultSet.getDouble("teachers"),
                        resultSet.getDouble("calworks"),
                        resultSet.getDouble("lunch"),
                        resultSet.getInt("computer"),
                        resultSet.getDouble("expenditure"),
                        resultSet.getDouble("income"),
                        resultSet.getDouble("english"),
                        resultSet.getDouble("read"),
                        resultSet.getDouble("math")
                );
            }
        }
        return null; // Если подходящая школа не найдена
    }


    public static ArrayList<String> getAllSchools() throws SQLException {
        ArrayList<String> schools = new ArrayList<>();
        String query = "SELECT school FROM school";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                schools.add(resultSet.getString("school"));
            }
        }
        return schools;
    }

    public static void closeDB() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println("Ошибка закрытия базы данных: " + e.getMessage());
        }
    }

    public static void clearTable() throws SQLException {
        String clearSQL = "DELETE FROM school";
        statement.execute(clearSQL);
    }
}
