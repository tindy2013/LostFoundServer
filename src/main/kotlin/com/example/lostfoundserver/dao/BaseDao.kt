package com.example.lostfoundserver.dao

import org.apache.commons.dbutils.QueryRunner

abstract class BaseDao {
    private val source = DataSource
    protected val runner = QueryRunner(source.dataSource)
    data class Limit(var page: Int, var count: Int) {
        val begin = (page - 1) * count
    }
}