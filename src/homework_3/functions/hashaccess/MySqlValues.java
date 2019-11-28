package homework_3.functions.hashaccess;

/**
 * 其他成员通过修改此文件来匹配你的MySQL
 * 本类基于MySQL5.5，JDBC4.0
 * 注意只要把MySQL localhost设为3306各个版本都应该能匹配
 */
public final class MySqlValues {
    //MySQL JDBC驱动字符串
    private final static   String DRIVER_MYSQL = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String DATABASE="MusicHash";
    private final static  String USER = "root";
    private final static String PASSWORD="123456";
    public static String GetDriver(){
        return DRIVER_MYSQL;
    }
    public static String GetUser(){
        return USER;
    }
    public static String GetURL(){
        return URL;
    }
    public static String GetPassword(){
        return PASSWORD;
    }
    public static String GetDatabase(){
        return DATABASE;
    }
}
