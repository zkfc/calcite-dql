/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yootoo.calcite.json;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.*;

/**
 * Schema mapped onto a rest-api
 */
class JsonSchema extends AbstractSchema {
  private Map<String,Table> tableMap;

  public JsonSchema(@NonNull String mappedTableName, @NonNull String target) {
    String jsonStr = !target.startsWith("[") ? '[' + target + ']' : target;
    this.tableMap = build(mappedTableName,jsonStr);
  }

  private Map<String,Table> build(String mappedTableName,String target){
    final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
    final Table table = new JsonTable(JSON.parseArray(target));
    builder.put(mappedTableName, table);
    return builder.build();
  }

  @Override
  protected Map<String, Table> getTableMap() {
    return tableMap;
  }
}
