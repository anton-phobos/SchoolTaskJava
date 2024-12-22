package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Graph extends JFrame {
    private final int DEFAULT_PADDING = 15;

    public Graph(Map<String, Integer> map, String chartTitle, String xLabel, String yLabel) {
        init(map, chartTitle, xLabel, yLabel);
    }

    private void init(Map<String, Integer> map, String chartTitle, String xLabel, String yLabel) {
        CategoryDataset dataset = createDataset(map);
        JFreeChart chart = createChart(dataset, chartTitle, xLabel, yLabel);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel);
        pack();
        setTitle(chartTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFreeChart createChart(CategoryDataset dataset, String chartTitle, String xLabel, String yLabel) {
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,  // Заголовок графика
                xLabel,      // Подпись оси X
                yLabel,      // Подпись оси Y
                dataset      // Данные для графика
        );
        return chart;
    }

    private CategoryDataset createDataset(Map<String, Integer> map) {
        var dataset = new DefaultCategoryDataset();
        map.forEach((key, value) -> dataset.setValue(value, "Количество студентов", key));
        return dataset;
    }
}
