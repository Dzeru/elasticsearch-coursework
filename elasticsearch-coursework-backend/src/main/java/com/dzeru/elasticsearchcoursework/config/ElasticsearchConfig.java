package com.dzeru.elasticsearchcoursework.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.dzeru.elasticsearchcoursework.repositories"})
public class ElasticsearchConfig {
    static {
        // https://discuss.elastic.co/t/elasticsearch-5-4-1-availableprocessors-is-already-set/88036/8
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${elasticsearch.address}")
    private String elasticsearchAddress;

    @Value("${elasticsearch.port}")
    private Integer elasticsearchPort;

    @Bean
    public Client client() throws UnknownHostException {
        Settings elasticsearchSettings = Settings.builder()
                .put("cluster.name", clusterName)
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(elasticsearchSettings);
        transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchAddress), elasticsearchPort));
        return transportClient;
    }
}
