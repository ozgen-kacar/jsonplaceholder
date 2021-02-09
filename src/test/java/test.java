
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;


public class test {
    private String baseURI = "https://jsonplaceholder.typicode.com";

    @Test
    @DisplayName("get the item which has id equals 2- use queryparam")
    public void test1() {
        Response response = given()
                .baseUri(baseURI + "/posts")
                .queryParam("id", "2")
                .get();
        Assertions.assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());

    }

    @Test
    @DisplayName("get all items list as json format")
    public void test2() {
        Response response = given()
                .baseUri(baseURI + "/posts")
                .get();
        Assertions.assertEquals(200, response.getStatusCode());
        System.out.println(response.prettyPrint());
    }


    @Test
    @DisplayName("get id=100 info by using rote/end point")
    public void test3() {
        Response response = given()
                .baseUri(baseURI + "/posts/100")
                .get();
        Assertions.assertEquals(200, response.getStatusCode());
        response.prettyPrint();
    }

    @Test
    @DisplayName("get comments which belongs to postid=5 by using rote/end point")
    public void test4() {
        Response response = given()
                .baseUri(baseURI + "/posts/5/comments")
                .get();
        Assertions.assertEquals(200, response.getStatusCode());
        response.prettyPrint();
    }

    @Test
    @DisplayName("verify that size is 100")
    public void test5() {
        given()
                .get(baseURI+"/posts").
                        then().
                        assertThat()
                .body("size()", is(100));

    }

    @DisplayName("get employee with id 50 and verify that response returns status code 200 also , print body")
    @Test
    public void test6() {

        Response response = given().
                header("accept", "application/json").get(baseURI + "/posts/50");
        int actualStatusCode = response.getStatusCode();
        response.prettyPrint();
        Assertions.assertEquals(200, actualStatusCode);

        System.out.println( "What kind of content server sends to you, in this response : "+response.getHeader("Content-Type"));
    }



    @Test
    @DisplayName("Save payload into java collection")
    public void test7() {
        Response response = given().
                contentType(ContentType.JSON).
                when().
                get(baseURI+"/posts");

        List<Map<String, ?>> collection = response.jsonPath().get();

        for (Map<String, ?> map : collection) {
            System.out.println(map);
        }
    }

    @Test
    @DisplayName("Add new user by using external JSON file")
    public void test8(){
        File file = new File(System.getProperty("user.dir")+"/addNewUser.json");
         given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().body(file).
                post(baseURI+"/posts").
                then().assertThat().
                statusCode(201);


    }

    @Test
    @DisplayName("Delete one of the comments")
    public void test9() {

        Response response = when().delete(baseURI+"/posts/1/comments");

        response.prettyPeek();
    }


}