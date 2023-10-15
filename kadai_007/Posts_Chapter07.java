package kadai.kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {

	public static void main(String[] args) {	
		Connection con = null;
		PreparedStatement statement = null;
		Statement state = null;
		
		// 投稿データ
		String[][] posts = {
			{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
			{"1002", "2023-02-08", "お疲れ様です！", "12"},
			{"1003", "2023-02-09", "今日も頑張ります！", "18"},
			{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
			{"1002", "2023-02-10", "明日から連休ですね！", "20"}		
		};
		
		try {
			 // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "7,.~#h2by)+W"
            );
            System.out.println("データベース接続成功：" + con);
            
            // SQLクエリを準備
            String sqlUpdate = "INSERT INTO posts(user_id, posted_at, post_content, likes) VALUES(?, ?, ?, ?);";
            statement = con.prepareStatement(sqlUpdate);
            
            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            for(int i = 0; i < posts.length; i++) {
            	// SQLクエリの「？」部分をリストのデータにか￥置き換え
            	statement.setString(1, posts[i][0]);
            	statement.setString(2, posts[i][1]);
            	statement.setString(3, posts[i][2]);
            	statement.setString(4, posts[i][3]);
            }
            // SQLクエリを実行
            System.out.println("レコード追加を実行します");
            rowCnt = statement.executeUpdate();
            System.out.println(rowCnt + "件のレコードが追加されました");
            
            // 再度SQLクエリを準備
            state = con.createStatement();
            String sqlAcq = "SELECT * FROM posts WHERE user_id = 1002;";
            
            // SQLクエリを実行
            ResultSet result = statement.executeQuery(sqlAcq);
            System.out.println("ユーザーIDが1002のレコードを検索しました");
            
            // SQLクエリの実行結果を抽出
            while(result.next()) {
            	String date = result.getString("posted_at");
            	String contents = result.getString("post_content");
            	int likes = result.getInt("likes");
            	System.out.println(result.getRow() + "件目：投稿日時=" + date + "/投稿内容=" + contents + "/いいね数=" + likes); 
            }
            
		} catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
            
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
            if( state != null ) {
                try { state.close(); } catch(SQLException ignore) {}
            }
        }

	}

}
