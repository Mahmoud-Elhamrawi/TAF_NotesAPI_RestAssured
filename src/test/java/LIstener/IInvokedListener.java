package LIstener;


import Utilities.utilityClasses;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;



public class IInvokedListener implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        File logFile = utilityClasses.getLastFileLog(Utilities.LogUtility.logPath);
        try {
            assert logFile != null;
            Allure.addAttachment("LOGS", Files.readString(logFile.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
