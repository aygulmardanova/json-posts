import org.springframework.core.io.ClassPathResource
import ru.itpark.client.RequestClient
import ru.itpark.processor.CachedAnnotationBPP
import ru.itpark.processor.PlaceholderSubstitutionBFPP
import ru.itpark.service.PostService

beans {

    propertyPlaceholderConfigurer(PlaceholderSubstitutionBFPP) {
        locations = new ClassPathResource("connection.properties");
    }

    beanPostProcessor(CachedAnnotationBPP) {}

    requestClient(RequestClient) {}

    postService(PostService, ref(requestClient)) {}

}