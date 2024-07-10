package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller // 해당 클래스가 컨트롤러(사용자의 요청을 처리하고 응답하는 편의기능)임을 명시
@RequestMapping("/hello") // 클래스 차원에 url매핑시에 requestMapping 사용
//@RestController // controller + 각 메서드마다 @ResponseBody
public class HelloController {
    //case1 : 사용자가 서버에게 화면요청 (get)
    //case2 : ResponseBody가 붙으면 단순 String 데이터 return (get)
    @GetMapping("/") //  getmapping 을 통해 get요청을 처리하고 url패턴을 명시
    @ResponseBody // 서버의 응답
//    responsebody를 사용하면 화면이 아닌 데이터를 return
//    (경우a) 만약 여기서 rerposebody가 없고, return이 스트링이면 스프링은
//    templates 폴더 밑에 helloworld.html(화면)파일을 찾아 리턴
    public String helloWorld(){
//        return "helloworld"; // (경우a) html파일이름이랑 리턴스트링이랑 같아야해
        return "hello world";
    }


//    case3 : 사용자가 json 데이터 요청 (get)
//    data를 리턴하되, json형식으로 return
//    method명은 helloJson, url패턴은 /hello/json
    @GetMapping("/json")
//    @RequestMapping(value = "/json", method = RequestMethod.GET) // 매서드 차원에서도 resquestMapping 사용 가능
    @ResponseBody
//    responseBody 사용하면서 객체를 return시 자동으로 직렬화
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("java");
        hello.setEmail("java@spring.com");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello); // 객체->json 문자열
//        return value; // 함수의 리턴값이 스트링 일때, 거치는 과정
        return hello;
    }


//    case4 : 사용자가 json데이터를 요청하되, 파라미터형식으로 특정 객체 요청 (get)
//    get 요청 중에 특정 데이터를 요청
    @GetMapping("/param1")                      // 호출이냐 아니냐??????????
    @ResponseBody // param1, ok 리턴
//        parameter형식 : ?name:hong
//        localhost:8080/hello/param1?name=hong
    public Hello param1(@RequestParam(value = "name")String inputName){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail("hong@naver.com");
        System.out.println(hello);
        return hello;
    }


//    url 패턴 param2, 메서드 : param2
//    parameter 2개 name email => hello 객체 생성 후 리턴
//    요청 방식 : localhost:8080/hello/param2?name=xxx&email=xxx.naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello param2(@RequestParam(value = "name")String inputName,
                        @RequestParam(value = "email")String inputEmail){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }


//    case5 : parameter 형식으로 요청하되, 서버에서 데이터바인딩 하는 형식(get)
    @GetMapping("/param3")
    @ResponseBody
//    parameter가 많을 경우, 객체로 대체가 가능, 객체에 각 변수에 맞게 알아서 바인딩(데이터(객체)바인딩=알아서맵핑된다고)
//    요청 방식 : localhost:8080/hello/param3?name=xxx&email=xxx.naver.com&password=xxx // 값을 안넣으면 null 값이 들어간다.
//    데이터바인딩 조건 : 기본생성자, setter(setter 쓰는 계층이 있고 안쓰는 계층이 있다.)
    public Hello param3(Hello hello){
        return hello;
    }


//    case6 : 서버에서 화면에 데이터를 넣어(ssr) 사용자에게 return(model 객체 : 키값을 통해 화면에 데이터를 주입시키는 객체 사용) (get) // 자바의 데이터를 가져다가 템플릿에서 활용하는 방법은 타임리프와 jpa 등이 있는데, 이때 자바의 데이터를 화면에 주입시키는 객체가 모델일 뿐이다.
    @GetMapping("/model-param")
//    @ResponseBody //   안쓴다. 왜? responsebody를 사용하면 화면이 아닌 데이터를 return
    public String modelParam(@RequestParam(value = "name")String inputName, Model model){
//        model객체에 name이라는 키값에 value를 세팅하면 해당 key:value는 화면으로 전달
        model.addAttribute("name", inputName); // 모델을 통해서 화면에 데이터 주입
        return "helloworld"; // 화면(html파일)이름과 리턴스트링값이 같아야한다.
    }


//    case7 : pathvariable방식을 통해 사용자로부터 값을 받아 화면 리턴 (get)
//    localhost:8080/hello/model-path/honggildong // localhost:8080/hello/model-path?name=hong (파라미터 방식)
//    예시 : localhost:8080/hello/author/1 또는 author?id=1
//    pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀더 restful(http통신 원칙 중 하나 : 자원의 구조를 명확하게 하라)한 형식
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable String name, Model model){
        model.addAttribute("name",name);
        return "helloworld";
    }






//    @GetMapping("/form-post1")
    @GetMapping("/form-post2")
    //    사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
    //    메서드명 : formView, 화면명: form-view
    public String formView(){
        return "form-view";
    }
