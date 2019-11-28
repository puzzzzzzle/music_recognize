package homework_3.functions.hashaccess;


import homework_3.functions.hashing.ShazamHash;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 因为部分小组成员不熟悉sql操作，所以你既可以用建表sql下的sql语句在cmd中建表
 * 也可以直接调用本类中的建表方法（当表不存在而需要使用时，会自动建表）
 *
 * 本类中我写了几乎所有该数据库可能使用到的方法
 * 包括数据库的创建、删除，表的创建、删除，初始化等等，可以对本数据库进行几乎所有操作
 *
 * 如果确保数据库和表没有任何问题(初始化后未对表或数据库进行删改操作)，可以直接调用QuickWriteSql，
 * 我对此删掉了所有检验的逻辑，使用了批量添加的方法，关掉了jdbc自动添加，尽可能提高速度
 * 因此这个方法会提高速度，但是不稳定，报错不清楚，请不要修改数据库和表的情况下使用该方法。
 */
public class HashAccess {
    private Statement statement=null;
    private Connection connection=null;
    public HashAccess() {
        //在构造时连接数据库，
        ConnectToDatabase(MySqlValues.GetDatabase());
        //构造时初始化表
        init();
    }
    public HashAccess(String timeDb) {
        //在构造时连接数据库，
        ConnectToDatabase(timeDb);
        if(!IsTableExist("song"))
        CreateTable("song");
    }

