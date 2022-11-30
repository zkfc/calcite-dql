package com.yootoo.calcite.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.Statistic;
import org.apache.calcite.schema.Statistics;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.util.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 * @author it.liu
 * @description json data mapped on a json table
 * @date 2022/11/30
 */
public class JsonTable extends AbstractTable implements ScannableTable {
    private final JSONArray jsonArr;

    public JsonTable(JSONArray obj) {
        this.jsonArr = obj;
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        final List<RelDataType> types = new ArrayList();
        final List<String> names = new ArrayList();
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            for (String k : jsonObj.keySet()) {
                if (names.contains(k)){
                    continue;
                }
                final RelDataType type = typeFactory.createJavaType(jsonObj.get(k).getClass());
                names.add(k);
                types.add(type);
            }
        }
        if (names.isEmpty()) {
            names.add("line");
            types.add(typeFactory.createJavaType(String.class));
        }
        return typeFactory.createStructType(Pair.zip(names, types));
    }

    @Override
    public Statistic getStatistic() {
        return Statistics.UNKNOWN;
    }

    @Override
    public Enumerable<Object[]> scan(DataContext root) {
        return new AbstractEnumerable<Object[]>() {
            @Override
            public Enumerator<Object[]> enumerator() {
                return new JsonEnumerator(jsonArr);
            }
        };
    }
}
