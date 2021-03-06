package edu.iis.mto.blog.rest.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by Piotrek on 23.05.2017.
 */
public class AddPostTest extends FunctionalTests{

    @Test
    public void onlyConfirmedUserCanLikeOtherUserPost(){
        JSONObject jsonObj = new JSONObject().put("entry", "message");
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_CREATED).when()
                .post("/blog/user/1/post");
    }
}
