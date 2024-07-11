package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberJdbcRepository implements MemberRepository{

    @Autowired
    private DataSource dataSource;

    @Override
    public Member save(Member member) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "insert into member(name, email, password) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.executeUpdate(); // 추가, 수정의 경우 excuteUpdate. 조회의 경우 excuteQuery.
            // 원래는 디비에 넣는 멤버를 리턴해야하는데~ 차피 나중에 스프링데이타jpa가 해줄거야~
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Member> findall() {
        return List.of();
    }

    @Override
    public Member findById(Long id) {
        return null;
    }
}