//    post 요청(사용자입장에서 서버에 데이터를 주는 상황)
//    case1 : url 인코딩 방식(text만) 전송
//    (데이터 받아오는) 형식 : key1=value&key2=value2
    @PostMapping("/form-post1") // getmapping과 같은 url패턴 사용도 가능
    @ResponseBody
    public String formPost1(@RequestParam(value = "name")String inputName,
                            @RequestParam(value = "email")String inputEmail,
                            @RequestParam(value = "password")String inputPassword){// html- input의 name <-> value
//        사용자로부터 받아온 내용 출력
        System.out.println(inputName + inputEmail + inputPassword);
        return "ok";
    }

    // 객체로 받자  // 화면을 form-post1과 함께 쓰고 있어용 // form의 action에서 변경해주세용
    @PostMapping("/form-post2") // getmapping과 같은 url패턴 사용도 가능
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello){ // ModelAttribute(데이터 바인딩 하는데 사용되는 어노테이션) 생략 가능
//      public String formPost2(Hello hello){
//        사용자로부터 받아온 내용 출력
        System.out.println(hello);
        return "ok";
    }

//    case2 : multipart/form-data 방식(text와 파일) 전송
//    url : form-file-post  메서드 : formFilePost    화면명 form-file-view
//    form 태그 name email password file
    @GetMapping("/form-file-post")
    public String formFileView() {
        return "form-file-view";
    }

    @PostMapping("/form-file-post") // get과 post는 구분이 가능하므로 같은 url로 해도 된다.
    @ResponseBody
    public String formFilePost(Hello hello,
                               @RequestParam(value = "photo")MultipartFile photo){ // 멀티파트 포토 Hello 클래스에 넣어도 돼용
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }


//    case3 : js를 활용한 form 데이터 전송(text만!)
    @GetMapping("/axios-form-view") // 현재는 ssr 방식(템플릿엔진..)이라 getmapping 필요
    public String axiosFormView(){
        //        name, email, password를 전송
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    //    axios를 통해 넘어오는 형식이 key1=value1&key2=value2 등 url 인코딩 방식
    public String axiosFormPost(Hello hello){
        System.out.println(hello);
        return "ok";
    }

//    case4 : js를 활용한 form 데이터 전송(+file)
    @GetMapping("axios-form-file-view")
    public String axiosFormFileView(){
    //        name, email, password, file를 전송
        return "axios-form-file-view";
    }

    @PostMapping("axios-form-file-view")
    @ResponseBody
    public String axiosFormFilePost(Hello hello, @RequestParam(value = "file")MultipartFile file){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }


//    case5 : js를 활용한 json 데이터 전송
//    url 패턴 : axios-json-view    화면명 : axios-json-view    get 요청 메서드 동일
//    post 요청 메서드 : axiosJsonPost
    @GetMapping("axios-json-view")
    public String axiosJsonView(){
        return "axios-json-view";
    }

    @PostMapping("axios-json-view")
    @ResponseBody
//    json으로 전송한 데이터를 받을 때에는 @RequestBody(제이슨데이터 받아서 파싱할꺼얌?) 어노테이션 사용
    public String axiosJsonPost(@RequestBody Hello hello){ // Form태그 -> 파라미터? // json은 파라미터형태로 안줬잖아 리퀘바디(표준이당)로 받아야함?
        System.out.println(hello);
        return "ok";
    }


//    case6 : js를 활용한 json 데이터 전송(+file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView(){
        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-file-view")
    @ResponseBody
//    @RequestPart : 파일과 Json을 처리할 때 주로 사용하는 어노테이셚
    public String axiosJsonFilePost(
//            @RequestParam(value = "hello") String hello,
//            @RequestParam(value = "file") MultipartFile file)
//            formdata를 통해 json, file(멀티미디어)을 처리할때 RequestPart라는 어노테이션을 많이 사용한다.
            @RequestPart("hello") Hello hello,
            @RequestPart("file") MultipartFile file)
            throws JsonProcessingException { // 파일을 처리하는 방식은 다양하다 / json 안쓰고 폼태그로 하는 경우도 있음 근데 파라미터 형식으로 복잡해질 수 있어서 어렵기도 해.
        System.out.println(hello);
//        String으로 받은 뒤 수동으로 객체로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(hello, Hello.class);
//        System.out.println(h1.getName());
        System.out.println(file.getOriginalFilename());
        return "ok";
    }


//    case7 : js를 활용한 json 데이터 전송(+여러 file : list multipart)
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView(){
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
    public String axiosJsonMultiFilePost(
            @RequestPart("hello") Hello hello,
            @RequestPart("files") List<MultipartFile> files) {
        System.out.println(hello);
        for(MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }

//    case 8 : 중첩된 JSON 데이터 처리
//    STUDENT 객체
//    {name:'hongildong', email:'hong@naver.com', score:[{math:80}, {science:90}, {java:80}]}
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView(){
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student) {
        System.out.println(student);
        return "ok";
    }


}


