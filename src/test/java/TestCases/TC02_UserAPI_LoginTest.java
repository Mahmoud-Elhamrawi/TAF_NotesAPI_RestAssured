package TestCases;

import API_POJO.Login;
import API_POJO.Register;
import API_POJO.UpdateUserData;
import Utilities.JasonDataUtils;
import Utilities.LogUtility;
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

public class TC02_UserAPI_LoginTest {

    static String token ="";


    @BeforeMethod
    public void setUp()  {
        RestAssured.baseURI="https://practice.expandtesting.com/notes/api";

    }

    @Description("Create a user → Log in → Create a note  → get user -> update user")
    @Test
    public void integrationTC1() throws IOException {

//**register**//
    Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
    register.setEmail("Test"+ Instant.now().toEpochMilli()+"@gmail.com");

    Response res = given()
            .contentType(ContentType.JSON)
            .pathParam("user","users")
            .pathParam("register","register")
            .body(register)
            .when().post("/{user}/{register}");


        //logs
        LogUtility.info("Response Status Code: " +res.statusCode() );
        LogUtility.info("Response Body:"+ res.prettyPrint());



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

        //logs
        LogUtility.info("Response Status Code: " +resLogin.statusCode() );
        LogUtility.info("Response Body:"+ resLogin.prettyPrint());


        Assert.assertEquals(resLogin.statusCode() , 200);
        Assert.assertEquals(resLogin.body().jsonPath().get("message"),"Login successful");
        token = resLogin.body().jsonPath().get("data.token").toString();


//**get profile**//
    Response profileUser = given()
            .header("x-auth-token",token)
            .pathParam("users","users")
            .pathParam("profile","profile")
            .when().get("/{users}/{profile}") ;


        //logs
        LogUtility.info("Response Status Code: " +profileUser.statusCode() );
        LogUtility.info("Response Body:"+ profileUser.prettyPrint());

//Assertion
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


        //logs
        LogUtility.info("Response Status Code: " +updateUserProfile.statusCode() );
        LogUtility.info("Response Body:"+ updateUserProfile.prettyPrint());
//Assertion
    Assert.assertEquals(updateUserProfile.statusCode(),200) ;
    Assert.assertEquals(updateUserProfile.body().jsonPath().get("message"),"Profile updated successful");
    Assert.assertEquals(updateUserProfile.body().jsonPath().get("data.name"),userData.getName());



    }


    @Description("Create a user → Log in → Create a note  → Delete user")
    @Test
    public void integrationTC3() throws IOException {
        //**register**//
        Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
        register.setEmail("Test"+ Instant.now().toEpochMilli()+"@gmail.com");

        Response res = given()
                .contentType(ContentType.JSON)
                .pathParam("user","users")
                .pathParam("register","register")
                .body(register)
                .when().post("/{user}/{register}");

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
        token = resLogin.body().jsonPath().get("data.token").toString();


        //** Delete User**//
        Response userDelete = given()
                .header("x-auth-token",token)
                .pathParam("user","users")
                .pathParam("delete","delete-account")
                .when().delete("/{user}/{delete}") ;


        //logs
        LogUtility.info("Response Status Code: " +userDelete.statusCode() );
        LogUtility.info("Response Body:"+ userDelete.prettyPrint());

//Assertion
        Assert.assertEquals(userDelete.statusCode(),200,"Expected status code tto br 200");
        Assert.assertEquals(userDelete.body().jsonPath().get("message"),"Account successfully deleted","Expected message telling user deleted");



    }


   @Description("Log in with invalid credentials and verify the error response.")
    @Test
    public void integrationTC2() throws IOException {
    Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
    register.setEmail("Test"+ Instant.now().toEpochMilli()+"@gmail.com");

       Response res = given()
               .contentType(ContentType.JSON)
               .pathParam("user","users")
               .pathParam("register","register")
               .body(register)
               .when().post("/{user}/{register}");

       res.prettyPrint() ;

    //**Login**//
    Login loginData = new Login();
    loginData.setEmail(register.getEmail());
    loginData.setPassword("ssd5ddd");

    Response resLogin  = given()
            .contentType(ContentType.JSON)
            .pathParam("user","users")
            .pathParam("login","login")
            .body(loginData)
            .when().post("/{user}/{login}");

       //logs
       LogUtility.info("Response Status Code: " +resLogin.statusCode() );
       LogUtility.info("Response Body:"+ resLogin.prettyPrint());

      //Assertion
       Assert.assertEquals(resLogin.statusCode() , 401);
       Assert.assertEquals(resLogin.body().jsonPath().get("message"),"Incorrect email address or password");


}



}
