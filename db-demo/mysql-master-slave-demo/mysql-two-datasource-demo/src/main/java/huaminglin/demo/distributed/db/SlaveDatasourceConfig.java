package huaminglin.demo.distributed.db;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages = "huaminglin.demo.distributed.db.repository.slave",
    sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDatasourceConfig {

  @Bean(name = "slaveDataSourceProperties")
  @ConfigurationProperties(prefix = "datasource.slave")
  public DataSourceProperties slaveDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "slaveDataSource")
  public DataSource slaveDataSource(
      @Qualifier("slaveDataSourceProperties") DataSourceProperties slaveDataSourceProperties) {
    return slaveDataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Bean("slaveSqlSessionFactory")
  public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(slaveDataSource);
//    String locationPattern = "classpath*:huaminglin.demo.distributed.db.repository.slave/*.xml";
//    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));
    // TODO What is the default location to load mapper?
    return bean.getObject();
  }

//  @Primary
//  @Bean("slaveSqlSessionTemplate")
//  public SqlSessionTemplate slaveSqlSessionTemplate(SqlSessionFactory slaveSqlSessionFactory) {
//    return new SqlSessionTemplate(slaveSqlSessionFactory);
//  }
  // TODO: What does SqlSessionTemplate do?
}
