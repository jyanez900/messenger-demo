package coop.nisc.jacobdemo;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:Features", plugin = {"pretty", "json:target/cucumber-json-report.json"})
public class CucumberTests {

}
