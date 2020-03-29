import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Liang
 * @date 2020/3/26 11:20
 */
public class VipManager {

    public static <object> void main(String[] args) {
        while (true) {
            System.out.println("----------------------学生会员系统----------------------");
            System.out.println("1: 会员查询  2: 会员注册  3: 查询会员详情  4: 修改会员信息 5: 删除会员  6: 退出");
            System.out.println("请选择操作序号:");
            Scanner scanner = new Scanner(System.in);
            int operation = scanner.nextInt();
            //System.out.println("操作符为:" + operation);
            //判助操作符是那种类型
            switch (operation) {
                case 1:
                    //会员查询
                    System.out.println("全部会员信息如下: ");
                    List<Map<String, Object>> selectStuList = VipManager.selectStuList();
                    System.out.println(selectStuList);
                    break;
                case 2:
                    //会员注册
                    System.out.println("请输入用户的姓名:");
                    Scanner scanner1 = new Scanner(System.in);
                    String name = scanner1.nextLine();
                    System.out.println("请输入用户的年龄:");
                    Scanner scanner2 = new Scanner(System.in);
                    int age = scanner2.nextInt();
                    System.out.println("请输入用户的性别: ");
                    Scanner scanner3 = new Scanner(System.in);
                    String sex = scanner3.nextLine();
                    //入参,需要跟sql语句的中的插入顺序保持一致
                    Object[] param = new Object[]{name, age, sex};
                    int result = VipManager.saveStu(param);
                    if (result == 1) {
                        System.out.println("注册成功! ");
                    } else {
                        System.out.println("注册失败! ");
                    }
                    break;
                case 3:
                    //查询会员详情
                    /*指定用户:根绝表中的主键查询
                     *输入一个值。 (用户id)去数据库查询。如果有就展示会员信息，否则就提示:此用户不存在
                     */

                    System.out.println("请输入要查询会员的id：");
                    Scanner scanner4 = new Scanner(System.in);
                    int id = scanner4.nextInt();
                    List<Map<String, Object>> stuById = VipManager.findStuList(id);
                    if(stuById!=null && stuById.size()>0){
                        System.out.println(stuById);
                    }else {
                        System.out.println("此用户不存在!");
                    }
                    break;
                case 4:
                    //修改会员信息
                    System.out.println("请输入要修改会员的id：");
                    Scanner scanner6 = new Scanner(System.in);
                    int upId = scanner6.nextInt();
                    List<Map<String, Object>> stuId = VipManager.findStuList(upId);
                    if(stuId!=null && stuId.size()>0){
                        System.out.println(stuId);
                        System.out.println("请修改用户的姓名:");
                        Scanner scanner7 = new Scanner(System.in);
                        String upName = scanner7.nextLine();

                        System.out.println("请修改用户的年龄:");
                        Scanner scanner8 = new Scanner(System.in);
                        int upAge = scanner8.nextInt();

                        System.out.println("请修改用户的性别: ");
                        Scanner scanner9 = new Scanner(System.in);
                        String upSex = scanner9.nextLine();

                        //入参,需要跟sql语句的中的插入顺序保持一致
                        Object[] upStu = new Object[]{upName, upAge, upSex, upId};
                        int updateNum = VipManager.updateStu(upStu);
                        if (updateNum == 1) {
                            System.out.println("修改成功! ");
                        } else {
                            System.out.println("修改失败! ");
                        }
                    }else {
                        System.out.println("此用户不存在!");
                    }
                    break;
                case 5:
                    //删除会员


                    System.out.println("请输入要删除会员的id：");
                    Scanner scanner5 = new Scanner(System.in);
                    int delStuId = scanner5.nextInt();
                    int delStu = VipManager.deleteStuList(delStuId);
                    if (delStu == 1) {
                        System.out.println("删除成功! ");
                    } else {
                        System.out.println("删除失败! ");
                    }
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("输入的操作的序号不存在");
                    break;

            }
        }
    }


    //查询全部会员信息
    public static List<Map<String, Object>> selectStuList() {
        String sql = "SELECT sid,sname,sage,ssex from student_2";
        //查询全部会员信息无需传参
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql,null);
        return maps;
    }

    //会员注册
    public static int saveStu(Object[] args) {
        String sql = "insert into student_2(sname,sage,ssex) values(?,?,?)";
        int updateNum = MysqlUtil.updateMethod(sql, args);
        return updateNum;
    }

    //查询会员详情
    public static List<Map<String, Object>> findStuList(int id) {
        String sql = "SELECT sid,sname,sage,ssex from student_2 where sid = ?";
        Object[] objects = {id};
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql,objects);
        return maps;
    }

    //删除会员信息
    public static int deleteStuList(int id) {
        String sql = "delete from student_2 where sid = ?";
        Object[] objects = {id};
        int deleteNum = MysqlUtil.updateMethod(sql,objects);
        return deleteNum;
    }
    //修改会员信息
    public static int updateStu(Object[] args) {
        String sql = "update student_2 set sname=?,sage=?,ssex=? where sid = ?";
        int upNum = MysqlUtil.updateMethod(sql, args);
        return upNum;
    }
}