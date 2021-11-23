package com.saucelabs.yy.Tests.Appium.SwagLabsApp.RealDevice.Android;

import com.saucelabs.yy.Tests.Appium.SwagLabsApp.RealDevice.TestBase;
import org.openqa.selenium.remote.LocalFileDetector;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import static java.lang.Thread.sleep;
import static org.testng.AssertJUnit.fail;

public class BacktraceTest extends TestBase {

    @Test(dataProvider = "AndroidRealDevices")
    public void crashpadTest(String platformName, String deviceName, Method testMethod) throws MalformedURLException {
        createDriver(platformName, deviceName, testMethod.getName());
        getDriver().setFileDetector(new LocalFileDetector());

        try {
            File file = File.createTempFile("crashpadResult", "log");
            FileOutputStream outputStream = new FileOutputStream(file);

            outputStream.write(getDriver().pullFolder("/sdcard/Android/data/backtraceio.backtraceio/files/Documents/crashpad"));
            System.out.println("Size of Crashpad completed dir before crash: " + file.length());

            assert(file.length() < 1000);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        getDriver().findElementById("backtraceio.backtraceio:id/nativeCrash").click();

        try {
            sleep(5000);

            File file = File.createTempFile("crashpadResult", "log");
            FileOutputStream outputStream = new FileOutputStream(file);

            outputStream.write(getDriver().pullFolder("/sdcard/Android/data/backtraceio.backtraceio/files/Documents/crashpad"));
            System.out.println("Size of Crashpad completed dir after crash: " + file.length());
            assert(file.length() > 10000);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        System.out.println(getDriver().getCapabilities().getCapability("testobject_test_report_url"));
    }
}
