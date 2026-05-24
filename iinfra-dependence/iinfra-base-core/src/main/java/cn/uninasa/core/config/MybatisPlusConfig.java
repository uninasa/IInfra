package cn.uninasa.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 自动填充处理器
     * 统一使用 Long 类型时间戳（毫秒）
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                Long currentTimeMillis = System.currentTimeMillis();
                
                // 填充时间字段（Long 类型时间戳）
                this.strictInsertFill(metaObject, "createTime", Long.class, currentTimeMillis);
                this.strictInsertFill(metaObject, "updateTime", Long.class, currentTimeMillis);
                
                // 填充创建人和更新人（默认为 "system"，后续集成 Spring Security 后从上下文获取）
                this.strictInsertFill(metaObject, "createBy", String.class, "system");
                this.strictInsertFill(metaObject, "updateBy", String.class, "system");
                
                // 填充逻辑删除标记（默认为未删除）
                this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                Long currentTimeMillis = System.currentTimeMillis();
                
                // 填充更新时间（Long 类型时间戳）
                this.strictUpdateFill(metaObject, "updateTime", Long.class, currentTimeMillis);
                
                // 填充更新人（默认为 "system"，后续集成 Spring Security 后从上下文获取）
                this.strictUpdateFill(metaObject, "updateBy", String.class, "system");
            }
        };
    }
}
