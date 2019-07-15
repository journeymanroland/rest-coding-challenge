package articles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate;

@RestController
class ArticleController {
	@Autowired
	ArticleRepository articleRepository;
	
	@GetMapping("/all-articles")
	public ArrayList<Article> getArticles() {
		def result = articleRepository.findAll();
		def articlesList = [];
		articlesList.addAll(result);
		return articlesList;
	}
	
	@GetMapping("/article/{id}")
	public Optional<Article> getArticle(@PathVariable int id) {
		def result = articleRepository.findById(id);
		if (result.isPresent()) {
			return result;
		}
	}
	
	@PostMapping("/new-article")
	public Article createNewArticle(@RequestBody Article article) {
		def id = Integer.valueOf(new Random().nextInt());
		def now = LocalDate.now().toString();
		def a = new Article(id, article.getTitle(), article.getDescription(), article.getAuthor(),
			article.getTags(), now, now);
		articleRepository.save(a);
		return a;
	}
	
	@DeleteMapping(value = "/article/{id}", produces = "application/json; charset=utf-8")
	public String deleteArticle(@PathVariable int id) {
		def result = articleRepository.existsById(id);
		articleRepository.deleteById(id);
		return "{ \"success\" : "+ (result ? "true" : "false") +" }";
	}
	
	// set content of article
	@PutMapping("/update-article/{id}")
	public Optional<Article> updateEmployee(@RequestBody Article newArticle, @PathVariable int id)
	{
	 def optionalArticle = articleRepository.findById(id)
	 if (optionalArticle.isPresent()) {
		def a = optionalArticle.get();
		a.setTitle(newArticle.getTitle());
		a.setDescription(newArticle.getDescription());
		a.setAuthor(newArticle.getAuthor());
		a.addTags(newArticle.getTags());
		a.updateArticle();
		articleRepository.save(a);
	 }
	 return optionalArticle;
	}
}
