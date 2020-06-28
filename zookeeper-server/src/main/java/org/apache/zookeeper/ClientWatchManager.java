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

package org.apache.zookeeper;

import java.util.Set;

/**
 * 客户端监听管理
 */
public interface ClientWatchManager {

    /**
     * Return a set of watchers that should be notified of the event. The
     * manager must not notify the watcher(s), however it will update it's
     * internal structure as if the watches had triggered. The intent being
     * that the callee is now responsible for notifying the watchers of the
     * event, possibly at some later time.
     * <p>
     * 根据 事件类型添加具体的 watcher 列表
     *
     * @param state event state 事件状态
     * @param type  event type 事件类型
     * @param path  event path 节点地址
     * @return may be empty set but must not be null 所有的事件
     */
    Set<Watcher> materialize(Watcher.Event.KeeperState state, Watcher.Event.EventType type, String path);

}
