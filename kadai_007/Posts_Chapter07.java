package text.kadai_007;

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
		Statement statementSelect = null;
		
		// ユーザーリスト
		String[][] contentsList = {
				{"1003","2023-02-08","昨日の夜は徹夜でした・・","13"},
				{"1002","2023-02-08","お疲れ様です！","12"},
				{"1003","2023-02-09","今日も頑張ります！","18"},
				{"1001","2023-02-09","無理は禁物ですよ！","17"},
				{"1002","2023-02-10","明日から連休ですね！","20"},
		};
		
		try {
			// DBに接続
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/Challenge_java",
				"root",
				"HPM5RvXB3nm9"
			);
			
			System.out.println("データベース接続成功：" + con);
			System.out.println("レコード追加を実行します");
			
			
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ? ,?);";
			statement = con.prepareStatement(sql);
			
			// リストの1行目から順番に読み込む
			int rowCnt = 0;
			for( int i = 0; i < contentsList.length; i++ ) {
				statement.setString(1, contentsList[i][0]); // user_id
				statement.setString(2, contentsList[i][1]); // posted_at
				statement.setString(3, contentsList[i][2]); // post_content
				statement.setString(4, contentsList[i][3]); // likes
				rowCnt = statement.executeUpdate() + rowCnt;
			}
				
			// SQLクエリを実行(DBMSに送信)
			System.out.println(rowCnt + "件のレコードが追加されました");

			
			
			// SQLクエリを準備
			statementSelect = con.createStatement();
			String sql2 = "SELECT * FROM posts WHERE user_id = 1002 ;";
			
			// SQLクエリを実行(DBMSに送信)
			ResultSet result = statementSelect.executeQuery(sql2);
			
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			// SQLクエリの実行結果を抽出
			while(result.next()) {
				String post_content = result.getString("post_content");
				String posted_at = result.getString("posted_at");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿時間=" + posted_at
									+ "／投稿内容=" + post_content + "／いいね数=" + likes);
				
				// 投稿時間　投稿内容　いいね数
			}
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if( statement != null ) {
				try { statement.close(); } catch(SQLException ignore) {}
			}
		}
		if ( con != null) {
			try { con.close(); } catch(SQLException ignore) {}
		}

	}


}
