package raisetech.studet.management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.studet.management.data.Student;
import raisetech.studet.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できてからのリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service,times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細のID検索が実行できて空で返ってくる事() throws Exception {
    String id = "999";
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}",id))
        .andExpect(status().isOk());

    verify(service,times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが４００で返ってくる事() throws Exception {
    mockMvc.perform(get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("このAPIは現在利用できません。古いURLとなっております。"));
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくる事()throws Exception{
    //リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    //本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない。
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
             {
             "student": {
                  "name": "江並公史",
                  "kanaName": "エナミコウジ",
                  "nickName": "エナミ",
                  "email": "test@example.com",
                  "area": "奈良",
                  "age": 40,
                  "sex": "男性",
                  "remark": "特になし"
              },
              "studentCourseList": [
              {
                  "courseName": "Java"
                  }
                ]
            }
           """
        ))
        .andExpect(status().isOk());

    verify(service,times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくる事()throws Exception{
    //リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    //本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない。
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                 {
                 "student": {
                      "studentId": "12",
                      "name": "江並公史",
                      "kanaName": "エナミコウジ",
                      "nickName": "エナミ",
                      "email": "test@example.com",
                      "area": "奈良",
                      "age": 40,
                      "sex": "男性",
                      "remark": "特になし"
                  },
                  "studentCourseList": [
                  {
                      "id": "15",
                      "studentId": "12",
                      "courseName": "Java",
                      "courseStartAt": "2026-05-01T10:00:00",
                      "courseEndAt": "2027-04-30T18:00:00"
                      }
                    ]
                }
               """
        ))
        .andExpect(status().isOk());

    verify(service,times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと(){
    Student student = new Student();
    student.setStudentId("1");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickName("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックにかかること(){
    Student student = new Student();
    student.setStudentId("テストです。");
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickName("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにして下さい。");

  }
}