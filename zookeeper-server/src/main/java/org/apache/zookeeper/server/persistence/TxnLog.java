/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zookeeper.server.persistence;

import java.io.Closeable;
import java.io.IOException;

import org.apache.jute.Record;
import org.apache.zookeeper.server.ServerStats;
import org.apache.zookeeper.txn.TxnDigest;
import org.apache.zookeeper.txn.TxnHeader;

/**
 * Interface for reading transaction logs.
 * <p>
 * 事务日志接口
 */
public interface TxnLog extends Closeable {

    /**
     * Setter for ServerStats to monitor fsync threshold exceed
     *
     * @param serverStats used to update fsyncThresholdExceedCount
     */
    void setServerStats(ServerStats serverStats);

    /**
     * roll the current
     * log being appended to
     * <p>
     * 回滚日志
     *
     * @throws IOException
     */
    void rollLog() throws IOException;

    /**
     * Append a request to the transaction log
     * 添加一个事务请求到日志中
     *
     * @param hdr the transaction header
     * @param r   the transaction itself
     * @return true iff something appended, otw false
     * @throws IOException
     */
    boolean append(TxnHeader hdr, Record r) throws IOException;

    /**
     * Append a request to the transaction log with a digset
     * 添加一个事务请求到日志中
     *
     * @param hdr    the transaction header
     * @param r      the transaction itself
     * @param digest transaction digest
     *               returns true iff something appended, otw false
     * @throws IOException
     */
    boolean append(TxnHeader hdr, Record r, TxnDigest digest) throws IOException;

    /**
     * Start reading the transaction logs
     * from a given zxid
     * <p>
     * 读取事务日志
     *
     * @param zxid
     * @return returns an iterator to read the
     * next transaction in the logs. 事务迭代器
     * @throws IOException
     */
    TxnIterator read(long zxid) throws IOException;

    /**
     * the last zxid of the logged transactions.
     * <p>
     * 获取罪行的zxid
     *
     * @return the last zxid of the logged transactions.
     * @throws IOException
     */
    long getLastLoggedZxid() throws IOException;

    /**
     * truncate the log to get in sync with the
     * leader.
     * <p>
     * 清空日志保,与 leader 同步
     *
     * @param zxid the zxid to truncate at.
     * @throws IOException
     */
    boolean truncate(long zxid) throws IOException;

    /**
     * the dbid for this transaction log.
     * <p>
     * 获取数据库id
     *
     * @return the dbid for this transaction log.
     * @throws IOException
     */
    long getDbId() throws IOException;

    /**
     * commit the transaction and make sure
     * they are persisted
     * <p>
     * 提交事务
     *
     * @throws IOException
     */
    void commit() throws IOException;

    /**
     * 事务同步消费的时间,单位毫秒
     *
     * @return transaction log's elapsed sync time in milliseconds
     */
    long getTxnLogSyncElapsedTime();

    /**
     * close the transactions logs
     * <p>
     * 关闭事务日志
     */
    void close() throws IOException;

    /**
     * Gets the total size of all log files
     * 获取日志总大小
     */
    long getTotalLogSize();

    /**
     * Sets the total size of all log files
     * <p>
     * 设置日志总大小
     */
    void setTotalLogSize(long size);

    /**
     * an iterating interface for reading
     * transaction logs.
     * <p>
     * 事务迭代器
     */
    interface TxnIterator extends Closeable {

        /**
         * return the transaction header.
         * 事务头部
         *
         * @return return the transaction header.
         */
        TxnHeader getHeader();

        /**
         * return the transaction record.
         * <p>
         * 获取事务
         *
         * @return return the transaction record.
         */
        Record getTxn();

        /**
         * 事务摘要
         *
         * @return the digest associated with the transaction.
         */
        TxnDigest getDigest();

        /**
         * go to the next transaction record.
         * <p>
         * 下一个事务
         *
         * @throws IOException
         */
        boolean next() throws IOException;

        /**
         * close files and release the
         * resources
         * <p>
         * 关闭事务
         *
         * @throws IOException
         */
        void close() throws IOException;

        /**
         * Get an estimated storage space used to store transaction records
         * that will return by this iterator
         * <p>
         * 获取事务的大小
         *
         * @throws IOException
         */
        long getStorageSize() throws IOException;

    }

}

