package TestCases;


import API_POJO.Login;
import API_POJO.Notes;
import API_POJO.Register;
import LIstener.IInvokedListener;
import Utilities.JasonDataUtils;
import Utilities.LogUtility;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Instant;

import static io.restassured.RestAssured.given;


@Listeners(IInvokedListener.class)
public class TC03_NotesAPI {

public static String token="";
public static String noteID ="";


    @BeforeMethod
    public void setUp()  {
        RestAssured.baseURI="https://practice.expandtesting.com/notes/api";
    }

    @Description("Create a user → Log in → Create a note → Retrieve the note → Update the note → Delete the note → Verify the user and note are deleted.")
    @Test
    public void end2end1() throws IOException {

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

        //Assertion
        Assert.assertEquals(resLogin.statusCode() , 200);
        Assert.assertEquals(resLogin.body().jsonPath().get("message"),"Login successful");
        token = resLogin.body().jsonPath().get("data.token").toString();

   //**creating  new  note**//
        Notes notes = JasonDataUtils.readJsonData("src/test/resources/DataNotes.json",Notes.class);

         notes.setDescription("project version" + Instant.now().toEpochMilli());
        Response resNote  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .body(notes)
                .when().post("/{notes}");
        //logs
        LogUtility.info("Response Status Code: " +resNote.statusCode() );
        LogUtility.info("Response Body:"+ resNote.prettyPrint());

        //Assertion
        Assert.assertEquals(resNote.statusCode() , 200);
        Assert.assertEquals(resNote.body().jsonPath().get("message"),"Note successfully created");
        Assert.assertEquals(resNote.body().jsonPath().get("data.category"),notes.getCategory());
        Assert.assertEquals(resNote.body().jsonPath().get("data.title"),notes.getTitle());

        noteID =resNote.body().jsonPath().get("data.id");


   //**Getting note by ID**//
        Response NoteData  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .pathParam("idNote",noteID)
                .when().get("/{notes}/{idNote}");


        //logs
        LogUtility.info("Response Status Code: " +NoteData.statusCode() );
        LogUtility.info("Response Body:"+ NoteData.prettyPrint());

        //Assertion
        Assert.assertEquals(NoteData.statusCode() , 200);
        Assert.assertEquals(NoteData.body().jsonPath().get("success"),true);
        Assert.assertEquals(NoteData.body().jsonPath().get("data.id"),noteID);


   //**Update specific note info**//
        notes.setCompleted(true);
        Response resUpdated  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .pathParam("idNote",noteID)
                .body(notes)
                .when().patch("/{notes}/{idNote}") ;

        //logs
        LogUtility.info("Response Status Code: " +resUpdated.statusCode() );
        LogUtility.info("Response Body:"+ resUpdated.prettyPrint());

        //Assertion
        Assert.assertEquals(resUpdated.statusCode() , 200 , "Expected status code to be 200");
        Assert.assertEquals(resUpdated.body().jsonPath().get("data.completed"),true );
        Assert.assertEquals(resUpdated.body().jsonPath().get("message"),"Note successfully Updated");

   //**Delete Note**//
        Response resDelete = given()
                .pathParam("idNote",noteID)
                .pathParam("notes","notes")
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .when().delete("/{notes}/{idNote}");

        //logs
        LogUtility.info("Response Status Code: " +resDelete.statusCode() );
        LogUtility.info("Response Body:"+ resDelete.prettyPrint());

        //Assertion
        Assert.assertEquals(resDelete.statusCode(),200, "Expected status code to be  200");
        Assert.assertEquals(resDelete.body().jsonPath().get("message"),"Note successfully deleted");

//**Getting note by ID After deleted**//
        Response NoteDataDeleted  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .pathParam("idNote",noteID)
                .when().get("/{notes}/{idNote}");


        //logs
        LogUtility.info("Response Status Code: " +NoteDataDeleted.statusCode() );
        LogUtility.info("Response Body:"+ NoteDataDeleted.prettyPrint());

        //Assertion
        Assert.assertEquals(NoteDataDeleted.statusCode() , 404);
        Assert.assertEquals(NoteDataDeleted.body().jsonPath().get("success"),false);
        Assert.assertEquals(NoteDataDeleted.body().jsonPath().get("message"),"No note was found with the provided ID, Maybe it was deleted");



    }


    @Description("Create a user → Log in with the user credentials → Create a note associated with that user.")
    @Test
    public void end2end2() throws IOException {
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

        //Assertion
        Assert.assertEquals(resLogin.statusCode() , 200);
        Assert.assertEquals(resLogin.body().jsonPath().get("message"),"Login successful");
        token = resLogin.body().jsonPath().get("data.token").toString();

        //**creating  new  note**//
        Notes notes = JasonDataUtils.readJsonData("src/test/resources/DataNotes.json",Notes.class);

        notes.setDescription("project version" + Instant.now().toEpochMilli());
        Response resNote  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .body(notes)
                .when().post("/{notes}");
        //logs
        LogUtility.info("Response Status Code: " +resNote.statusCode() );
        LogUtility.info("Response Body:"+ resNote.prettyPrint());


        //Assertion
        Assert.assertEquals(resNote.statusCode() , 200);
        Assert.assertEquals(resNote.body().jsonPath().get("message"),"Note successfully created");
        Assert.assertEquals(resNote.body().jsonPath().get("data.category"),notes.getCategory());
        Assert.assertEquals(resNote.body().jsonPath().get("data.title"),notes.getTitle());


    }




}
