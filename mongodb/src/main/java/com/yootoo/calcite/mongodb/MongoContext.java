package com.yootoo.calcite.mongodb;

import com.mongodb.client.MongoDatabase;
import com.yootoo.calcite.CalciteContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.adapter.mongodb.MongoSchemaFactory;

/**
 * @author it.liu
 * @description calcite mongodb sql execute context
 * @date 2022/7/22
 */
@Slf4j
@Getter
public class MongoContext extends CalciteContext{

    public MongoContext(MongoDatabase mongoDatabase) {
        super(mongoDatabase.getName(), MongoSchemaFactory.create(mongoDatabase));
    }
}
