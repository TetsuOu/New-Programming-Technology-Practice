import java.util.*;
import java.sql.*;
public class DButil {
    private String url = PropertyUtil.getValue("url");
    private String user = PropertyUtil.getValue("user");
    private String password = PropertyUtil.getValue("password");
    private String driverClass = PropertyUtil.getValue("driverClass");
    private Connection con = null;
    public DButil(){
        con = getConnection();
    }
    public Connection getConnection() {
        Connection conn = null;
        try{
            System.out.println("正在连接数据库...");
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("数据库连接成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    //赠、删、改等调用这个函数
    public int executeUpdate(String sql,Object... params){
        int rlt = 0;
        try{
            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement(sql);
            putParams(pstmt,params);
            rlt = pstmt.executeUpdate();
            pstmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rlt;
    }
    //放置参数
    private void putParams(PreparedStatement pstmt, Object[] params) throws SQLException{
        if(params!=null){
            for(int i=0;i<params.length;++i){
                //pstmt.setObject(i+1,params);
                if(params[i] instanceof String) pstmt.setString(i+1,(String)params[i]);
                else if(params[i] instanceof Integer) pstmt.setInt(i+1,(Integer)params[i]);
                else if(params[i] == null) pstmt.setNull(i+1, Types.INTEGER);
            }
        }
    }
    //查询调用这个函数
    public List<Map<String,Object>> query(String sql,Object... params){
        PreparedStatement pstmt = null;
        List<Map<String,Object>> list = null;
        try{
            pstmt = con.prepareStatement(sql);
            putParams(pstmt,params);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            String[] keys = new String[rsmd.getColumnCount()];
            for(int i=1;i<=rsmd.getColumnCount();++i){
                keys[i-1] = rsmd.getColumnLabel(i);
            }
            list = new ArrayList<Map<String,Object>>();
            while(rs.next()){
                Map<String,Object> map = new HashMap<String,Object>();
                for(int i=0;i<keys.length;++i){
                    map.put(keys[i],rs.getObject(keys[i]));
                }
                list.add(map);
            }
            rs.close();
            pstmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public void close(){
        if(this.con!=null){
            try{
                this.con.close();
                System.out.println("已关闭接口..");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}