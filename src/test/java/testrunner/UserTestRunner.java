package testrunner;

import com.github.javafaker.Faker;
import controller.User;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class UserTestRunner extends Setup {
    public UserTestRunner() throws IOException {
        initConfig();
    }
    @Test(priority = 1, description = "Calling user login")
    public void doLogin() throws ConfigurationException, IOException {
        User user = new User();
        user.doLogin("salman@roadtocareer.net", "1234");
    }
    @Test(priority = 2, description = "Create new user")
    public void createNewUser() throws IOException, ConfigurationException {
        User user = new User();
        int randomId = Utils.generateRandomId(1000, 9999);
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = "test" + randomId + "@test.com";
        String phone_number = "0150" + randomId + "100";
        JsonPath res = user.createUser(name, email, "1234", phone_number, "1234567", "Customer");
        String message = res.get("message");
        int id = res.get("user.id");
        System.out.println(message);
        System.out.println("New user id is " + id);
        Utils.saveEnvVar("userId", String.valueOf(id));
        Assert.assertTrue(message.contains("User created"));

    }

    @Test(priority = 3, description = "Get user info")
    public void getUserInfo() throws IOException {
        User user = new User();
        user.getUserInfo(prop.getProperty("userId"));
    }


}
