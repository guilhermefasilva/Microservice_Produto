package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "acceptance/steps",
		features = "src/test/java/resources",
			monochrome = true,
			publish = true)
public class TestRunner {

}
