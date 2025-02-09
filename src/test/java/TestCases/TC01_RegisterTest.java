package TestCases;

import API_POJO.Login;
import API_POJO.Register;
import Utilities.JasonDataUtils;
import com.beust.ah.A;
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

public class TC01_RegisterTest {

    public static String email ="";


    @BeforeMethod
    public void setUp()
    {
        RestAssured.baseURI="https://practice.expandtesting.com/notes/api";
    }

    @Description("Apply component testing level ")
    @Test
    public void validRegisterTC() throws IOException {

        Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
          register.setEmail("Test"+ Instant.now().toEpochMilli()+"@gmail.com");


        Response res = given()
                .contentType(ContentType.JSON)
                .pathParam("user","users")
                .pathParam("register","register")
                .body(register)
                .when().post("/{user}/{register}");

       //Response Data
        res.prettyPrint() ;

        //Assertion
        Assert.assertEquals(res.statusCode(), 201 );
        Assert.assertEquals(res.body().jsonPath().get("message"),"User account created successfully");
        Assert.assertEquals(res.body().jsonPath().get("data.name"),register.getName(),"Expected name equal name in file json ");


        email = res.body().jsonPath().get("data.email");
    }



    @Description("register with invalid empty format")
    @Test
    public void in_ValidRegisterTC1() throws IOException {
        Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
        register.setEmail("Test"+ Instant.now().toEpochMilli());  // invalid format email


        Response res = given()
                .contentType(ContentType.JSON)
                .pathParam("user","users")
                .pathParam("register","register")
                .body(register)
                .when().post("/{user}/{register}");

      //Response Data
        res.prettyPrint() ;

      //Assertion
        Assert.assertEquals(res.statusCode(), 400,"Expected status code to be 400");
        Assert.assertEquals(res.body().jsonPath().get("message"),"A valid email address is required");

    }


    @Description("register with exist email")
    @Test
    public void in_ValidRegisterTC2() throws IOException {

        Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
        register.setEmail("test1739080211628@gmail.com");// exist email


        Response res = given()
                .contentType(ContentType.JSON)
                .pathParam("user","users")
                .pathParam("register","register")
                .body(register)
                .when().post("/{user}/{register}");

        //response data
        res.prettyPrint() ;

        //Assertion
        Assert.assertEquals(res.statusCode(), 409 );
        Assert.assertEquals(res.body().jsonPath().get("message"),"An account already exists with the same email address");

    }



    @Description("register with missing required field")
    @Test
    public void in_ValidRegisterTC3() throws IOException {

        Register register = JasonDataUtils.readJsonData("src/test/resources/userRegisterData.json",Register.class);
        register.setEmail("");  // missing required field


        Response res = given()
                .contentType(ContentType.JSON)
                .pathParam("user","users")
                .pathParam("register","register")
                .body(register)
                .when().post("/{user}/{register}");

        // response
        res.prettyPrint() ;

        //Assertion
        Assert.assertEquals(res.statusCode(), 400 ,"Expected status code to be 400");
        Assert.assertEquals(res.body().jsonPath().get("message"),"A valid email address is required");

    }


}





