package ru.itpark.service;

import org.springframework.stereotype.Component;
import ru.itpark.client.RequestClient;

@Component
public class PostService {

    private RequestClient client;

    public PostService(RequestClient client) {
        this.client = client;
    }

    public String getPost(Integer id) {
        return client.getPost(id);
    }

    public RequestClient getClient() {
        return client;
    }
}
