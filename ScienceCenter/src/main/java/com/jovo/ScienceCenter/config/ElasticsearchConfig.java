package com.jovo.ScienceCenter.config;

import com.jovo.ScienceCenter.elasticsearch_plugin.serbian_analyzer.AnalysisSerbianPlugin;
import org.elasticsearch.Version;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.env.Environment;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jovo.ScienceCenter.repository.elasticsearch")
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port}")
    private int elasticsearchPort;

    //load value from application.property
    @Value("${elasticsearch.cluster-name}")
    private String clusterName;


    @Bean
    public TransportClient client() throws Exception {
        //return new PreBuiltTransportClient(Settings.EMPTY)
        //Builder settings = Settings.builder();
        //settings.put("settings.analysis.analyzer", "serbian_analyzer");
        return new PreBuiltTransportClient(Settings.EMPTY/*, Collections.singletonList(AnalysisSerbianPlugin.class)*/)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort));
    }

    /*@SuppressWarnings("resource")
    public Client nodeClient() {

        Builder settings = Settings.builder();
        //enable bind on localhost:9200 for node client
        //settings.put("index.analysis.analyzer.default.type", "serbian_analyzer");
        settings.put("node.local", true);
        settings.put("cluster.name", clusterName);
        //expose elasticsearch on http://localhost:9200
        settings.put("http.enabled", true);
        settings.put("path.home", "data");
        settings.put("path.log", "log");
        settings.put("index.analysis.version", "1.0.0");

        //Create node client with plugins, add plugins to built in elsticsearch
        Node node = null;
        try {
            node = new NodeWithPlugins(InternalSettingsPreparer.prepareEnvironment(settings.build(), null),
                    //Version.CURRENT,
                    Collections.singletonList(AnalysisSerbianPlugin.class)).start();
        } catch (NodeValidationException e) {
            e.printStackTrace();
        }
        return node.client();
    }*/

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
        //return new ElasticsearchTemplate(nodeClient());
    }

    /*private class NodeWithPlugins extends Node {
        protected NodeWithPlugins(Environment environment, Collection<Class<? extends Plugin>> list) {
            super(environment, list, true);
        }

        @Override
        protected void registerDerivedNodeNameWithLogger(String s) {

        }
    }*/


}
