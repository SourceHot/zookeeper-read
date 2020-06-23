# zookeeper 序列化与反序列化
> author: [huifer](https://github.com/huifer)
>
> git_repo : [zookeeper](https://github.com/SourceHot/zookeeper-read)
>


## 核心接口
- 序列化的顶层接口为: `org.apache.jute.OutputArchive`
- 反序列化的顶层接口为: `org.apache.jute.InputArchive` 
- 统筹接口为: `org.apache.jute.Record`


### OutputArchive
- 类路径: `org.apache.jute.OutputArchive`
    - 将各类不同数据类型进行序列化

```java
public interface OutputArchive {

    void writeByte(byte b, String tag) throws IOException;

    void writeBool(boolean b, String tag) throws IOException;

    void writeInt(int i, String tag) throws IOException;

    void writeLong(long l, String tag) throws IOException;

    void writeFloat(float f, String tag) throws IOException;

    void writeDouble(double d, String tag) throws IOException;

    void writeString(String s, String tag) throws IOException;

    void writeBuffer(byte[] buf, String tag)
            throws IOException;

    void writeRecord(Record r, String tag) throws IOException;

    void startRecord(Record r, String tag) throws IOException;

    void endRecord(Record r, String tag) throws IOException;

    void startVector(List<?> v, String tag) throws IOException;

    void endVector(List<?> v, String tag) throws IOException;

    void startMap(TreeMap<?, ?> v, String tag) throws IOException;

    void endMap(TreeMap<?, ?> v, String tag) throws IOException;

}
```

### InputArchive
- 类路径: `org.apache.jute.InputArchive`

```java
public interface InputArchive {

    /**
     * 反序列化为byte类型
     *
     * @param tag
     * @return
     * @throws IOException
     */
    byte readByte(String tag) throws IOException;

    boolean readBool(String tag) throws IOException;

    int readInt(String tag) throws IOException;

    long readLong(String tag) throws IOException;

    float readFloat(String tag) throws IOException;

    double readDouble(String tag) throws IOException;

    String readString(String tag) throws IOException;

    /**
     * 从缓冲中读取
     *
     * @param tag
     * @return
     * @throws IOException
     */
    byte[] readBuffer(String tag) throws IOException;

    /**
     * 开始读取
     *
     * @param r
     * @param tag
     * @throws IOException
     */
    void readRecord(Record r, String tag) throws IOException;

    /**
     * 开始读取
     *
     * @param tag
     * @throws IOException
     */
    void startRecord(String tag) throws IOException;

    /**
     * 结束读取
     *
     * @param tag
     * @throws IOException
     */
    void endRecord(String tag) throws IOException;

    Index startVector(String tag) throws IOException;

    void endVector(String tag) throws IOException;

    /**
     * 开始读取map
     *
     * @param tag
     * @return
     * @throws IOException
     */
    Index startMap(String tag) throws IOException;

    /**
     * 结束读取map
     *
     * @param tag
     * @throws IOException
     */
    void endMap(String tag) throws IOException;

}
```

### Record
- 类路径: `org.apache.jute.Record`

```java
@InterfaceAudience.Public
public interface Record {
    /**
     * 序列化
     *
     * @param archive 序列化接口的实现
     * @param tag     数据
     * @throws IOException
     */
    void serialize(OutputArchive archive, String tag) throws IOException;

    /**
     * 反序列化
     *
     * @param archive 反序列化接口的实现
     * @param tag     数据
     * @throws IOException
     */
    void deserialize(InputArchive archive, String tag) throws IOException;
}
```



### BinaryInputArchive
- 基本属性

```java
    /**
     * 读取数据的接口
     */
    private final DataInput in;
    /**
     * 最大缓存区大小
     */
    private final int maxBufferSize;
    /**
     * 额外缓冲区大小
     */
    private final int extraMaxBufferSize;
```

- 实现的方法: 这里实现方法大同小异就不具体贴出每一个了.主要依赖于 **DataInput** 进行操作
    
```java
    public byte readByte(String tag) throws IOException {
        return in.readByte();
    }
```


### BinaryOutputArchive
- 基本属性

```java
    /**
     * 数据写出接口
     */
    private final DataOutput out;
    /**
     * 缓冲区
     */
    private ByteBuffer bb = ByteBuffer.allocate(1024);
```

- 实现的方法主要依赖于 **DataOutput**

```java
    public void writeByte(byte b, String tag) throws IOException {
        out.writeByte(b);
    }
```



### BinaryIndex
- 类路径: `org.apache.jute.BinaryInputArchive.BinaryIndex`

```java
    /**
     * 二进制索引
     */
    private static class BinaryIndex implements Index {
        /**
         * 元素的数量
         */
        private int nelems;

        BinaryIndex(int nelems) {
            this.nelems = nelems;
        }

        /**
         * 是否完成
         *
         * @return
         */
        public boolean done() {
            return (nelems <= 0);
        }

        /**
         * 下一个
         */
        public void incr() {
            nelems--;
        }
    }
```