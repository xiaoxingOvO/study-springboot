package com.xx.hander;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author xiaoxing
 * @create 2021-12-19 14:07
 */
@Slf4j
@Component      //把处理器加载到IOC容器中
public class MyMetaObjectHander implements MetaObjectHandler {


    //插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        // 起始版本 3.3.0(推荐使用)
        // this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        // this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());

        // 或者 起始版本 3.3.3(推荐)
        // this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        // // 或者 也可以使用(3.3.0 该方法有bug)
        // this.fillStrategy(metaObject, "createTime", LocalDateTime.now());
        // this.setFieldValByName("createTime", new Date(), metaObject);
        // this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    //更新时填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 起始版本 3.3.0(推荐)
        //this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        // // 或者 起始版本 3.3.3(推荐)
        // this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
        // // 或者 也可以使用(3.3.0 该方法有bug)
        // this.fillStrategy(metaObject, "updateTime", LocalDateTime.now());
    }
}
