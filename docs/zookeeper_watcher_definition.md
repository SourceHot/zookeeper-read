# zookeeper watcher 定义
> author: [huifer](https://github.com/huifer)
>
> git_repo : [zookeeper](https://github.com/SourceHot/zookeeper-read)
>
> 本系列使用的分支: https://github.com/apache/zookeeper/tree/release-3.6.1

- 类路径: `org.apache.zookeeper.Watcher`
- 在**Watcher**接口中定义如下内容
    1. zookeeper在watcher中存在的状态: `org.apache.zookeeper.Watcher.Event.KeeperState`
    1. 事件的类型: `org.apache.zookeeper.Watcher.Event.EventType`
    
    
## 事件类型
- `org.apache.zookeeper.Watcher.Event.EventType`
- 事件类型如下
    - None: 无
    - NodeCreated: 节点创建
    - NodeDeleted: 节点删除
    - NodeDataChanged: 节点数据变更
    - NodeChildrenChanged: 节点子集数据变更
    - DataWatchRemoved: 数据监控删除
    - ChildWatchRemoved: 子集监控删除
    - PersistentWatchRemoved: 持久化的监控删除
    
    
## zookeeper在watcher中存在的状态
-  `org.apache.zookeeper.Watcher.Event.KeeperState`
- 状态如下
    - Unknown: 未知状态
    - Disconnected: 断开
    - NoSyncConnected: 没有使用
    - SyncConnected: 链接的
    - AuthFailed: 身份验证失败
    - ConnectedReadOnly: 链接只读
    - SaslAuthenticated: 通过 SASL 验证
    - Expired: 超时
    - Closed: 关闭
    
    
- 两个枚举都提供了输入一个int类型的值转换成对应枚举的方法