package raisetech.studet.management.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.studet.management.data.Student;
import raisetech.studet.management.data.StudentCourse;
import raisetech.studet.management.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること(){
    Student student = new Student();

    student.setStudentId("1");
    student.setName("山田 太郎");
    student.setKanaName("やまだ たろう");
    student.setNickName("たろちゃん");
    student.setEmail("yamada.taro@example.com");
    student.setArea("福岡県");
    student.setAge(22);
    student.setSex("男性");
    student.setRemark(" ");
    student.setDeleted(false);

    StudentCourse studentCourse = new StudentCourse();

    studentCourse.setId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("Javaコース");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList,studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡した時に受講生コース情報は除外されること(){
    Student student = new Student();

    student.setStudentId("1");
    student.setName("山田 太郎");
    student.setKanaName("やまだ たろう");
    student.setNickName("たろちゃん");
    student.setEmail("yamada.taro@example.com");
    student.setArea("福岡県");
    student.setAge(22);
    student.setSex("男性");
    student.setRemark(" ");
    student.setDeleted(false);

    StudentCourse studentCourse = new StudentCourse();

    studentCourse.setId("1");
    studentCourse.setStudentId("2");
    studentCourse.setCourseName("Javaコース");
    studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList,studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEmpty();
  }
}