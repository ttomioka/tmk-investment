package jp.tmkworks.tmk_investment.util.jooq;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "util.jooq.partitionedBulkInsert")
public class JooqUtils {
  private static final int DEFAULT_PARTITION_SIZE = 400_000;

  public static <R extends Record> void partitionedBulkInsert(DSLContext jooq, Table<R> table, List<R> fullList) {
    partitionedBulkInsert(jooq, table, fullList, DEFAULT_PARTITION_SIZE);
  }

  public static <R extends Record> void partitionedBulkInsert(DSLContext jooq, Table<R> table, List<R> fullList, int partitionSize) {
    int fullSize = fullList.size();
    log.debug("Partitioned BulkInsert process started. total size: {}", String.format("%,d", fullSize));

    var partitionedLists = Lists.partition(fullList, partitionSize);
    int processCount = 1;
    for (var partitionedNewRecords : partitionedLists) {
      jooq.insertInto(table, table.fields())
          .valuesOfRecords(partitionedNewRecords)
          .execute();

      int processSize = partitionedLists.size();
      var insertedSize = String.format("%,d", partitionedNewRecords.size());
      log.debug("{} / {} partitioned bulk inseret process done. insertedSize: {}", processCount++, processSize, insertedSize);
    }

    log.debug("Partitioned BulkInsert process completed.");
  }

}
