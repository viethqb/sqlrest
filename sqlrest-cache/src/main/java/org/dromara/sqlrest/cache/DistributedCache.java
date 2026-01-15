// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.cache;

import java.util.concurrent.TimeUnit;

/**
 * Distributed cache interface definition
 */
public interface DistributedCache {

  /**
   * Get cache name
   *
   * @return String
   */
  String getName();

  /**
   * Get value by key
   *
   * @param key  key
   * @param type type
   * @param <T>  generic type
   * @return T
   */
  <T> T get(String key, Class<T> type);

  /**
   * Write cache value to cache
   *
   * @param key    key
   * @param value  value
   * @param expire expiration time
   * @param unit   expiration time unit
   */
  void put(String key, Object value, long expire, TimeUnit unit);

  /**
   * Delete cache data for specified key
   *
   * @param key key
   */
  void evict(String key);
}
