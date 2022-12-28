package com.example.lostfoundserver.dao

import com.alibaba.druid.pool.DruidDataSource

object DataSource {
    val dataSource = DruidDataSource().apply {
        username = "root"
        password = "root"
        url = "jdbc:mysql://127.0.0.1:3306/lostfound?characterEncoding=utf-8"
        driverClassName = "com.mysql.cj.jdbc.Driver"
    }
}