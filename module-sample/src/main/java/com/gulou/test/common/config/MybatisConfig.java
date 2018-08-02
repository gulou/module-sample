package com.gulou.test.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * <p>mybatis配置</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/01 12:14
 */

@Configuration
@MapperScan(basePackages = "com.gulou.test.*.dao",
        sqlSessionFactoryRef = "sqlSessionFactory") //扫描指定包下面的mapper
public class MybatisConfig {

    //xml文件位置
    private static final String MAPPER_LOCATION = "classpath:mapper/module/**/*Mapper.xml";

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //TODO gulou 实现对查询SQL的打印
}
