package TestCases;


import API_POJO.Login;
import API_POJO.Notes;
import API_POJO.Register;
import Utilities.JasonDataUtils;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Instant;

import static io.restassured.RestAssured.given;

public class TC03_NotesTC {

public static String token="";
public static String noteID ="";


    @BeforeClass
    public void setUp()  {
        RestAssured.baseURI="https://practice.expandtesting.com/notes/api";
    }

    @Description("Apply end-2-end testing Level")
    @Test
    public void notesTC() throws IOException {

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

        System.out.println(res.body().jsonPath().get("data.email").toString());



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

   //**creating  new  note**//
        Notes notes = JasonDataUtils.readJsonData("src/test/resources/DataNotes.json",Notes.class);

         notes.setDescription("project version" + Instant.now().toEpochMilli());
        Response resNote  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .body(notes)
                .when().post("/{notes}");
        //Response  Data
        resNote.prettyPrint() ;

        //Assertion
        Assert.assertEquals(resNote.statusCode() , 200);
        Assert.assertEquals(resNote.body().jsonPath().get("message"),"Note successfully created");
        Assert.assertEquals(resNote.body().jsonPath().get("data.category"),notes.getCategory());
        Assert.assertEquals(resNote.body().jsonPath().get("data.title"),notes.getTitle());

        noteID =resNote.body().jsonPath().get("data.id");
        System.out.println(noteID);

   //**Getting note by ID**//
        Response NoteData  = given()
                .header("x-auth-token",token)
                .contentType(ContentType.JSON)
                .pathParam("notes","notes")
                .pathParam("idNote",noteID)
                .when().get("/{notes}/{idNote}");


     //Response Data
        NoteData.prettyPrint() ;

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

        //Response Data
        resUpdated.prettyPrint() ;

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

        //Response Data
        resUpdated.prettyPrint() ;

        //Assertion
        Assert.assertEquals(resDelete.statusCode(),200, "Expected status code to be  200");
        Assert.assertEquals(resDelete.body().jsonPath().get("message"),"Note successfully deleted");





    }






}
