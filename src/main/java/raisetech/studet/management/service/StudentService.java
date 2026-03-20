package raisetech.studet.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.studet.management.controller.converter.StudentConverter;
import raisetech.studet.management.data.Student;
import raisetech.studet.management.data.StudentsCourses;
import raisetech.studet.management.domain.StudentDetail;
import raisetech.studet.management.repositry.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

    private StudentRepository repository;
  private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository,StudentConverter converter) {
        this.repository = repository;
      this.converter = converter;
    }

  /**
   * 受講生の一覧検索を行います。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（一覧）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentsCourses> studentsCoursesList = repository.searchStudentCourseList();
        return converter.convertStudentDetails(studentList, studentsCoursesList);
    }

  /**
   * 受講生検索です
   * IDに紐づく受講生情報を取得した後、その受講生情報に紐ずく受講生コース情報を取得して設定します。
   * @param studentId 受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String studentId){
        Student student = repository.searchStudent(studentId);
        List<StudentsCourses> studentsCourses = repository.searchStudentCourse(student.getStudentId());
        return new StudentDetail(student,studentsCourses);
    }

    public List<StudentsCourses> searchStudentCourseList() {
        return repository.searchStudentCourseList();
    }

    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            studentsCourses.setStudentId(studentDetail.getStudent().getStudentId());
            studentsCourses.setCourseStartAt(LocalDateTime.now());
            studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourse(studentsCourses);
        }
        return studentDetail;
    }

    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        repository.updateStudent(studentDetail.getStudent());
        for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
            studentsCourses.setStudentId(studentDetail.getStudent().getStudentId());
            repository.updateStudentCourse(studentsCourses);
        }
    }
}
