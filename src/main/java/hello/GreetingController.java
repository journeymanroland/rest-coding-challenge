package hello;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class GreetingController {
	@Autowired
	GreetingRepository greetingRepository;
    
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        System.out.println("foo!");
    	return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    @GetMapping("/greetings")
    public ArrayList<Greeting> getAllGreetings() {
    	Iterable<Greeting> result = greetingRepository.findAll();
    	ArrayList<Greeting> employeesList = new ArrayList<Greeting>();
    	result.forEach(employeesList::add);
    	return employeesList;
    }
    
    @PostMapping("/new-greeting")
    public Greeting newGreeting(@RequestBody Greeting greeting) {
    	Long id = Long.valueOf(new Random().nextInt());
    	Greeting g = new Greeting(id, greeting.getContent());
    	greetingRepository.save(g);
    	return g;
    }
    
    @DeleteMapping(value = "/greeting/{id}", produces = "application/json; charset=utf-8")
    public String deleteGreeting(@PathVariable String id) {
     Boolean result = greetingRepository.existsById(id);
     greetingRepository.deleteById(id);
     return "{ \"success\" : "+ (result ? "true" : "false") +" }";
    }
    
    @PutMapping("/update-greeting/{id}")
    public Optional<Greeting> updateEmployee(@RequestBody Greeting newGreeting, @PathVariable String id)
    {
     Optional<Greeting> optionalEmp = greetingRepository.findById(id);
     if (optionalEmp.isPresent()) {
    	Greeting g = optionalEmp.get();
    	g.setContent(newGreeting.getContent());
    	greetingRepository.save(g);
     }
     return optionalEmp;
    }
}
