package com.spring.blog.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
//@MapperScan(basePackages = "com.spring.blog.mapper")
@MapperScan(basePackages = {"com.spring.blog.user.mapper", "com.spring.blog.post.mapper"})
public class MybatisConfig {    
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        
        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml");
        sessionFactory.setConfigLocation(myBatisConfig);
        
        //sessionFactory.setTypeAliasesPackage("com.spring.blog.vo");
        sessionFactory.setTypeAliasesPackage("com.spring.blog.user.vo, com.spring.blog.post.vo");

        
        return sessionFactory.getObject();
    }

}
