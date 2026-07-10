package id.my.agungdh.testscraping;

import id.my.agungdh.testscraping.model.BlogData;
import id.my.agungdh.testscraping.scraper.BlogScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TestScrapingApplication implements CommandLineRunner {

    private final BlogScraper blogScraper;

    public TestScrapingApplication(BlogScraper blogScraper) {
        this.blogScraper = blogScraper;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestScrapingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Starting blog scraping...");
        BlogData data = blogScraper.scrape();

        log.info("\n========================================");
        log.info("  CATEGORIES ({})", data.getCategories().size());
        log.info("========================================");
        for (String category : data.getCategories()) {
            log.info("  - {}", category);
        }

        log.info("\n========================================");
        log.info("  TAGS ({})", data.getTags().size());
        log.info("========================================");
        for (String tag : data.getTags()) {
            log.info("  - {}", tag);
        }

        log.info("\n========================================");
        log.info("  BLOG POSTS ({})", data.getPosts().size());
        log.info("========================================");
        for (BlogData.BlogPost post : data.getPosts()) {
            log.info("  Title   : {}", post.getTitle());
            log.info("  URL     : {}", post.getUrl());
            log.info("  Date    : {}", post.getDate());
            log.info("  Category: {}", post.getCategory());
            log.info("  Tags    : {}", post.getTags());
            log.info("  Excerpt : {}", post.getExcerpt());
            log.info("----------------------------------------");
        }
    }
}
