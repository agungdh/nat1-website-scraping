package id.my.agungdh.testscraping.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogData {
    private Set<String> categories;
    private Set<String> tags;
    private List<BlogPost> posts;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogPost {
        private String title;
        private String excerpt;
        private String category;
        private List<String> tags;
        private String date;
        private String url;
    }
}
