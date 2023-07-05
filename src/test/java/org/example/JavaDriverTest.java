package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.CassandraContainer;

import java.io.IOException;
import java.net.InetSocketAddress;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JavaDriverTest {

    private CassandraContainer container;

    @BeforeAll
    public void init() throws IOException {
        container = (CassandraContainer) new CassandraContainer("cassandra:latest").withExposedPorts(9042);
        container.start();
    }

    @AfterAll
    public void close() {
        if (container != null) {
            container.stop();
        }
    }

    @Test
    public void test() {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(container.getHost(), container.getMappedPort(9042)))
                .withLocalDatacenter(container.getLocalDatacenter())
                .build();
        session.close();
    }

}
