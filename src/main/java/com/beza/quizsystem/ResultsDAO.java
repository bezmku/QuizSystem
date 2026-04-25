package com.beza.quizsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultsDAO {
    public List<QuizResult> getResultsByUserId(int userId){
        List<QuizResult> list = new ArrayList<>();
        String sql = "SELECT s.subject_name, r.date_taken, r.total_question,r.score "+
                "FROM quiz_results r "+
                "JOIN subjects s ON s.subject_id = r.subject_id "+
                "WHERE r.user_id = ? ORDER BY r.date_taken DESC";
                try(Connection conn = DatabaseConfig.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setInt(1,userId);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        QuizResult result = new QuizResult(
                                rs.getString("subject_name"),
                                rs.getTimestamp("date_taken").toString(),
                                rs.getInt("total_question"),
                                rs.getInt("score")
                        );
                        list.add(result);
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
                return list;
    }

    public List<QuizResult> getResultsForTeacher(int teacherId){
        List<QuizResult> list = new ArrayList<>();
        String sql = "SELECT u.username, s.subject_name, r.date_taken, r.total_question, r.score "+
                    "FROM quiz_results r "+
                    "JOIN users u ON u.user_id = r.user_id "+
                    "JOIN subjects s ON s.subject_id = r.subject_id "+
                    "JOIN questions q ON q.subject_id = r.subject_id "+
                    "WHERE q.teacher_id = ? "+
                    "GROUP BY r.result_id "+
                    "ORDER BY r.date_taken DESC";

        try(Connection conn = DatabaseConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                QuizResult res = new QuizResult(
                        rs.getString("username"),
                        rs.getString("subject_name"),
                        rs.getString("date_taken"),
                        rs.getInt("total_question"),
                        rs.getInt("score")
                        );
                list.add(res);
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveResults(int userId, int subjectId,int total, int score){
        String sql = "INSERT INTO quiz_results(user_id, subject_id, score, total_question) VALUES(?, ?, ?, ?)";

        try(Connection conn = DatabaseConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,userId);
            ps.setInt(2,subjectId);
            ps.setInt(3,score);
            ps.setInt(4,total);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
