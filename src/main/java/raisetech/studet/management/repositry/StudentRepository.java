package raisetech.studet.management.repositry;

import org.apache.ibatis.annotations.*;
import raisetech.studet.management.data.Student;
import raisetech.studet.management.data.StudentCourse;

import java.util.List;

// /**を入力してEnterを押すとコメントが残せる。クラス名やメソッド名にカーソルでコメントの情報が見れる。

/**
 * 受講生テーブルと受講生コース情報と紐ずくRepositoryです。
 * 全件検索や単一条件の検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */
  @Select("SELECT * FROM students WHERE is_deleted = false")
    List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param studentId　受講生ID
   * @return 受講生
   */
    @Select("SELECT * FROM students WHERE student_id = #{studentId}")
    Student searchStudent(String studentId);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報（全件）
   */
  @Select("SELECT * FROM students_courses")
    List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐ずく受講生コース情報を検索します。。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐ずくコース情報
   */
    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
    @Insert("INSERT INTO students(name, kana_name, nick_name, email, area, age, sex, remark, is_deleted) " +
            "VALUES(#{name}, #{kanaName}, #{nickName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "studentId")
    void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース
   */
    @Insert("INSERT INTO students_courses(student_id, course_name, course_start_at, course_end_at)"
            + "VALUES(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  @Update("UPDATE students SET name = #{name}, kana_name = #{kanaName}, nick_name = #{nickName}, " +
            "email = #{email}, area = #{area}, age = #{age}, sex = #{sex},  remark = #{remark}, " +
            "is_deleted = #{isDeleted} WHERE student_id = #{studentId}")
    void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
    @Update("UPDATE students_courses SET course_name = #{courseName} WHERE id = #{id}")
    void updateStudentCourse(StudentCourse studentCourse);
}
