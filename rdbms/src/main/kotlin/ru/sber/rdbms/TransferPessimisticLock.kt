package ru.sber.rdbms

import java.lang.Long.max
import java.lang.Long.min
import java.sql.DriverManager
import java.sql.SQLException

class TransferPessimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val blockId1 = min(accountId1, accountId2)
        val blockId2 = max(accountId1, accountId2)

        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )
        connection.use { conn ->
            val autoCommit = conn.autoCommit
            try {
                conn.autoCommit = false
                var count: Int
                val prepareStatement1 = conn.prepareStatement("select * from account1 where id = ? for update")
                prepareStatement1.use { statement ->
                    statement.setLong(1, blockId1)
                    statement.executeQuery()
                }
                val prepareStatement2 = conn.prepareStatement("select * from account1 where id = ? for update")
                prepareStatement2.use { statement ->
                    statement.setLong(1, blockId2)
                    statement.executeQuery()
                }
                val prepareStatement3 = conn.prepareStatement("select * from account1 where id = ?")
                prepareStatement3.use { statement ->
                    statement.setLong(1, accountId1)
                    statement.executeQuery().use {
                        it.next()
                        count = it.getInt("amount")
                    }
                }
                if (count < amount) {
                    throw CountAmountException()
                }
                val prepareStatement4 = conn.prepareStatement("update account1 set amount = amount - ? where id = ?")
                prepareStatement4.use { statement ->
                    statement.setLong(1, amount)
                    statement.setLong(2, accountId1)
                    statement.executeUpdate()
                }
                val prepareStatement5 = conn.prepareStatement("update account1 set amount = amount + ? where id = ?")
                prepareStatement5.use { statement ->
                    statement.setLong(1, amount)
                    statement.setLong(2, accountId2)
                    statement.executeUpdate()
                }
                conn.commit()
            } catch (exception: SQLException) {
                println(exception.message)
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}
fun main(){
    var transferPessimisticLock = TransferPessimisticLock()
    transferPessimisticLock.transfer(1,2,10)
}
