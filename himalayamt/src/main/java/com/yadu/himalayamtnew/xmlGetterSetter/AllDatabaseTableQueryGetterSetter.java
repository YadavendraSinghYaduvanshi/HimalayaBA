package com.yadu.himalayamtnew.xmlGetterSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeevanp on 08-12-2017.
 */

public class AllDatabaseTableQueryGetterSetter {

    public String getSql_query() {
        return sql_query;
    }

    public void setSql_query(String sql_query) {
        this.sql_query = sql_query;
    }

    String sql_query;
    public List getDatalist() {
        return datalist;
    }

    public void setDatalist(List datalist) {
        this.datalist = datalist;
    }

    List datalist =new ArrayList();

}
