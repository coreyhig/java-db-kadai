package sql_kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Scores_Chapter10 {
    public static void main(String[] args) {
    	
    	Scanner scanner = null;
        Connection con = null;
        Statement statement = null;

        try {
        	 System.out.println("0(昇順)か1(降順)を入力してください：");
             scanner = new Scanner(System.in); // 入力待ち
        
             //入力内容に応じて並べ替え方向を決定
             String order = switch( scanner.nextInt() ) {
                 case 0 -> "ASC;";
                 case 1 -> "DESC;";
                 default -> "ASC;"; // デフォルトは昇順扱い
             };
            
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "178Mania@"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            statement = con.createStatement();
            String sql1 = "UPDATE users SET score_math = '95' WHERE id = 5;";
            String sql2 = "UPDATE users SET score_english = '80' WHERE id = 5;";           
            String sql3 = "SELECT * FROM users ORDER BY age " + order;
            
            // SQLクエリを実行（DBMSに送信）
            System.out.println("レコード更新:" + statement.toString() );
            int rowCnt = statement.executeUpdate(sql1);
            System.out.println( rowCnt + "件のレコードが更新されました");
            System.out.println("レコード更新:" + statement.toString() );
            statement.executeUpdate(sql2);
            System.out.println("データ取得を実行：" + sql3);
            ResultSet result = statement.executeQuery(sql3);
            
            // SQLクエリの実行結果を抽出
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                System.out.println(result.getRow() + "件目：id=" + id
                                   + "／name=" + name + "／age=" + age );
            };
        } catch(InputMismatchException e) {
            System.out.println("入力が正しくありません");
            
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
        }
    }
}