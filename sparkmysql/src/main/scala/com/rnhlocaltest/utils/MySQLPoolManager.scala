package com.rnhlocaltest.utils

import java.io.Serializable
import java.sql.Connection

import com.mchange.v2.c3p0.ComboPooledDataSource

/**
  * Created with IntelliJ IDEA.
  * Author: fly_elephant@163.com
  * Description:MySQL连接池管理类
  * Date: Created in 2018-11-17 12:43
  * https://github.com/flyelephant/spark_mysql.git
  */
object MySQLPoolManager {
    var mysqlManager: MysqlPool = _

    var driverClass: String = "com.mysql.jdbc.Driver"
    var minPoolSize: Int = 5
    var maxPoolSize: Int = 20
    var acquireIncrement: Int = 5
    var maxStatements: Int = 180

    def getMysqlManager: MysqlPool = {
        synchronized {
            if (mysqlManager == null) {
                mysqlManager = new MysqlPool
            }
        }
        mysqlManager
    }

    class MysqlPool extends Serializable {
        private val cpds: ComboPooledDataSource = new ComboPooledDataSource(true)
        try {
            cpds.setJdbcUrl(DataSourceConfig.url)
            cpds.setDriverClass(driverClass)
            cpds.setUser(DataSourceConfig.username)
            cpds.setPassword(DataSourceConfig.password)
            cpds.setMinPoolSize(minPoolSize)
            cpds.setMaxPoolSize(maxPoolSize)
            cpds.setAcquireIncrement(acquireIncrement)
            cpds.setMaxStatements(maxStatements)
        } catch {
            case e: Exception => e.printStackTrace()
        }

        def getConnection: Connection = {
            try {
                cpds.getConnection()
            } catch {
                case ex: Exception =>
                    ex.printStackTrace()
                    null
            }
        }

        def close(): Unit = {
            try {
                cpds.close()
            } catch {
                case ex: Exception =>
                    ex.printStackTrace()
            }
        }
    }

}

