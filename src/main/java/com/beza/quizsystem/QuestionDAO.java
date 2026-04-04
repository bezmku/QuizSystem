package com.beza.quizsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    String sql = "SELECT * FROM questions";
    public List<Question> getAllQuestions(){
        List<Question> questions = new ArrayList<>();


        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Question q = new Question(
                        rs.getInt("question_id"),
                        rs.getString("question_text"),
                        rs.getString("opt_a"),
                        rs.getString("opt_b"),
                        rs.getString("opt_c"),
                        rs.getString("opt_d"),
                        rs.getString("correct_answer")
                );
                questions.add(q);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return questions;
    }
}
