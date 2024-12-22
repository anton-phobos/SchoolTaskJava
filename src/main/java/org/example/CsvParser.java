package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvParser {

    public static ArrayList<String[]> parseCsv(String filePath) {
        ArrayList<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // To skip header line

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");

                // Очищаем все значения от лишних кавычек и пробелов
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replace("\"", "").trim(); // Убираем кавычки и пробелы
                }

                // Добавляем очищенные данные в список
                records.add(values);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения CSV файла: " + e.getMessage());
        }

        return records;
    }
}
