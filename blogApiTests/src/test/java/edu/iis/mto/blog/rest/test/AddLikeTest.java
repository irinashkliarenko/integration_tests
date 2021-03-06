package edu.iis.mto.blog.rest.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Piotrek on 23.05.2017.
 */
public class AddLikeTest {

    @Test
    public void testOrder(){
        onlyConfirmedUserCanAddPost();
        confirmedUserCannotLikeOwnPost();
        cannotLikePostTwiceByOneUser();
    }

    @Ignore
    public void onlyConfirmedUserCanAddPost(){
        JSONObject jsonObj = new JSONObject().put("entry", "message");
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_CREATED).when()
                .post("/blog/user/1/post");

        jsonObj = new JSONObject().put("email", "test@test.com").put("accountStatus", "NEW").put("firstName", "test").put("lastName", "test");
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_CREATED).when()
                .post("/blog/user");

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_OK).when()
                .post("/blog/user/3/like/1");
    }

    @Ignore
    public void confirmedUserCannotLikeOwnPost(){
        JSONObject jsonObj = new JSONObject();
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_BAD_REQUEST).when()
                .post("/blog/user/1/like/1");
    }

    @Ignore
    public void cannotLikePostTwiceByOneUser(){
        JSONObject jsonObj = new JSONObject();
        String receive = "";
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_OK).when()
                .post("/blog/user/3/like/1");

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(receive).expect().log().all().statusCode(HttpStatus.SC_OK).when()
                .get("/blog/user/1/post").print().contains("likesCount:" + " 1");
    }
}
