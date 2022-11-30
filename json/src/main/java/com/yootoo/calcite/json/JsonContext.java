package com.yootoo.calcite.json;

import com.yootoo.calcite.CalciteContext;
import org.apache.calcite.schema.Schema;

/**
 * @author it.liu
 * @description calcite-dql
 * @date 2022/11/30
 */
public class JsonContext extends CalciteContext {
    public JsonContext(String name, Schema schema) {
        super(name, schema);
    }
}
