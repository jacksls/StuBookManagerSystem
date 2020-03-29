import java.util.*;

import static javax.swing.UIManager.getInt;

/**
 * @author Liang
 * @date 2020/3/27 9:59
 */
public class StuMemberBook {
    /**
     * 会员表：记录学生的信息
     * 图书表：记录一些书本信息
     * 会员和书关系表： 会员可以借书和还书
     * <p>
     * 1：会员：
     * 会员登陆：只有登录以后才能进行操作：
     * 1：会员查询(查询会员的全部数据)
     * 2：会员的注册
     * 3：查询会员详情（只能该会员自己的信息）
     * 4：修改会员信息（只能修改自己）
     * 5：删除会员
     * 6：查询自己名下借书情况
     * 书城：
     * 1：图书的查询
     * 2：图书的新增
     * 3：图书的修改
     * 4：图书的删除
     * 5：借书（当前登陆的用户借书）
     * 6：还书（当前登陆的用户还书）
     */


    private static int ID;
    private static String STU_NAME;
    private static String STU_NUM;
    private static String STU_PWD;
    private static int STU_AGE;
    private static String ROOT = "root";
    private static String PASSWORD = "root";


    public static <object> void main(String[] args) {
        //学生书城会员系统
        studentBookMemberSystem();

    }

