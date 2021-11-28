package huaminglin.demo.distributed.db;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MapperScan(basePackages = "huaminglin.demo.distributed.db.repository.master",
    sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDatasourceConfig {

  @Primary
  @Bean(name = "masterDataSourceProperties")
  @ConfigurationProperties(prefix = "datasource.master")
  public DataSourceProperties masterDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "masterDataSource")
  public DataSource masterDataSource(
      @Qualifier("masterDataSourceProperties") DataSourceProperties masterDataSourceProperties) {
    return masterDataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Primary
  @Bean("masterSqlSessionFactory")
  public SqlSessionFactory masterSqlSessionFactory(DataSource masterDataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(masterDataSource);
//    String locationPattern = "classpath*:huaminglin.demo.distributed.db.repository.master/*.xml";
//    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));
    // TODO What is the default location to load mapper?
    return bean.getObject();
  }

//  @Primary
//  @Bean("masterSqlSessionTemplate")
//  public SqlSessionTemplate masterSqlSessionTemplate(SqlSessionFactory masterSqlSessionFactory) {
//    return new SqlSessionTemplate(masterSqlSessionFactory);
//  }
  // TODO: What does SqlSessionTemplate do?
}
