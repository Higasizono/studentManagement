package raisetech.studet.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.studet.management.controller.converter.StudentConverter;
import raisetech.studet.management.data.Student;
import raisetech.studet.management.data.StudentsCourses;
import raisetech.studet.management.domain.StudentDetail;
import raisetech.studet.management.service.StudentService;

import java.util.Arrays;
import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@RestController
public class StudentController {


    private StudentService service;


  @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    //GET POST
    //GETは取得する、リクエストを受け取る()
    //POSTは情報を与える,渡す(新規登録、更新)

    //全件取得

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧（全件）
   */
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

  /**
   * 受講生検索です。
   * IDに紐ずく任意の受講生の情報を取得します。
   *
   * @param studentId 受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{studentId}")
    public StudentDetail getStudent(@PathVariable String studentId){
        return service.searchStudent(studentId);
    }

    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail){
        StudentDetail responseStudentDetail =  service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }
}
