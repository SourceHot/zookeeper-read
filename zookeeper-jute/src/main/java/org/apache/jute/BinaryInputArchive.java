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

package org.apache.jute;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 二进制反序列化接口实现
 */
public class BinaryInputArchive implements InputArchive {

    public static final String UNREASONBLE_LENGTH = "Unreasonable length = ";

    /**
     * CHECKSTYLE.OFF: ConstantName - for backward compatibility
     * <p>
     * 最大缓存
     */
    public static final int maxBuffer = Integer.getInteger("jute.maxbuffer", 0xfffff);
    // CHECKSTYLE.ON:
    private static final int extraMaxBuffer;

    static {
        final Integer configuredExtraMaxBuffer =
                Integer.getInteger("zookeeper.jute.maxbuffer.extrasize", maxBuffer);
        if (configuredExtraMaxBuffer < 1024) {
            // Earlier hard coded value was 1024, So the value should not be less than that value
            extraMaxBuffer = 1024;
        } else {
            extraMaxBuffer = configuredExtraMaxBuffer;
        }
    }

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

    /**
     * Creates a new instance of BinaryInputArchive.
     */
    public BinaryInputArchive(DataInput in) {
        this(in, maxBuffer, extraMaxBuffer);
    }

    public BinaryInputArchive(DataInput in, int maxBufferSize, int extraMaxBufferSize) {
        this.in = in;
        this.maxBufferSize = maxBufferSize;
        this.extraMaxBufferSize = extraMaxBufferSize;
    }

    public static BinaryInputArchive getArchive(InputStream strm) {
        return new BinaryInputArchive(new DataInputStream(strm));
    }

    /**
     * 读取
     *
     * @param tag
     * @return
     * @throws IOException
     */
    public byte readByte(String tag) throws IOException {
        return in.readByte();
    }

    public boolean readBool(String tag) throws IOException {
        return in.readBoolean();
    }

    public int readInt(String tag) throws IOException {
        return in.readInt();
    }

    public long readLong(String tag) throws IOException {
        return in.readLong();
    }

    public float readFloat(String tag) throws IOException {
        return in.readFloat();
    }

    public double readDouble(String tag) throws IOException {
        return in.readDouble();
    }

    public String readString(String tag) throws IOException {
        int len = in.readInt();
        if (len == -1) {
            return null;
        }
        checkLength(len);
        byte[] b = new byte[len];
        in.readFully(b);
        return new String(b, StandardCharsets.UTF_8);
    }

    public byte[] readBuffer(String tag) throws IOException {
        int len = readInt(tag);
        if (len == -1) {
            return null;
        }
        checkLength(len);
        byte[] arr = new byte[len];
        in.readFully(arr);
        return arr;
    }

    public void readRecord(Record r, String tag) throws IOException {
        r.deserialize(this, tag);
    }

    public void startRecord(String tag) throws IOException {
    }

    public void endRecord(String tag) throws IOException {
    }

    public Index startVector(String tag) throws IOException {
        int len = readInt(tag);
        if (len == -1) {
            return null;
        }
        return new BinaryIndex(len);
    }

    public void endVector(String tag) throws IOException {
    }

    public Index startMap(String tag) throws IOException {
        return new BinaryIndex(readInt(tag));
    }

    public void endMap(String tag) throws IOException {
    }

    // Since this is a rough sanity check, add some padding to maxBuffer to
    // make up for extra fields, etc. (otherwise e.g. clients may be able to
    // write buffers larger than we can read from disk!)
    private void checkLength(int len) throws IOException {
        if (len < 0 || len > maxBufferSize + extraMaxBufferSize) {
            throw new IOException(UNREASONBLE_LENGTH + len);
        }
    }

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
}
