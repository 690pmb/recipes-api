package pmb.recipes

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestRecipesApplication {

	@Bean
	@ServiceConnection
	fun elasticsearchContainer(): ElasticsearchContainer {
		return ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.17.10"))
	}

	@Bean
	@ServiceConnection
	fun mariaDbContainer(): MariaDBContainer<*> {
		return MariaDBContainer(DockerImageName.parse("mariadb:latest"))
	}

}

fun main(args: Array<String>) {
	fromApplication<RecipesApplication>().with(TestRecipesApplication::class).run(*args)
}
