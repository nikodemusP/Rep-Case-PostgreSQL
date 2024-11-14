package com.example.starter;

import java.sql.JDBCType;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.jdbcclient.JDBCConnectOptions;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.jdbcclient.SqlOutParam;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Tuple;

public class RepCaseMain extends AbstractVerticle {

  /*
   * create or replace procedure repcase( repcase_para OUT text) as $$ BEGIN repcase_para := 'Hallo'; END; $$ LANGUAGE PLPGSQL;
   */

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

      JDBCConnectOptions jdbcOptions = new JDBCConnectOptions()
                    // H2 connection string
                    .setJdbcUrl("jdbc:postgresql://localhost:5432/template1")
                    .setUser("postgres")
                    .setPassword("postgres");
      PoolOptions poolOptions = new PoolOptions()
                                      .setMaxSize(2)
                                      .setName("pool-name");
      JDBCPool pool = JDBCPool.pool(vertx, jdbcOptions, poolOptions);

      startPromise.complete();

      pool.preparedQuery("{call repcase(?)}")
        .execute(Tuple.of(SqlOutParam.OUT(JDBCType.VARCHAR)))
        .onFailure( err -> err.printStackTrace() )
        .onSuccess( succ -> System.out.println(succ.rowCount()) );
  }
}
