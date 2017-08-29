package ut.programming.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

public class Application {
    
    public int countWords(String words) {
        String[] separateWords = StringUtils.split(words, ' ');
        return (separateWords == null) ? 0 : separateWords.length;
    }

    public void greet() {
	List<String> greetings = new ArrayList<>();
	greetings.add("Hello");

	for (String greeting : greetings) {
	    System.out.println("Greeting: " + greeting);
	}
    }
    
    public void dict() {
	Map<String, String> map = new HashMap<String, String>();
	map.put("name", "Alex Unstoppable");
	System.out.println("Dict: " + map.get("name"));
    }

    public Application() {
        System.out.println ("Inside Application");
    }

    // method main(): ALWAYS the APPLICATION entry point
    public static void main (String[] args) {
      	System.out.println ("Starting Application");
    	Application app = new Application();
        app.greet();
        int count = app.countWords("I have five words ha.");
        System.out.println("Word Count: " + count);
	app.dict();
    }
}
