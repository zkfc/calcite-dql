package com.yootoo.calcite;

import com.google.common.collect.Lists;
import lombok.Cleanup;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author it.liu
 * @description calcite sql execute context
 * @date 2022/11/29
 */
public class CalciteContext {
    private final String url = "jdbc:calcite:lex=MYSQL";
    private final Properties config = new Properties(){{setProperty("caseSensitive","false");}};
    private final String name;
    private final Schema schema;

    public CalciteContext(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
    }

    /**
     * execute sql
     * @param sql
     * @return
     * @throws Exception
     */
    public List<Map> execute(String sql){
        List<Map> records = Lists.newLinkedList();
        try {
            @Cleanup CalciteConnection c = createCalciteConnection();
            addSchema(c);
            @Cleanup ResultSet rs = c.createStatement().executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            while(rs.next()){
                Map record = new LinkedHashMap(){{
                    for(int i = 1; i <= md.getColumnCount(); i++){
                        put(md.getColumnName(i),rs.getObject(i));
                    }
                }};
                records.add(record);
            }
            return records;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

    protected void addSchema(CalciteConnection c){
        c.getRootSchema().add(name,schema);
    }

    private CalciteConnection createCalciteConnection(){
        try {
            CalciteConnection calciteConnection = DriverManager.getConnection(url,config).unwrap(CalciteConnection.class);
            return calciteConnection;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
