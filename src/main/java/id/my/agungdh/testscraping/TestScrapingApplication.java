package id.my.agungdh.testscraping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import id.my.agungdh.testscraping.model.BlogData;
import id.my.agungdh.testscraping.scraper.BlogScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
    public void run(String... args) throws Exception {
        log.info("Starting blog scraping...");
        BlogData data = blogScraper.scrape();

        String outputDir = System.getenv().getOrDefault("OUTPUT_DIR", ".");
        Path outputDirPath = Path.of(outputDir);
        Files.createDirectories(outputDirPath);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path outputPath = outputDirPath.resolve("blog-data-" + timestamp + ".json");
        mapper.writeValue(outputPath.toFile(), data);

        log.info("Blog data written to {}", outputPath.toAbsolutePath());
        log.info("Categories: {}, Tags: {}, Posts: {}",
                data.getCategories().size(), data.getTags().size(), data.getPosts().size());
    }
}
