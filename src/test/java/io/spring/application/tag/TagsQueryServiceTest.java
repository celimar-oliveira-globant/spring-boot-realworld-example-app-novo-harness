package io.spring.application.tag;

import io.spring.application.TagsQueryService;
import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.infrastructure.DbTestBase;
import io.spring.infrastructure.repository.MyBatisArticleRepository;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({TagsQueryService.class, MyBatisArticleRepository.class})
public class TagsQueryServiceTest extends DbTestBase {
  @Autowired private TagsQueryService tagsQueryService;

  @Autowired private ArticleRepository articleRepository;

  @Test
  public void should_get_all_tags_sorted_ascending() {
    articleRepository.save(
        new Article("first article", "test", "test", Arrays.asList("zeta", "alpha"), "123"));
    articleRepository.save(
        new Article("second article", "test", "test", Arrays.asList("gamma"), "123"));

    Assertions.assertIterableEquals(
        Arrays.asList("alpha", "gamma", "zeta"), tagsQueryService.allTags());
  }

  @Test
  public void should_return_empty_list_when_there_are_no_tags() {
    Assertions.assertIterableEquals(Collections.emptyList(), tagsQueryService.allTags());
  }
}
