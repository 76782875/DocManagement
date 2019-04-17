package ������;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import �ͻ���.Doc;

public class SqlDoc extends XiaoJian {

	public SqlDoc() throws Exception {
		super();
		// TODO �Զ����ɵĹ��캯�����
	}
	public boolean put(String key,Doc doc){
       	try {
           stmt=conn.createStatement();
           String sql ;
    	 if (!containsKey(key)) sql = "insert into dbo.DocTab values(?,?,?,?,?,?) ";  
    	 else sql="update dbo.DocTabTab set DocId = ?,id= ?,creator=?,timestamp=?,description=?,filename=? where Docid='"+key+"'";
    	   PreparedStatement pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
    	   pst.setString(1, key);  
    	   pst.setString(2,doc.getID()); 
    	   pst.setString(3, doc.getCreator());
    	   pst.setString(4, doc.getTimestamp().toString());
    	   pst.setString(5, doc.getDescription());
    	   pst.setString(6, doc.getFilename());
    	   pst.executeUpdate();  
    	   pst.close();  
    	   return true;
       	}catch(Exception e) {
       		System.out.println(e.getMessage());
       		return false;
       	}
    }
    public boolean containsKey(String namekey){//�����ݿ�������ݲ�ѯ
        try {
            rs=stmt.executeQuery("select *from DocTab where DocId='"+namekey+"'");
           if(rs.next()) {
            return true;}
           else return false;
        } catch (SQLException e) {
            return false;
        }
        
    }
	public void remove(String namekey) {
		// TODO �Զ����ɵķ������
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
		}
		String sql = "delete from DocTab where DocId='"+namekey+"'";  
		try {
			stmt.executeUpdate(sql);
			stmt.close(); 
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
		}
	}
	public Doc get(String namekey) {
		containsKey(namekey);
		String ID = null;
		String creator = null;
		Timestamp timestamp = null;
	    String description = null;
		 String filename = null;
		try {
			ID=rs.getString("id");
			creator=rs.getString("creator");
			timestamp=rs.getTimestamp("timestamp");
			description=rs.getString("description");
			filename=rs.getString("filename");
		}catch(Exception e) {
			return null;
		}
		return new Doc(ID, creator, timestamp, description, filename);
		
	}
	public ArrayList<Doc> getDoc() {
		ArrayList<Doc> doc=new ArrayList<Doc>();
		 try {
		   rs = stmt.executeQuery("select id,creator,timestamp,description,filename from DocTab");
		   while(rs.next()){
			   String ID=rs.getString("id");
			   String	creator=rs.getString("creator");
			   Timestamp timestamp=rs.getTimestamp("timestamp");
				String description=rs.getString("description");
				String filename=rs.getString("filename");
			 doc.add(new Doc(ID, creator, timestamp, description, filename));
		       }
		   }catch (SQLException e) {
			   System.out.println(e.getMessage());
				return null;	// TODO �Զ����ɵ� catch ��
				}
		return doc;}

}
