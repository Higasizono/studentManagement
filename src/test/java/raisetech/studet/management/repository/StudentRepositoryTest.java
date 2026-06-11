package raisetech.studet.management.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.studet.management.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private  StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生の登録が行えること(){

    Student student = new Student();
    student.setName("山田 太郎");
    student.setKanaName("やまだ たろう");
    student.setNickName("たろちゃん");
    student.setEmail("yamada.taro@example.com");
    student.setArea("福岡県");
    student.setAge(22);
    student.setSex("男性");
    student.setRemark(" ");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(6);
  }

}