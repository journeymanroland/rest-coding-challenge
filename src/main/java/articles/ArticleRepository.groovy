package articles

import org.springframework.data.repository.CrudRepository

interface ArticleRepository extends CrudRepository<Article, String> {
}
