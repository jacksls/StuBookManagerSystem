import java.util.ArrayList;
import java.util.List;

/**
 * @author Liang
 * @date 2020/3/24 17:09
 */
public class Student {

    String sNO;
    String sName;
    String sSex;
    int sAge;
    double sJava;

/*    @Override
    public String toString() {
        return  "学号为" + sNO + ", 姓名：'" + sName + ", 性别：" + sSex + ", 年龄：" + sAge + ", Java语言成绩：" + sJava +"\n";
    }*/

    @Override
    public String toString() {
        return "Student{" +
                "sNO='" + sNO + '\'' +
                ", sName='" + sName + '\'' +
                ", sSex='" + sSex + '\'' +
                ", sAge=" + sAge +
                ", sJava=" + sJava +
                '}';
    }

    /*
            定义一个表示学生信息的类Student，要求如下：
            （1）类Student的成员变量：
            sNO　表示学号；sName表示姓名；sSex表示性别；
            sAge表示年龄；sJava：表示Java课程成绩。
            （2）类Student带参数的构造方法：
            在构造方法中通过形参完成对成员变量的赋值操作。
            （3）类Student的方法成员：
            getNo（）：获得学号；
            getName（）：获得姓名；
            getSex（）：获得性别；
            getAge（）获得年龄；
            getJava（）：获得Java 课程成绩
            (4)根据类Student的定义，创建五个该类的对象，
            输出每个学生的信息，计算并输出这五个学生Java语言成绩的平均值，
            以及计算并输出他们Java语言成绩的最大值和最小值。
            */
public static void main(String[] args) {

    double min=100 , max=0 , sum=0;

    Student[] student = new Student[6];
    student[0] = new Student("11","张三","男",20,88.0);
    student[1] = new Student("12","李四","女",19,92.0);
    student[2] = new Student("13","王五","男",21,83.0);
    student[3] = new Student("14","陈六","女",20,75.0);
    student[4] = new Student("15","黄七","男",19,62.0);


    List<Student> list=new ArrayList<>();
    for(int i = 0 ; i < 5 ; i++) {
        list.add(student[i]);
    }
    System.out.println(list+"\n他们Java语言成绩的平均值为："+list.stream().mapToDouble(Student::getsJava).average().getAsDouble()+
            "\n他们Java语言成绩的最大值为："+list.stream().mapToDouble(Student::getsJava).max().getAsDouble()+
            "\n他们Java语言成绩的最小值为："+list.stream().mapToDouble(Student::getsJava).min().getAsDouble());



    //法二
/*    for(int i=1 ; i <= 5 ; i++) {
            System.out.println("学号："+student[i].getsNO()+
                                "，姓名：" +student[i].getsName()+
                                "，性别："+student[i].getsSex()+
                                "，年龄："+student[i].getsAge()+
                                "，Java语言成绩："+student[i].getsJava());
        sum += student[i].getsJava();
        max = Math.max(student[i].getsJava(),max);
        min = Math.min(student[i].getsJava(),min);
    }
        System.out.println("他们Java语言成绩的平均值为："+sum/5+
                           "\n他们Java语言成绩的最大值为："+max+
                           "\n他们Java语言成绩的最小值为："+min);*/
}

    public Student(String sNO, String sName, String sSex, int sAge, double sJava) {
        this.sNO = sNO;
        this.sName = sName;
        this.sSex = sSex;
        this.sAge = sAge;
        this.sJava = sJava;
    }

    public String getsNO() {
        return sNO;
    }

    public String getsName() {
        return sName;
    }

    public String getsSex() {
        return sSex;
    }

    public Integer getsAge() {
        return sAge;
    }

    public double getsJava() {
        return sJava;
    }
}
