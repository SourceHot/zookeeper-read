# zookeeper server exit code 
> author: [huifer](https://github.com/huifer)
>
> git_repo : [zookeeper](https://github.com/SourceHot/zookeeper-read)
>
> 本系列使用的分支: https://github.com/apache/zookeeper/tree/release-3.6.1

- zookeeper 服务退出码

- `org.apache.zookeeper.server.ExitCode`
```java
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
```