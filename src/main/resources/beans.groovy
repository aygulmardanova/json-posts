import org.springframework.core.io.ClassPathResource
import ru.itpark.client.RequestClient
import ru.itpark.processor.CachedAnnotationBPP
import ru.itpark.processor.PlaceholderSubstitutionBFPP
import ru.itpark.service.PostService

beans {

    propertyPlaceholderConfigurer(PlaceholderSubstitutionBFPP) {
        locations = new ClassPathResource("connection.properties");
    }

    xmlns([ctx:'http://www.springframework.org/schema/context'])
    ctx.'annotation-config'(true)

    beanPostProcessor(CachedAnnotationBPP) {}

    requestClient(RequestClient) {}

    postService(PostService, ref(requestClient)) {}

}