    public static void studentBookMemberSystem() {
        while (true) {
            try {
                System.out.println("----------------------学生书城会员系统----------------------");
                System.out.println("1: 会员登录  2: 会员注册  3: 退出\n");
                System.out.println("请选择操作序号:");
                Scanner scanner = new Scanner(System.in);
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1:
                        //会员登录
                        System.out.println("请输入学号:");
                        Scanner scanner1 = new Scanner(System.in);
                        String num = scanner1.nextLine();
                        List<Map<String, Object>> stuByNum = StuMemberBook.stuLogin(num);
                        if (stuByNum != null && stuByNum.size() > 0) {
                            System.out.println("请输入密码:");
                            Scanner scanner2 = new Scanner(System.in);
                            String pwd = scanner2.nextLine();

                            ID = (int) stuByNum.get(0).get("id");
                            STU_NUM = stuByNum.get(0).get("stu_num").toString();
                            STU_PWD = stuByNum.get(0).get("stu_pwd").toString();
                            STU_NAME = stuByNum.get(0).get("stu_name").toString();
                            STU_AGE = (int) stuByNum.get(0).get("stu_age");

                            if (STU_NUM.equals(num) && STU_PWD.equals(pwd)) {
                                System.out.println("登录成功！\n");
                                stuVipManager();
                            } else {
                                System.out.println("您输入的密码错误！\n");
                            }
                        } else {
                            System.out.println("此用户不存在！请注册账号！\n");
                        }
                        break;
                    case 2:
                        //会员注册
                        signUp();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("不存在此操作序号有误！请重新输入！\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("您输入有误！\n");
                continue;
            }
        }
    }


    public static void stuVipManager() {
        /*1：会员查询(查询会员的全部数据)
        2：会员的注册
        3：查询会员详情（只能该会员自己的信息）
        4：修改会员信息（只能修改自己）
        5：删除会员
        6：查询自己名下借书情况*/
        while (true) {
            try {
                System.out.println("---------------------------------学生会员系统---------------------------------");
                System.out.println("1: 查询所有会员  2: 会员注册  3: 查询该会员信息  4: 修改该会员信息  5: 删除会员  6: 查询借书情况  7: 返回上级  8: 退出\n");
                System.out.println("请选择操作序号:");
                Scanner scanner3 = new Scanner(System.in);
                int operation = scanner3.nextInt();
                //判助操作符是那种类型
                switch (operation) {
                    case 1:
                        //会员查询
                        System.out.println("所有全部会员信息如下: ");
                        List<Map<String, Object>> selectStuList = StuMemberBook.selectStuList();
                        System.out.println(selectStuList);
                        break;
                    case 2:
                        //会员注册
                        signUp();
                        break;
                    case 3:
                        //查询会员详情
                        // 指定用户:根绝表中的主键查询
                        //*输入一个值。 (用户id)去数据库查询。如果有就展示会员信息，否则就提示:此用户不存在

                        queryMemberDetails();

                        break;
                    case 4:
                        //修改会员信息
                        System.out.print("\n会员信息如下：");
                        queryMemberDetails();
                        boolean isWrong = true;
                        do {
                            //输入密码...
                            System.out.print("请输入当前密码:");
                            Scanner scanner4 = new Scanner(System.in);
                            String pwd = scanner4.nextLine();
                            if (STU_PWD.equals(pwd)) {
                                isWrong = false;
                            } else {
                                System.out.println("您输入的密码错误！请重新输入！\n");
                                break;
                            }
                        } while (isWrong);
                        System.out.println("请修改姓名:");
                        Scanner scanner5 = new Scanner(System.in);
                        String upName = scanner5.nextLine();

                        System.out.println("请修改年龄: ");
                        Scanner scanner6 = new Scanner(System.in);
                        int upAge = scanner6.nextInt();

                        System.out.println("请修改学号:");
                        Scanner scanner7 = new Scanner(System.in);
                        String upNum = scanner7.nextLine();

                        System.out.println("请修改密码: ");
                        Scanner scanner8 = new Scanner(System.in);
                        String upPwd = scanner8.nextLine();

                        //入参,需要跟sql语句的中的插入顺序保持一致
                        Object[] upStu = new Object[]{upName, upAge, upNum, upPwd, ID};
                        int updateNum = StuMemberBook.updateStu(upStu);
                        if (updateNum == 1) {
                            System.out.println("修改成功! ");
                        } else {
                            System.out.println("修改失败! ");
                        }
                        break;
                    case 5:
                        //删除会员
                        //验证超级管理员
                        compareRoot();
                        //删除操作
                        boolean isWrong2 = true;
                        int delStuId = 0;
                        do {
                            try {
                                System.out.println("请输入要删除的会员id：");
                                Scanner scanner9 = new Scanner(System.in);
                                delStuId = scanner9.nextInt();
                                List<Map<String, Object>> deleteStuId = StuMemberBook.findStuList(delStuId);
                                if (deleteStuId != null && deleteStuId.size() > 0) {
                                    isWrong2 = false;
                                    String stu_name = deleteStuId.get(0).get("stu_name").toString();
                                    String stu_num = deleteStuId.get(0).get("stu_num").toString();
                                    int stu_age = (int) deleteStuId.get(0).get("stu_age");
                                    System.out.println("\n你所输入的会员信息如下：");
                                    //取出结果集中每行的值
                                    System.out.println("学生id：" + delStuId + "  学生姓名：" + stu_name + "  学生学号：" + stu_num + "  学生年龄：" + stu_age + "\n");
                                } else {
                                    System.out.println("你输入的会员id不存在！请重新输入！\n");
                                }
                            } catch (Exception e) {
                                System.out.println("你输入的会员id有误！只能输入数字！\n");
                            }
                        } while (isWrong2);


                        //查询单个会员借书详情
                        List<Map<String, Object>> stuBookId = StuMemberBook.findStuOwnBook(delStuId);
                        if (stuBookId != null && stuBookId.size() > 0) {
                            System.out.print("未归还图书名称：");
                            for (int i = 0; i < stuBookId.size(); i++) {
                                String bookStuName = stuBookId.get(i).get("book_name").toString();
                                //取出结果集中每行的值
                                System.out.print(bookStuName+"  ");
                            }
                            System.out.println("\n查询到该学生存在以上图书未归还，故会员信息无法删除！\n");
                            break;
                        }else {
                            System.out.println("确定删除（Y/N）：");
                            Scanner scanner = new Scanner(System.in);
                            String yes = scanner.nextLine();
                            if(yes.equals("Y") || yes.equals("y")){
                                int delStu = StuMemberBook.deleteStuList(delStuId);
                                if (delStu == 1) {
                                    System.out.println("删除成功! ");
                                } else {
                                    System.out.println("删除失败! ");
                                }
                                break;
                            }else if (yes.equals("N") || yes.equals("n")){
                                System.out.println("操作已取消！ 返回上一级！\n");
                                break;
                            }else{
                                System.out.println("指令输入错误！ 返回上一级！\n");
                                break;
                            }
                        }
                    case 6:
                        //查询该会员借书情况
                        /*书城：
                        1：图书的查询
                        2：图书的新增
                        3：图书的修改
                        5：借书（当前登陆的用户借书）
                        6：还书（当前登陆的用户还书）*/
                        studentBookSystem();
                        break;
                    case 7:
                        //返回上级
                        studentBookMemberSystem();
                    case 8:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("不存在此操作序号有误！请重新输入！\n");
                        break;

                }
            } catch (Exception e) {
                System.out.println("您输入有误！\n");
                continue;
            }
        }
    }

    public static void studentBookSystem() {
        /*书城：
        1：图书的查询
        2：图书的新增
        3：图书的修改
        5：借书（当前登陆的用户借书）
        6：还书（当前登陆的用户还书）*/
        while (true) {
            try {
                System.out.println("---------------------------------学生书城系统---------------------------------");
                System.out.println("1: 查询图书  2: 新增图书  3: 修改图书  4: 删除图书  5: 借书  6: 还书  7: 返回上级  8: 退出\n");
                System.out.print("请选择操作序号:");
                Scanner scanner10 = new Scanner(System.in);
                int serialNumber = scanner10.nextInt();
                switch (serialNumber) {
                    case 1:
                        //查询图书
                        System.out.println("所有图书信息如下: ");
                        List<Map<String, Object>> books = StuMemberBook.findBooks();
                        System.out.println(books);
                        break;
                    case 2:
                        //新增图书
                        //验证超级管理员
                        compareRoot();
                        complyAddBook();
                        break;
                    case 3:
                        //修改图书
                        //验证超级管理员
                        compareRoot();
                        //查询单本图书详情
                        boolean wrong = true;
                        int book_id = 0;
                        do {
                            try {
                                System.out.println("请输入需要修改的图书id");
                                Scanner scanner17 = new Scanner(System.in);
                                book_id = scanner17.nextInt();
                                List<Map<String, Object>> bookId = StuMemberBook.findBook(book_id);
                                if (bookId != null && bookId.size() > 0) {
                                    wrong = false;
                                    String bookName = bookId.get(0).get("book_name").toString();
                                    float bookPrice = (float) bookId.get(0).get("book_price");
                                    String bookAuthor = bookId.get(0).get("book_author").toString();
                                    String bookPublish = bookId.get(0).get("book_publish").toString();
                                    System.out.print("\n你所输入的图书信息如下：");
                                    //取出结果集中每行的值
                                    System.out.println("图书id：" + book_id + "  书名：" + bookName + "  作者：" + bookAuthor + "  价格：" + bookPrice + "  出版社：" + bookPublish + "\n");
                                } else {
                                    System.out.println("你输入的图书id不存在！请重新输入！\n");
                                }
                            } catch (Exception e) {
                                System.out.println("你输入的图书id有误！只能输入数字！\n");
                            }
                        } while (wrong);
                        //修改图书信息
                        System.out.println("请输入新书名:");
                        Scanner scanner13 = new Scanner(System.in);
                        String book_name = scanner13.nextLine();
                        System.out.println("请输入新作者:");
                        Scanner scanner14 = new Scanner(System.in);
                        String book_author = scanner14.nextLine();
                        System.out.println("请输入新价格: ");
                        Scanner scanner15 = new Scanner(System.in);
                        float book_price = scanner15.nextFloat();
                        System.out.println("请输入新出版社名称: ");
                        Scanner scanner16 = new Scanner(System.in);
                        String book_publish = scanner16.nextLine();

                        //入参,需要跟sql语句的中的插入顺序保持一致
                        Object[] param = new Object[]{book_name, book_price, book_author, book_publish, book_id};
                        int result = StuMemberBook.updateBook(param);
                        if (result == 1) {
                            System.out.println("修改成功! ");
                        } else {
                            System.out.println("修改失败! ");
                        }
                        break;
                    case 4:
                        //删除图书
                        //验证超级管理员
                        compareRoot();
                        //打印图书信息
                        boolean isWrong1 = true;
                        int delBookId = 0;
                        do {
                            try {
                                System.out.println("请输入要删除的图书id：");
                                Scanner scanner18 = new Scanner(System.in);
                                delBookId = scanner18.nextInt();
                                List<Map<String, Object>> bookId = StuMemberBook.findBook(delBookId);
                                if (bookId != null && bookId.size() > 0) {
                                    isWrong1 = false;
                                    String bookName = bookId.get(0).get("book_name").toString();
                                    float bookPrice = (float) bookId.get(0).get("book_price");
                                    String bookAuthor = bookId.get(0).get("bookAuthor").toString();
                                    String bookPublish = bookId.get(0).get("book_publish").toString();
                                    System.out.println("需要删除的图书信息如下: ");
                                    //取出结果集中每行的值
                                    System.out.println("图书id：" + delBookId + "  书名：" + bookName + "  作者：" + bookAuthor + "  价格：" + bookPrice + "  出版社：" + bookPublish + "\n");
                                } else {
                                    System.out.println("你输入的图书不存在！请重新输入！\n");
                                }
                            } catch (Exception e) {
                                System.out.println("你输入的图书id有误！只能输入数字！\n");
                            }
                        } while (isWrong1);
                        //删除图书
                        System.out.println("确定删除（Y/N）：");
                        Scanner scanner = new Scanner(System.in);
                        String yes = scanner.nextLine();
                        if(yes.equals("Y") || yes.equals("y")){
                            int delBookNum = StuMemberBook.deleteBook(delBookId);
                            if (delBookNum == 1) {
                                System.out.println("删除成功! \n");
                            } else {
                                System.out.println("删除失败! \n");
                            }
                            break;
                        }else if (yes.equals("N") || yes.equals("n")){
                            System.out.println("操作已取消！ 返回上一级！\n");
                            break;
                        }else{
                            System.out.println("指令输入错误！ 返回上一级！\n");
                            break;
                        }
                    case 5:
                        //借书
                        //已借图书详情
                        borrowedBookDetails();
                        //查询书城空余图书
                        System.out.println("\n书城空余图书信息如下: ");
                        List<Map<String, Object>> queryBook = StuMemberBook.queryBooks();
                        if (queryBook != null && queryBook.size() > 0) {
                            for (int i = 0; i < queryBook.size(); i++) {
                                String bookId = queryBook.get(i).get("id").toString();
                                String bookName = queryBook.get(i).get("book_name").toString();
                                String bookPrice = queryBook.get(i).get("book_price").toString();
                                String bookAuthor = queryBook.get(i).get("book_author").toString();
                                String bookPublish = queryBook.get(i).get("book_publish").toString();
                                //取出结果集中每行的值
                                System.out.println("图书id：" + bookId + "  书名：" + bookName + "  作者：" + bookAuthor + "  价格：" + bookPrice + "  出版社：" + bookPublish);
                            }
                            System.out.println();
                        }else {
                            System.out.println("\n抱歉，书城没有空余图书！\n");
                            break;
                        }
                        //借书
                        boolean isWrong3 = true;
                        boolean equal = false;
                        int borrowBookId;
                        do {
                            try {
                                System.out.println("请输入想借的图书id：");
                                Scanner scanner21 = new Scanner(System.in);
                                borrowBookId = scanner21.nextInt();
                                List<Map<String, Object>> queryBookId = StuMemberBook.queryBooks();

                                //创建一个集合来存空余图书的id号
                                List list = new ArrayList();
                                for (int i = 0; i < queryBookId.size(); i++) {
                                    int bookId = (int) queryBookId.get(i).get("id");
                                    list.add(bookId);
                                }

                                //查询输入的图书id是否为空余图书
                                for (int i = 0; i < list.size(); i++) {
                                    //int nowBorrowBookId = (int) list.get(i);
                                    if (list.get(i).equals(borrowBookId)) {
                                        equal = true;
                                    }
                                }

                                //如果为空余图书，则可以借书
                                if (equal == true) {
                                    Object[] borrowBook = new Object[]{ID, borrowBookId};
                                    int borrowBookResult = StuMemberBook.borrowBooks(borrowBook);
                                    if (borrowBookResult == 1) {
                                        isWrong3 = false;
                                        System.out.println("借书成功! ");
                                    } else {
                                        System.out.println("借书失败! ");
                                    }

                                } else {
                                    //如果输入id不是空余图书，则退出
                                    System.out.println("你输入的图书id不存在！请重新输入！\n");
                                }
                            } catch (Exception e) {
                                System.out.println("你输入的图书id有误！只能输入数字！请重新输入！\n");
                            }
                        } while (isWrong3);
                        break;
                    case 6:
                        //还书
                        //已借图书详情
                        borrowedBookDetails();
                        System.out.println();

                        //还书操作
                        boolean isWrong4 = true;
                        int returnBookId;
                        boolean whether = false;
                        do {
                            try {
                                System.out.println("请输入要退换的图书id：");
                                Scanner scanner22 = new Scanner(System.in);
                                returnBookId = scanner22.nextInt();
                                List<Map<String, Object>> reStuOwnBookId = StuMemberBook.findStuOwnBook(ID);

                                //创建一个集合来存已借图书的id号
                                List list = new ArrayList();
                                for (int i = 0; i < reStuOwnBookId.size(); i++) {
                                    int reBookId = (int) reStuOwnBookId.get(i).get("book_id");
                                    list.add(reBookId);
                                }

                                //查询输入的图书id是否为已借图书的id号
                                for (int i = 0; i < list.size(); i++) {
                                    //int nowReturnBookId = (int) list.get(i);
                                    if (list.get(i).equals(returnBookId)) {
                                        whether = true;
                                    }
                                }



                                //如果为已借图书的id号，则可以还书
                                if (whether == true) {
                                    //退还图书
                                    System.out.println("确定退还（Y/N）：");
                                    Scanner scanner23 = new Scanner(System.in);
                                    String isYes = scanner23.nextLine();
                                    if(isYes.equals("Y") || isYes.equals("y")){
                                        int returnBookNum = StuMemberBook.returnBook(returnBookId);
                                        if (returnBookNum == 1) {
                                            System.out.println("退还成功! \n");
                                        } else {
                                            System.out.println("退还失败! \n");
                                        }
                                        break;
                                    }else if (isYes.equals("N") || isYes.equals("n")){
                                        System.out.println("操作已取消！ 返回上一级！\n");
                                    }else{
                                        System.out.println("指令输入错误！ 返回上一级！\n");
                                    }
                                    break;
                                } else {
                                    //如果输入id不是已借图书的id号，则重新输入
                                    System.out.println("你输入的图书id不存在！请重新输入！\n");
                                }
                            } catch (Exception e) {
                                System.out.println("你输入的图书id有误！只能输入数字！请重新输入！\n");
                            }
                        } while (isWrong4);
                        break;
                    case 7:
                        //返回上级
                        stuVipManager();
                    case 8:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("不存在此操作序号有误！请重新输入！\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("您输入有误！\n");
                continue;
            }
        }
    }


    //验证超级管理员
    public static void compareRoot() {
        boolean isTrue = true;
        do {
            //输入密码...
            System.out.println("请输入超级管理员账号： ");
            Scanner scanner11 = new Scanner(System.in);
            String root = scanner11.nextLine();
            System.out.println("请输入超级管理员密码： ");
            Scanner scanner12 = new Scanner(System.in);
            String passwdord = scanner12.nextLine();
            if (root.equals(ROOT) || passwdord.equals(PASSWORD)) {
                isTrue = false;
            } else {
                System.out.println("您输入的密码错误！请重新输入！\n");
            }
        } while (isTrue);
        System.out.println("登录成功！\n");
    }

    //查询单个会员详情
    public static void queryMemberDetails() {
        List<Map<String, Object>> stuById = StuMemberBook.findStuList(ID);
        Map<String, Object> stuOwnId = stuById.get(0);
        ID = (int) stuOwnId.get("id");
        String stu_name = stuOwnId.get("stu_name").toString();
        String stu_num = stuOwnId.get("stu_num").toString();
        int stu_age = (int) stuOwnId.get("stu_age");
        //取出结果集中每行的值
        System.out.println("学生id：" + ID + "  学生姓名：" + stu_name + "  学生学号：" + stu_num + "  学生年龄：" + stu_age + "\n");
    }

    //会员登录
    public static List<Map<String, Object>> stuLogin(String num) {
        String sql = "SELECT id,stu_num,stu_pwd,stu_name,stu_age FROM stu WHERE  stu_num = ?";
        Object[] objects = {num};
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, objects);
        return maps;
    }

    //查询全部会员信息
    public static List<Map<String, Object>> selectStuList() {
        String sql = "SELECT id,stu_name,stu_num,stu_age FROM stu";
        //查询全部会员信息无需传参
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, null);
        return maps;
    }

    //会员注册
    public static int saveStu(Object[] args) {
        String sql = "INSERT INTO stu(stu_name,stu_num,stu_pwd,stu_age) VALUES(?,?,?,?)";
        int updateNum = MysqlUtil.updateMethod(sql, args);
        return updateNum;
    }

    //会员注册实现
    public static void signUp() {
        System.out.println("请输入姓名:");
        Scanner scanner3 = new Scanner(System.in);
        String stu_name = scanner3.nextLine();
        System.out.println("请输入学号:");
        Scanner scanner4 = new Scanner(System.in);
        String stu_num = scanner4.nextLine();
        System.out.println("请输入密码: ");
        Scanner scanner5 = new Scanner(System.in);
        String stu_pwd = scanner5.nextLine();
        System.out.println("请输入年龄: ");
        Scanner scanner6 = new Scanner(System.in);
        int stu_age = scanner6.nextInt();

        //入参,需要跟sql语句的中的插入顺序保持一致
        Object[] param = new Object[]{stu_name, stu_num, stu_pwd, stu_age};
        int result = StuMemberBook.saveStu(param);
        if (result == 1) {
            System.out.println("注册成功! \n");
        } else {
            System.out.println("注册失败! \n");
        }
    }

    //查询单个会员详情
    public static List<Map<String, Object>> findStuList(int id) {
        String sql = "SELECT id,stu_name,stu_num,stu_age FROM stu WHERE id = ?";
        Object[] objects = {id};
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, objects);
        return maps;
    }

