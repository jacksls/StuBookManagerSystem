import com.mysql.jdbc.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liang
 * @date 2020/3/25 16:21
 */
public class MysqlUtil {
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/stubook?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PWD = "552552";

    //获取连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            //1:注册驱动引入-个mysql的驱动包
            Class.forName(DRIVER_CLASS);
            //2:获取连接
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //释放连接
    public static void release(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
                //System.out.println("resultSet 关闭");
            }
            if (statement != null) {
                statement.close();
                //System.out.println("statement 关闭");
            }
            if (connection != null) {
                connection.close();
                //System.out.println("connection 关闭");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通用的新增方法
    public static int updateMethod(String sql, Object[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int updateNum = 0;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //参数赋值
            if (args != null && args.length != 0) {
                for (int i = 0; i < args.length; i++) {
                    //参数赋值
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            //执行
            updateNum = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(connection, preparedStatement, null);
        }
        //返回数据库更新的行教
        return updateNum;

    }


    // 通用的查询方法
    public static List<Map<String, Object>> selectMethod(String sql, Object[] args) {

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (args != null && args.length != 0) {
                for (int i = 0; i < args.length; i++) {
                    // 参数赋值
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();

            //目标:希望返回一个List<Map<String, Object>>
            // map ：key - value 键值对
            // 格式:列名和值
            //先获取所有返回的列名
            if (resultSet != null) {
                while (resultSet.next()) {
                    //先获取所有返回的列名//获取所有的列集
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    //获取列的总个数
                    int columnCount = metaData.getColumnCount();
                    //封装Map引包的快捷健:alt+enter
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 0; i < columnCount; i++) {
                        //賦值map中的key是列名，value是对应的值
                        String columnName = metaData.getColumnName(i + 1);
                        Object value = resultSet.getObject(columnName);
                        map.put(columnName, value);
                    }
                    //将一行数据放入放回值中
                    resultList.add(map);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(connection, preparedStatement, resultSet);
        }
        //返回整行数据
        return resultList;
    }

    //public static void main(String[] args) throws SQLException {
        /*Connection connection = MysqlUtil.getConnection();
        System.out.println(connection);
        release(connection, null, null);*/
/*        //查询测试
        String sql = "SELECT id,name,age,sex from student_2";
        Object[] objects = {3};
  *//*    //取出值
        ResultSet resultSet = selectMethod(sql, objects);
        if(resultSet!=null){
            while(resultSet.next()){
                //取出结果集中每行的值
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                System.out.println("id="+id+" name="+name+" age="+age+" sex="+sex);
            }*//*
        List<Map<String, Object>> mapList = selectMethod(sql, objects);
        System.out.println(mapList.size());*/

        //更新测试
/*        String sql="insert into student_1(id, name,age,sex) VALUES(? ,?,?,?)";
        Object[] objects = {11, "UFO" , 25 , "男"};
        int updateNum = updateMethod(sql,objects);
        System.out.println("影响行数"+updateNum);

    }*/

}