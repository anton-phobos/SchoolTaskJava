package org.example;

import lombok.Getter;
import lombok.Setter;

public class School {
    @Setter
    @Getter
    private String district;
    @Getter
    private String schoolName;
    @Getter
    private String county;
    @Getter
    private String grades;
    @Getter
    private int students;
    @Getter
    private double teachers;
    @Getter
    private double calworks;
    @Getter
    private double lunch;
    @Getter
    private int computer;
    @Getter
    private double expenditure;
    @Getter
    private double income;
    @Getter
    private double english;
    @Getter
    private double read;
    @Getter
    private double math;

    public School(String district, String schoolName, String county, String grades, int students, double teachers,
                  double calworks, double lunch, int computer, double expenditure, double income,
                  double english, double read, double math) {
        this.district = district;
        this.schoolName = schoolName;
        this.county = county;
        this.grades = grades;
        this.students = students;
        this.teachers = teachers;
        this.calworks = calworks;
        this.lunch = lunch;
        this.computer = computer;
        this.expenditure = expenditure;
        this.income = income;
        this.english = english;
        this.read = read;
        this.math = math;
    }

    public void setStudents(int students) {
        if (students > 0) {
            this.students = students;
        } else {
            System.out.println("Количество студентов должно быть положительным.");
        }
    }

    public void printInfo() {
        System.out.println("Округ: " + this.district);
        System.out.println("Школа: " + this.schoolName);
        System.out.println("Графство: " + this.county);
        System.out.println("Классы: " + this.grades);
        System.out.println("Студенты: " + this.students);
        System.out.println("Учителя: " + this.teachers);
        System.out.println("Calworks: " + this.calworks + "%");
        System.out.println("Льготные обеды: " + this.lunch + "%");
        System.out.println("Компьютеры: " + this.computer);
        System.out.println("Расходы: " + this.expenditure + "$");
        System.out.println("Доход: " + this.income + "$");
        System.out.println("Уровень английского: " + this.english + "%");
        System.out.println("Чтение: " + this.read);
        System.out.println("Математика: " + this.math);
    }

    @Override
    public String toString() {
        return "Округ: " + this.district + "/ Школа: " + this.schoolName + "/ Графство: " + this.county +
                "/ Классы: " + this.grades + "/ Студенты: " + this.students + "/ Учителя: " + this.teachers +
                "/ Calworks: " + this.calworks + "% / Льготные обеды: " + this.lunch + "% / Компьютеры: " + this.computer +
                "/ Расходы: " + this.expenditure + "$ / Доход: " + this.income + "$ / Английский: " + this.english + "% /" +
                " Чтение: " + this.read + "/ Математика: " + this.math;
    }
}
