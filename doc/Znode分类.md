# znode 分类
1. 持久性的 znode( PERSISTENT):这样的znode创建之后即使发生 ZooKeeper集群宕机或者宕机也不会丢失。
2. 临时性的 znode( EPHEMERAL): client宕机者或者 client在指定的 timeout时间内没有给 ZooKeeper集群发消息,这样的 znode就会消失。
3. 持久顺序性的 znode( PERSISTENT_SEQUENTIAL): znode 除了具备持久性 znode的特点之外,znode的名字具备顺序性。
4. 临时顺序性的( EPHEMERAL SEQUENTIAL): znode除了具备临时性 znode的特点之外, znode的名字具备顺序性。