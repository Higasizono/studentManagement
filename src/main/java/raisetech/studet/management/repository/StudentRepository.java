package raisetech.studet.management.repository;

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
   * @return 受講生一覧（全件）
   */
    List<Student> search();

  /**
   * 受講生の検索を行います。
   * @param studentId　受講生ID
   * @return 受講生
   */
    Student searchStudent(String studentId);

  /**
   * 受講生のコース情報の全件検索を行います。
   * @return 受講生のコース情報（全件）
   */
    List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐ずく受講生コース情報を検索します。。
   * @param studentId 受講生ID
   * @return 受講生IDに紐ずくコース情報
   */
    List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   * @param student 受講生
   */
    void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   * @param studentCourse 受講生コース
   */
    void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   * @param student 受講生
   */
    void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   * @param studentCourse 受講生コース情報
   */
    void updateStudentCourse(StudentCourse studentCourse);
}
