package ru.itpark.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itpark.Main;
import ru.itpark.annotation.Cached;
import ru.itpark.model.Post;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Component
public class RequestClient {

    @Value("${url}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Cached
    public String getPost(Integer id) {
        try {
            String postsJson = Files.readString(
                    Paths.get(
                            Objects.requireNonNull(
                                    Main.class.getClassLoader().getResource("posts.json")
                            ).toURI()));
            ObjectMapper objectMapper = new ObjectMapper();
            List<Post> posts = objectMapper.readValue(postsJson, new TypeReference<List<Post>>() {
            });
            return posts
                    .stream()
                    .filter(post -> id.equals(post.getId()))
                    .findFirst()
                    .map(Post::getBody)
                    .orElse(null);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "RequestClient{" +
                "url='" + url + '\'' +
                '}';
    }
}