    /**
     * 连接数据库
     * @param database 数据库名
     * @return
     */
    private boolean ConnectToDatabase(String database) {
        try {
            //加载JDBC驱动
            Class.forName(MySqlValues.GetDriver());
            //创建数据库连接对象
            connection = DriverManager.getConnection(MySqlValues.GetURL() +database, MySqlValues.GetUser(), MySqlValues.GetPassword());
            return true;
        } catch (ClassNotFoundException e) {
            //驱动加载失败直接报错退出
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            //未找到库时调用创建方法
            CrestDatabase(database);
            try {
                //创建数据库连接对象
                connection=DriverManager.getConnection(MySqlValues.GetURL() + database, MySqlValues.GetUser(), MySqlValues.GetPassword());
                return true;
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 创建数据库
     * @param database  数据库名
     * @return
     */
    public boolean CrestDatabase(String database){
        try {
            connection = DriverManager.getConnection(MySqlValues.GetURL(),MySqlValues.GetUser(),MySqlValues.GetPassword());
            statement = connection.createStatement();
            String sql = "CREATE DATABASE "+database;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean init(){
        if(!IsTableExist("song")){
            CreateTable("song");
        }
        if(!IsTableExist("finger")){
            CreateTable("finger");
        }
        return true;
    }

    /**
     * 快速写入数据库的方法，10,0000条数据测试用时8秒
     * @param songName
     * @param hashes
     * @return
     */
    public boolean QuickWriteSql(String songName, ArrayList<ShazamHash> hashes){
        try {
            //关掉jdbc的自动写入，防止每条记录写入一个log，会在内存写完后一次性写入sql，极大地提高速度
            connection.setAutoCommit(false);

            //得到末尾的song和finger的id
            ResultSet song= GetTable("song");
            ResultSet finger =GetTable("finger");
            int songNum=0,fingerNum=0;
            while (song.next()){
                songNum = song.getInt("Id");
            }
            while (finger.next()){
                fingerNum=finger.getInt("Id");
            }
//            System.out.println("songNum"+songNum);
//            System.out.println("fingerNum"+fingerNum);

            //插入song
            String sql = "INSERT INTO song " +
                    "VALUES (null, ?)";
            PreparedStatement preparedStatement= null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,songName);
            //加到暂存
            preparedStatement.addBatch();
            //释放暂存
            preparedStatement.executeBatch();

            //插入finger
            String sql1 = "INSERT INTO finger " +
                    "VALUES (null, ?, ?, ?,?,?)";
            PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
            int size = hashes.size();
            ShazamHash hashTemp=null;

//            //插入songFinger表
//            String sql2 = "INSERT INTO songFinger " +
//                    "VALUES (?, ?,?)";
//            PreparedStatement preparedStatement2= connection.prepareStatement(sql2);
            String songId=String.valueOf(songNum+1);
//           循环写入
            for (int i=0;i<size;i++){
                hashTemp=hashes.get(i);
                preparedStatement1.setString(1, String.valueOf(hashTemp.GetF1()));
                preparedStatement1.setString(2, String.valueOf(hashTemp.GetF2()));
                preparedStatement1.setString(3, String.valueOf(hashTemp.GetDt()));
                preparedStatement1.setString(4, songId);
                preparedStatement1.setString(5, String.valueOf(hashTemp.getOffset()));


//                preparedStatement2.setString(1, songId);
//                preparedStatement2.setString(2, String.valueOf(fingerNum+1+i));
//                preparedStatement2.setString(3, String.valueOf(hashTemp.getOffset()));

                //加到暂存
                preparedStatement1.addBatch();
//                preparedStatement2.addBatch();
            }
            //释放暂存
            preparedStatement1.executeBatch();
//            preparedStatement2.executeBatch();

            //统一提交，由于关掉了自动提交，需要手动提交
            connection.commit();

            //再次打开自动提交，以便其他方法使用
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Connection getConnection(){
        return connection;
    }

    public boolean DeleteDatabase(String database){
        try {
            statement = connection.createStatement();
            String sql = "DROP DATABASE "+database;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 本方法供不会执行sql的组员方便使用，请尽量用建表sql下的命令行建表，当建表失败或者未找到表时会自动调用本方法
     * @param table 表名
     * @return
     */
    public boolean CreateTable(String table){
        String sql=null;
        try {
            statement = connection.createStatement();
            if (table == "song") {
                sql = "CREATE TABLE " + table +
                        " (Id int not NULL AUTO_INCREMENT, " +
                        " path varchar(255) not NULL, " +
                        " PRIMARY KEY ( Id ))";
            }
            else if (table == "finger") {
                sql = "CREATE TABLE " + table +
                        " (Id int not NULL AUTO_INCREMENT, " +
                        " f1 varchar(50) not NULL, " +
                        " f2 varchar(50) not NULL, " +
                        " dt varchar(50) not NULL, " +
                        " song int not NULL, "+
                        "offset VARCHAR(50), " +
                        " foreign key(song) references song(Id), " +
                        " PRIMARY KEY ( Id ))";
            }
            else{
                return false;
            }
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public ResultSet GetTable(String table) {
        ResultSet result = null;
        try
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM "+table);
        } catch (SQLException e)
        {
            try {
                statement = connection.createStatement();
                CreateTable(table);
                result = statement.executeQuery("SELECT * FROM "+table);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

    public boolean IsTableExist(String table){
        try
        {
            statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM "+table);
            return true;
        } catch (SQLException e)
        {
            return false;
        }
    }
    public String timeTest(){
        try
        {
            HashAccess hashAccess =new HashAccess("timeDb");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            hashAccess.AddSong(df.format(new Date()));
            ResultSet res= hashAccess.GetTable("song");
            String time="";
            while (res.next()){
                time=res.getString("path");
            }
            return time;
        } catch (SQLException e)
        {
            return "";
        }
    }

    public boolean AddSong(String path){
        if(!IsTableExist("song")){
            CreateTable("song");
        }
        try {
            String sql = "INSERT INTO song " +
                    "VALUES (null, ?)";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,path);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public  boolean AddFinger(String f1,String f2,String dt,int songId, double offset){
        if(!IsTableExist("finger")){
            CreateTable("finger");
        }
        try {
            String sql = "INSERT INTO finger " +
                    "VALUES (null, ?, ?, ?.?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,f1);
            preparedStatement.setString(2,f2);
            preparedStatement.setString(3,dt);
            preparedStatement.setString(4, String.valueOf(songId));
            preparedStatement.setString(5, String.valueOf(offset));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateSong(int Id,String path){
        if(!IsTableExist("song")){
            return false;
        }
        try {
            String sql = "UPDATE song " +
                    "SET path = ? WHERE Id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,path);
            preparedStatement.setString(2, String.valueOf(Id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String FindSong(int id){
        ResultSet result = null;
        String name="";
        try
        {
            String sql = "SELECT * FROM  song " +
                    "WHERE Id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            result = preparedStatement.executeQuery();
            while (result.next()){
                name = result.getString("path");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return name;
    }

    public ResultSet FindSong(String path){
        ResultSet result = null;
        try
        {
            String sql = "SELECT * FROM  song " +
                    "WHERE path LIKE ? ";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, path);
            result = preparedStatement.executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet findFinger(int id){
        ResultSet result = null;
        try
        {
            String sql = "SELECT * FROM  finger " +
                    "WHERE Id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            result = preparedStatement.executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    private boolean DeleteSong (int id){
        try
        {
            String sql = "DELETE FROM  song " +
                    "WHERE Id = ?";

            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean DeleteFinger (int id){
        try
        {
            String sql = "DELETE FROM  finger " +
                    "WHERE Id = ?";
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void PrintTableToConsole(String table){
        ResultSet resultSet = GetTable(table);
        try {
            if(table=="song"){
                System.out.println("songs:\nId\tpath");
                while (resultSet.next()){
                    System.out.println(resultSet.getInt("Id")+"\t"+resultSet.getString("path"));
                }
            }
            if(table=="finger"){
                System.out.println("fingers:\nId\tf1\tf2\tdt\tsongId\toffset");
                while (resultSet.next()){
                    System.out.println(resultSet.getInt("Id")+"\t"+resultSet.getString("f1")+
                            "\t"+resultSet.getString("f2")+"\t"+resultSet.getString("dt")+"\t"+
                            resultSet.getString("song")+"\t"+resultSet.getString("offset"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
