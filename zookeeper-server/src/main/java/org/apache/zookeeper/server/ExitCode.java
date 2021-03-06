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

package org.apache.zookeeper.server;

/**
 * Exit code used to exit server
 * <p>
 * 服务器退出码
 */
public enum ExitCode {

    /**
     * Execution finished normally
     * 正常执行完成的退出码.
     */
    EXECUTION_FINISHED(0),

    /**
     * Unexpected errors like IO Exceptions
     * 意外的退出状态码, 如 IO 异常
     */
    UNEXPECTED_ERROR(1),

    /**
     * Invalid arguments during invocations
     * 调用期间的参数错误状态码
     */
    INVALID_INVOCATION(2),

    /**
     * Cannot access datadir when trying to replicate server
     *
     * 尝试复制服务的时候无法读取 data_dir 文件
     */
    UNABLE_TO_ACCESS_DATADIR(3),

    /**
     * Unable to start admin server at ZooKeeper startup
     * 无法再 zookeeper 启动的时候启动管理服务器
     */
    ERROR_STARTING_ADMIN_SERVER(4),

    /**
     * Severe error during snapshot IO
     * 服务快照期间出现的错误
     */
    TXNLOG_ERROR_TAKING_SNAPSHOT(10),

    /**
     * zxid from COMMIT does not match the one from pendingTxns queue
     *
     * commit 的 zxid 和 pendingTxns 队列中的不匹配
     */
    UNMATCHED_TXN_COMMIT(12),

    /**
     * Unexpected packet from leader, or unable to truncate log on Leader.TRUNC
     *
     * 意外的 leader 数据包
     */
    QUORUM_PACKET_ERROR(13),

    /**
     * Unable to bind to the quorum (election) port after multiple retry
     *
     * 多次重试后无法绑定 端口
     */
    UNABLE_TO_BIND_QUORUM_PORT(14);

    private final int value;

    ExitCode(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }

}
