package TestCases;

import API_POJO.Login;
import API_POJO.Register;
import API_POJO.UpdateUserData;
import Utilities.JasonDataUtils;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Instant;

import static io.restassured.RestAssured.given;

public class TC02_LoginTest {

    static String token ="";


    @BeforeClass
    public void setUp()  {
        RestAssured.baseURI="https://practice.expandtesting.com/notes/api";

    }


    @Description("Apply integration testing level")
    @Test
    public void validLoginTC() throws IOException {

//**register**//
    Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
    register.setEmail("Test"+ Instant.now().toEpochMilli()+"@gmail.com");

    Response res = given()
            .contentType(ContentType.JSON)
            .pathParam("user","users")
            .pathParam("register","register")
            .body(register)
            .when().post("/{user}/{register}");

    res.prettyPrint() ;
    token = res.body().jsonPath().get("token");


//**Login**//
        Login loginData = new Login();
        loginData.setEmail(register.getEmail());
        loginData.setPassword(register.getPassword());

        Response resLogin  = given()
                .contentType(ContentType.JSON)
                .pathParam("user","users")
                .pathParam("login","login")
                .body(loginData)
                .when().post("/{user}/{login}");

        resLogin.prettyPrint() ;
        Assert.assertEquals(resLogin.statusCode() , 200);
        Assert.assertEquals(resLogin.body().jsonPath().get("message"),"Login successful");
        token = resLogin.body().jsonPath().get("data.token").toString();


//**get profile**//
    Response profileUser = given()
            .header("x-auth-token",token)
            .pathParam("users","users")
            .pathParam("profile","profile")
            .when().get("/{users}/{profile}") ;

    profileUser.prettyPrint() ;

    Assert.assertEquals(profileUser.statusCode(),200) ;
    Assert.assertEquals(profileUser.body().jsonPath().get("message"),"Profile successful");


    //**update user profile**//
    UpdateUserData userData = JasonDataUtils.readJsonData("src/test/resources/userDataUpdate.json",UpdateUserData.class);

    Response updateUserProfile = given()
            .header("x-auth-token",token)
            .contentType(ContentType.JSON)
            .pathParam("users","users")
            .pathParam("profile","profile")
            .body(userData)
            .when().patch("/{users}/{profile}") ;

    updateUserProfile.prettyPrint() ;

    Assert.assertEquals(updateUserProfile.statusCode(),200) ;
    Assert.assertEquals(updateUserProfile.body().jsonPath().get("message"),"Profile updated successful");
    Assert.assertEquals(updateUserProfile.body().jsonPath().get("data.name"),userData.getName());



    }





}
