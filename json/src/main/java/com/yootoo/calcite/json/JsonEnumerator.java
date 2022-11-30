package com.yootoo.calcite.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.Linq4j;
import java.util.ArrayList;
import java.util.List;

/**
 * @author it.liu
 * @description calcite-dql
 * @date 2022/11/30
 */
public class JsonEnumerator implements Enumerator<Object[]> {

    private Enumerator<Object[]> enumerator;

    public JsonEnumerator(JSONArray jsonArr) {
        List<Object[]> objs = new ArrayList();
        for (Object obj : jsonArr) {
            objs.add(((JSONObject) obj).values().toArray());
        }
        enumerator = Linq4j.enumerator(objs);
    }

    @Override
    public Object[] current() {
        return enumerator.current();
    }

    @Override
    public boolean moveNext() {
        return enumerator.moveNext();
    }

    @Override
    public void reset() {
        enumerator.reset();
    }

    @Override
    public void close() {
        enumerator.close();
    }

}
