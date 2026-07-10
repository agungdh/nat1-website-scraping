package id.my.agungdh.testscraping.scraper;

import com.microsoft.playwright.*;
import id.my.agungdh.testscraping.model.BlogData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogScraper {

    public BlogData scrape() {
        log.info("Launching Playwright browser...");
        boolean headless = !"false".equalsIgnoreCase(System.getenv("HEADLESS"));
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            Page page = browser.newPage();

            log.info("Navigating to homepage...");
            page.navigate("https://nat1-website.agungdh.my.id");

            log.info("Clicking Blog button...");
            page.locator("a.nav-link[href='/blog']").click();

            page.waitForLoadState();

            log.info("Scraping blog posts...");
            List<BlogData.BlogPost> posts = new ArrayList<>();
            Set<String> allCategories = new LinkedHashSet<>();
            Set<String> allTags = new LinkedHashSet<>();

            var cards = page.locator(".blog-card").all();
            log.info("Found {} blog cards", cards.size());

            for (var card : cards) {
                String category = card.locator(".blog-card-cat-link").innerText();
                String title = card.locator(".blog-card-title-link").innerText();
                String excerpt = card.locator(".blog-card-excerpt").innerText();
                String date = card.locator(".blog-card-date").innerText();
                String url = card.locator(".blog-card-title-link").getAttribute("href");

                List<String> tagNames = new ArrayList<>();
                var tagElements = card.locator(".blog-card-tag").all();
                for (var tag : tagElements) {
                    String tagName = tag.innerText();
                    tagNames.add(tagName);
                    allTags.add(tagName);
                }

                allCategories.add(category);

                posts.add(BlogData.BlogPost.builder()
                        .title(title)
                        .excerpt(excerpt)
                        .category(category)
                        .tags(tagNames)
                        .date(date)
                        .url(url)
                        .build());
            }

            BlogData result = BlogData.builder()
                    .categories(allCategories)
                    .tags(allTags)
                    .posts(posts)
                    .build();

            log.info("Scraping complete. {} categories, {} tags, {} posts",
                    allCategories.size(), allTags.size(), posts.size());

            browser.close();
            return result;
        }
    }
}
