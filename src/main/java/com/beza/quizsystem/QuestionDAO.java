package com.beza.quizsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public boolean saveQuestions(List<Question> questions,int subjectId){
        String sql = "INSERT INTO questions(question_text,opt_a,opt_b,opt_c,opt_d,correct_answer,subject_id) VALUES(?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseConfig.getConnection()){
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                for(Question q : questions){
                    ps.setString(1,q.getText());
                    ps.setString(2,q.getOptA());
                    ps.setString(3,q.getOptB());
                    ps.setString(4,q.getOptC());
                    ps.setString(5,q.getOptD());
                    ps.setString(6,q.getCorrectAnswer());
                    ps.setInt(7,subjectId);
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
                return true;
            }catch(SQLException e){
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Question> getAllQuestions(){
        String sql = "SELECT * FROM questions";
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
    public List<Question> getQuestionsById(int id){
        String sql = "SELECT * FROM questions WHERE subject_id = ?";
        List<Question> questions = new ArrayList<>();


        try(Connection conn = DatabaseConfig.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
           try( ResultSet rs = ps.executeQuery()){

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
        }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return questions;
    }
    public List<Subject> getSubjects(){
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects";
        try(Connection conn = DatabaseConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
              while(rs.next()){
                  Subject s = new Subject(
                          rs.getInt("subject_id"),
                          rs.getString("subject_name")
                  );
                  subjects.add(s);
              }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return subjects;
    }

}