    //修改会员信息
    public static int updateStu(Object[] args) {
        String sql = "UPDATE stu SET stu_name=?,stu_age=?,stu_num=?,stu_pwd=? WHERE id = ?";
        int upNum = MysqlUtil.updateMethod(sql, args);
        return upNum;
    }

    //删除会员信息
    public static int deleteStuList(int id) {
        String sql = "DELETE FROM stu WHERE id = ?";
        Object[] objects = {id};
        int deleteNum = MysqlUtil.updateMethod(sql, objects);
        return deleteNum;
    }
    //查询会员是否借书
    public static List<Map<String, Object>> findStuOwnBook(int id) {
        String sql = "SELECT na.id,na.stu_name,bo.book_id,bo.book_name FROM (SELECT s.id,s.stu_name,sb.book_id FROM stu s INNER JOIN stu_book sb ON s.id=sb.stu_id) na INNER JOIN (SELECT sb.book_id,b.book_name,b.book_author,b.book_price,book_publish FROM book b INNER JOIN stu_book sb ON b.id=sb.book_id) bo ON na.book_id=bo.book_id WHERE na.id = ? ORDER BY bo.book_id";
        Object[] objects = {id};
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, objects);
        return maps;
    }

    //-----------------------------------------------------------------------------------------------

    //1: 查询图书  2: 新增图书  3: 修改图书  4: 删除图书  5: 借书  6: 还书

    //查询全部图书
    public static List<Map<String, Object>> findBooks() {
        String sql = "SELECT id,book_name,book_price,book_author,book_publish FROM book";
        //查询全部会员信息无需传参
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, null);
        return maps;
    }

    //查询单本图书
    public static List<Map<String, Object>> findBook(int id) {
        String sql = "SELECT id,book_name,book_price,book_author,book_publish FROM book WHERE id = ?";
        Object[] objects = {id};
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, objects);
        return maps;
    }

    //新增图书
    public static int addBook(Object[] args) {
        String sql = "INSERT INTO book(book_name,book_price,book_author,book_publish) VALUES(?,?,?,?)";
        int updateNum = MysqlUtil.updateMethod(sql, args);
        return updateNum;
    }

    //实现新增图书
    public static void complyAddBook() {
        System.out.println("请输入书名:");
        Scanner scanner13 = new Scanner(System.in);
        String book_name = scanner13.nextLine();
        System.out.println("请输入作者:");
        Scanner scanner14 = new Scanner(System.in);
        String book_author = scanner14.nextLine();
        System.out.println("请输入价格: ");
        Scanner scanner15 = new Scanner(System.in);
        String book_price = scanner15.nextLine();
        System.out.println("请输入出版社名称: ");
        Scanner scanner16 = new Scanner(System.in);
        int book_publish = scanner16.nextInt();

        //入参,需要跟sql语句的中的插入顺序保持一致
        Object[] param = new Object[]{book_name, book_price, book_author, book_publish};
        int result = StuMemberBook.addBook(param);
        if (result == 1) {
            System.out.println("新增图书成功! ");
        } else {
            System.out.println("新增图书失败! ");
        }
    }

    //修改图书
    public static int updateBook(Object[] args) {
        String sql = "UPDATE book SET book_name=?,book_price=?,book_author=?,book_publish=? WHERE id = ?";
        int upNum = MysqlUtil.updateMethod(sql, args);
        return upNum;
    }
    //删除图书
    public static int deleteBook(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        Object[] objects = {id};
        int deleteNum = MysqlUtil.updateMethod(sql, objects);
        return deleteNum;
    }


    //查询书城空余图书
    public static List<Map<String, Object>> queryBooks() {
        String sql = "SELECT b.id,b.book_name,b.book_price,b.book_author,b.book_publish FROM book b  LEFT JOIN stu_book sb ON b.id=sb.book_id WHERE sb.book_id is null ORDER BY b.id";
        List<Map<String, Object>> maps = MysqlUtil.selectMethod(sql, null);
        return maps;
    }

    //借书
    public static int borrowBooks(Object[] args) {
        String sql = "INSERT INTO stu_book(stu_id,book_id) VALUES(?,?)";
        int updateNum = MysqlUtil.updateMethod(sql, args);
        return updateNum;
    }
    //已借图书详情
    public static void borrowedBookDetails() {
        List<Map<String, Object>> stuOwnBookId = StuMemberBook.findStuOwnBook(ID);
        if (stuOwnBookId != null && stuOwnBookId.size() > 0) {
            System.out.println("已借图书详情：");
            System.out.print("共"+stuOwnBookId.size()+"本，分别为：");
            for (int i = 0; i < stuOwnBookId.size(); i++) {
                String bookStuName = stuOwnBookId.get(i).get("book_name").toString();
                int bookStuId = (int) stuOwnBookId.get(i).get("book_id");
                //取出结果集中每行的值
                System.out.print("["+bookStuId+"]"+bookStuName+"  ");
            }
            System.out.println();
        }
    }

    //还书
    public static int returnBook(int id) {
        String sql = "DELETE FROM stu_book WHERE book_id = ?";
        Object[] objects = {id};
        int deleteNum = MysqlUtil.updateMethod(sql, objects);
        return deleteNum;
    }
}