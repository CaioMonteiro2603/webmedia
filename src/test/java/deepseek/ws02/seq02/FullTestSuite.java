package deepseek.ws02.seq02;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    LoginPageTest.class,
    InventoryPageTest.class,
    CartPageTest.class,
    CheckoutTest.class
})
public class FullTestSuite {
    // This class serves as a test suite to run all tests
}