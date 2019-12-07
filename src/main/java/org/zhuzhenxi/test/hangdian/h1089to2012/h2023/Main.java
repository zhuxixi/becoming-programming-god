package org.zhuzhenxi.test.hangdian.h1089to2012.h2023;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            //n个学生
            int n = in.nextInt();
            //每个学生m门课
            int m = in.nextInt();
            //第几个学生
            int studentIndex = 1;
            //初始化班级
            Clazz clazz = new Clazz(n, m);
            while (studentIndex < n + 1 && in.hasNext()) {
                Student student = new Student(studentIndex);
                for (int i = 1; i < m + 1; i++) {
                    int score = in.nextInt();
                    Course course = new Course(i, score);
                    student.getAllClazz().put(i, course);
                }
                student.calculatePersonalAverageScore();
                clazz.addStudent(student);
                studentIndex = studentIndex+1;
            }
            clazz.calculateAverage();
            clazz.calculateWhosNoShamed();
            //输出学生平均成绩
            for (int i = 0; i < clazz.getStudents().size(); i++) {
                System.out.printf("%.2f",clazz.getStudents().get(i).getPersonalAverageScore());
                if (i+1==clazz.getStudents().size()){
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();
            for (int i = 1; i < clazz.averages.size()+1; i++) {
                System.out.printf("%.2f",clazz.averages.get(i));
                if (i+1==clazz.averages.size()+1){
                    continue;
                }
                System.out.print(" ");
            }
            System.out.println();
            System.out.println(clazz.noShamedStudentNums);
            System.out.println();
        }
    }

    /**
     * 班级
     * 保存学生的引用，所有学生的成绩(按照课程编号分类)
     *
     */
    private static class Clazz {
        //学生数
        private int studentNum = 0;
        //所有学生
        private List<Student> students = new ArrayList<>();
        //所有学生的成绩，按照课程编号分类
        private Map<Integer, List<Course>> all = new HashMap<>();
        //每个课程编号的平均分
        private Map<Integer, Double> averages = new HashMap<>();
        //没给班里拖后腿的学生数
        private int noShamedStudentNums = 0;

        /**
         * 先把课程编号和成绩集合初始化
         * @param studentNum1 学生数量
         * @param courseNum1 课程数量
         */
        public Clazz(int studentNum1, int courseNum1) {
            studentNum = studentNum1;
            for (int i = 1; i < courseNum1 + 1; i++) {
                all.put(i, new ArrayList<>());
            }
        }
        /**
         * 班级添加学生的时候讲学生课程放到全班成绩中
         * 再把这个学生的成绩扔进all里面
         * @param student 当前从控制台读取的学生，包含学生成绩的引用
         */
        public void addStudent(Student student){
            students.add(student);
            for (Map.Entry<Integer,Course> entry:student.getAllClazz().entrySet()){
                all.get(entry.getKey()).add(entry.getValue());
            }
        }

        /**
         * 计算平均值
         * 遍历all,每个编号里面的多个成绩取平均值，再放到average
         */
        public void calculateAverage() {
            for (Map.Entry<Integer, List<Course>> entry : all.entrySet()) {
                List<Course> courses = entry.getValue();
                double scores = 0;
                for (Course course : courses) {
                    scores += course.getScore();
                }
                double average = scores / courses.size();
                averages.put(entry.getKey(), average);
            }
        }

        /**
         * 计算没给班里拖后腿的学生数，所有科目分数都高于平均值
         */
        public void calculateWhosNoShamed(){
            for (Student student:students){
                //默认丢脸
                boolean shamed = false;
                for (Map.Entry<Integer,Course> entry:student.allClazz.entrySet()){
                    double averageScore = averages.get(entry.getKey());
                    if (entry.getValue().score<averageScore){
                        shamed = true;
                    }
                }
                if (shamed){
                    continue;
                }
                noShamedStudentNums +=1;
            }
        }

        public List<Student> getStudents() {
            return students;
        }

    }

    private static class Student {
        private int studentIndex = 1;
        private Map<Integer, Course> allClazz = new HashMap<>();
        private double personalAverageScore = 0;

        public Student(int studentIndex1) {
            studentIndex = studentIndex1;
        }

        public void calculatePersonalAverageScore(){
            double sumScore = allClazz.values().stream().collect(Collectors.summingInt(Course::getScore));
            personalAverageScore = sumScore/allClazz.size();
        }
        public Map<Integer, Course> getAllClazz() {
            return allClazz;
        }

        public double getPersonalAverageScore() {
            return personalAverageScore;
        }

    }

    private static class Course {
        //课程序号
        private int index;
        //课程分数
        private int score;

        public Course(int index1, int score1) {
            index = index1;
            score = score1;
        }

        public int getScore() {
            return score;
        }

    }
}
