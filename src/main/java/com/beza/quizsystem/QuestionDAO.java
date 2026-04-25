package com.beza.quizsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public boolean saveQuestions(List<Question> questions,int subjectId, int teacherId){
        String sql = "INSERT INTO questions(teacher_id,question_text,opt_a,opt_b,opt_c,opt_d,correct_answer,subject_id) VALUES(?,?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseConfig.getConnection()){
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                for(Question q : questions){
                    ps.setInt(1,teacherId);
                    ps.setString(2,q.getText());
                    ps.setString(3,q.getOptA());
                    ps.setString(4,q.getOptB());
                    ps.setString(5,q.getOptC());
                    ps.setString(6,q.getOptD());
                    ps.setString(7,q.getCorrectAnswer());
                    ps.setInt(8,subjectId);
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
    public List<Question> getQuestionsByTeacherId(int teacherId){
        String sql = "SELECT q.*, s.subject_name "+
                    "FROM questions q JOIN subjects s ON q.subject_id = s.subject_id "+
                    "WHERE q.teacher_id = ? ORDER BY q.question_id DESC";
        List<Question> list = new ArrayList<>();
        try(Connection conn = DatabaseConfig.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,teacherId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Question q = new Question(
                        rs.getInt("question_id"),
                        rs.getString("question_text"),
                        rs.getString("opt_a"),
                        rs.getString("opt_b"),
                        rs.getString("opt_c"),
                        rs.getString("opt_d"),
                        rs.getString("correct_answer"),
                        rs.getString("subject_name")

                );
                list.add(q);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;

    }

}
