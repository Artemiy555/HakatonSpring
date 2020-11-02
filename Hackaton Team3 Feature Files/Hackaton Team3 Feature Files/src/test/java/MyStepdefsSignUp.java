import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class MyStepdefsSignUp {
    private static ChromeDriver driver;
    private static JavascriptExecutor js;
    String URL="https://hackatoncloud.herokuapp.com/sign-up";
    String reg_email;
    String reg_name;
    String reg_pass;
    String reg_short_pass;

//data attributes:
    String data_title="REGtitle";
    String data_username_input="REGusernameInput";
    String data_email_input="REGemailInput";
    String data_pass_input="REGpassInput";
    String data_conf_pass_input="REGconfPassInput";
    String data_sign_up_btn="REGsignUpBtn";
    String data_sign_in_link="REGsignInLink";
    String data_gmail_sign_up_btn="REGgmailSignUpBtn";
    String data_title_001="AUTtitle";
    String data_error_mssg="errorMssg";


    @Given("^I open registration page$")
    public void iOpenRegistrationPage() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
     //   waiter = new WebDriverWait(driver,1000);
        js = (JavascriptExecutor) driver;


        boolean b = !(driver.findElement(new By.ByCssSelector(data_username_input)).isDisplayed()
                && driver.findElement(new By.ByCssSelector(data_email_input)).isDisplayed()
                && driver.findElement(new By.ByCssSelector(data_pass_input)).isDisplayed()
                && driver.findElement(new By.ByCssSelector(data_conf_pass_input)).isDisplayed()
                && driver.findElement(new By.ByCssSelector(data_sign_up_btn)).isDisplayed()
                && driver.findElement(new By.ByCssSelector(data_sign_in_link)).isDisplayed());
        if(!b)  {
            System.out.println("iOpenRegistrationPage");
            driver.quit();}
    }



    @When("^I enter valid username$")
    public void iEnterValidUsername() {
        reg_name=randomString(stringType.CAMEL_CASE_ENG,8);

        WebElement username_input = driver.findElement(new By.ByCssSelector(data_username_input));
        if (!username_input.isDisplayed()) System.out.println("iEnterValidUsername");
        username_input.sendKeys(reg_name);
    }

    @And("^I enter valid email$")
    public void iEnterValidEmail() {
        reg_email=randomString(stringType.ENG_NUM,8)+"@hmail.com";
        WebElement email_input = driver.findElement(new By.ByCssSelector(data_email_input));
        if (!email_input.isDisplayed()) System.out.println("iEnterValidEmail");
        email_input.sendKeys(reg_email);
    }

    @And("^I enter valid pass$")
    public void iEnterValidPass() {
        reg_pass=randomString(stringType.ALL,9);
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        if (!pass_input.isDisplayed()) System.out.println("iEnterValidEmail");
        pass_input.sendKeys(reg_pass);
    }

    @And("^I enter valid confirmation pass$")
    public void iEnterValidConfirmationPass() {
        WebElement pass_conf_input = driver.findElement(new By.ByCssSelector(data_conf_pass_input));
        if (!pass_conf_input.isDisplayed()) System.out.println("iEnterValidEmail");
        pass_conf_input.sendKeys(reg_pass);
    }

    @And("^User clicks sign up btn$")
    public void userClicksSignUpBtn() {
        WebElement signUpBtn = driver.findElement(new By.ByCssSelector(data_sign_up_btn));
        if (!signUpBtn.isDisplayed()) System.out.println("userClicksSignUpBtn");
        signUpBtn.click();
    }

    @Then("^System proceeds with registration$")
    public void systemProceedsWithRegistration() {
        boolean res=driver.findElement(new By.ByCssSelector(data_title_001)).isDisplayed();
        driver.quit();
        Assertions.assertEquals(true,res);
    }

    @When("^I enter already registred username$")
    public void iEnterAlreadyRegistredUsername() {
        WebElement username_input = driver.findElement(new By.ByCssSelector(data_username_input));
        if (!username_input.isDisplayed()) System.out.println("iEnterAlreadyRegistredUsername");
        username_input.sendKeys(reg_name);
    }

    @Then("^System shows error message$")
    public void systemShowsErrorMessage() {
        boolean res=driver.findElement(new By.ByCssSelector(data_error_mssg)).isDisplayed();
        driver.quit();
        Assertions.assertEquals(true,res);
    }

    @When("^I enterl already registred username in uppercase$")
    public void iEnterAlreadyRegistredUsernameInUppercase() throws Exception {
        WebElement username_input = driver.findElement(new By.ByCssSelector(data_username_input));
        username_input.sendKeys(reg_name.toUpperCase());
    }

    @And("^I enter already registred email in uppercase$")
    public void iEnterAlreadyRegistredEmailInUppercase() {
        WebElement email_input = driver.findElement(new By.ByCssSelector(data_email_input));
        email_input.sendKeys(reg_email.toUpperCase());
    }

    @And("^I enter valid pass in lowercase$")
    public void iEnterValidPassInLowercase() {
        reg_pass=randomString(stringType.LOW_CASE_ENG,5)
                +randomString(stringType.SIGN,2)
                +randomString(stringType.NUMBERS,2);
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        pass_input.sendKeys(reg_pass);

    }

    @And("^User submits pass in uppercase in confirmation pass$")
    public void userSubmitsPassInUppercaseInConfirmationPass() {
        WebElement pass_conf_input = driver.findElement(new By.ByCssSelector(data_conf_pass_input));
        pass_conf_input.sendKeys(reg_pass);
    }

    @And("^I enter empty email$")
    public void iEnterEmptyEmail() {
        WebElement email_input = driver.findElement(new By.ByCssSelector(data_email_input));
        email_input.sendKeys("");
    }

    @And("^User submits email without symbols before @$")
    public void userSubmitsEmailWithoutSymbolsBefore() {
        WebElement email_input = driver.findElement(new By.ByCssSelector(data_email_input));
        email_input.sendKeys("@mail.com");
    }

    @And("^User submits email with spaces$")
    public void userSubmitsEmailWithSpaces() {
        WebElement email_input = driver.findElement(new By.ByCssSelector(data_email_input));
        email_input.sendKeys(randomString(stringType.ENG_NUM,8)+" @hmail.com");
        }

    @And("^I entern empty pass$")
    public void iEnternEmptyPass() {
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        pass_input.sendKeys("");
      }

    @And("^User submits short pass$")
    public void userSubmitsShortPass() {
        reg_short_pass=randomString(stringType.CAMEL_CASE_ENG,4)
                +randomString(stringType.SIGN,1)
                +randomString(stringType.NUMBERS,1);
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        pass_input.sendKeys(reg_short_pass);
        WebElement pass_conf_input = driver.findElement(new By.ByCssSelector(data_conf_pass_input));
        pass_input.sendKeys(reg_short_pass);
    }

    @And("^User submits short pass confirmation pass$")
    public void userSubmitsShortPassConfirmationPass() {
          }

    @And("^User submits digits pass$")
    public void userSubmitsDigitsPass() {
        reg_short_pass=randomString(stringType.NUMBERS,9);
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        pass_input.sendKeys(reg_short_pass);
        WebElement pass_conf_input = driver.findElement(new By.ByCssSelector(data_conf_pass_input));
        pass_input.sendKeys(reg_short_pass);
    }

    @And("^User submits digits pass confirmation$")
    public void userSubmitsDigitsPassConfirmation() {
    }

    @And("^User submits lowercase pass$")
    public void userSubmitsLowercasePass() {
        reg_short_pass=randomString(stringType.NUMBERS,9);
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        pass_input.sendKeys(reg_short_pass);
        WebElement pass_conf_input = driver.findElement(new By.ByCssSelector(data_conf_pass_input));
        pass_input.sendKeys(reg_short_pass);
    }

    @And("^User submits lowercase confirmation$")
    public void userSubmitsLowercaseConfirmation() {
    }

    @And("^User submits differnet pass in confirmation field$")
    public void userSubmitsDiffernetPassInConfirmationField() {
        WebElement pass_input = driver.findElement(new By.ByCssSelector(data_pass_input));
        pass_input.sendKeys(randomString(stringType.LOW_CASE_ENG,5)
                +randomString(stringType.SIGN,2)
                +randomString(stringType.NUMBERS,2));
        WebElement pass_conf_input = driver.findElement(new By.ByCssSelector(data_conf_pass_input));
        pass_input.sendKeys(randomString(stringType.LOW_CASE_ENG,5)
                +randomString(stringType.SIGN,2)
                +randomString(stringType.NUMBERS,2));
    }

    public String randomString (stringType strT, int length){
        char[] chars;
                switch (strT){
                    case LOW_CASE_ENG: chars="abcdefghijklmnopqrstuvwxyz".toCharArray(); break;
                    case UP_CASE_ENG: chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(); break;
                    case CAMEL_CASE_ENG: chars="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(); break;
                    case ENG_NUM: chars="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray(); break;
                    case NUMBERS: chars="1234567890".toCharArray(); break;
                    case ALL: chars="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*()_+=?|".toCharArray(); break;
                    case CYRILLIC: chars="абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray(); break;
                    case SIGN: chars="~!@#$%^&*()_+=?|".toCharArray(); break;
                    default: chars="!@#$%^&*()_+=?|".toCharArray();
                }
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();}

    @Then("^I see username, e-mail, pass, confirm pass fields signUp and signUp buttons$")
    public void iSeeUsernameEMailPassConfirmPassFieldsSignUpAndSignUpButtons() {
                   boolean res=(driver.findElement(new By.ByCssSelector(data_username_input)).isDisplayed()
                    && driver.findElement(new By.ByCssSelector(data_email_input)).isDisplayed()
                    && driver.findElement(new By.ByCssSelector(data_pass_input)).isDisplayed()
                    && driver.findElement(new By.ByCssSelector(data_conf_pass_input)).isDisplayed()
                   && driver.findElement(new By.ByCssSelector(data_sign_up_btn)).isDisplayed()
                           && driver.findElement(new By.ByCssSelector(data_gmail_sign_up_btn)).isDisplayed());
            driver.quit();
            Assertions.assertEquals(true,res);

    }
}

enum stringType {
    LOW_CASE_ENG,
    UP_CASE_ENG,
    CAMEL_CASE_ENG,
    ENG_NUM,
    NUMBERS,
    ALL,
    CYRILLIC,
    SIGN
}

