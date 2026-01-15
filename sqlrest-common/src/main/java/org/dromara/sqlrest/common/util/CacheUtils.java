// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class CacheUtils {

  // Cache duration: 2 hours
  public static final long CACHE_DURATION_SECONDS = 7200L;

  private static Cache<String, Object> loadingCache = CacheBuilder.newBuilder()
      /* Set initial capacity of cache container to 10 */
      .initialCapacity(10)
      /* Set maximum capacity of cache container to 1000 */
      .maximumSize(1000)
      /* Enable cache hit rate recording */
      .recordStats()
      /* Set concurrency level to 8, the concurrency level value determines the number of threads that can write cache simultaneously */
      .concurrencyLevel(8)
      /* Set expiration time to 2 hours */
      .expireAfterAccess(CACHE_DURATION_SECONDS, TimeUnit.SECONDS)
      .build();

  public static void put(String key, Object value) {
    loadingCache.put(key, value);
  }

  public static Object get(String key) {
    return loadingCache.getIfPresent(key);
  }

  public static void remove(String key) {
    loadingCache.invalidate(key);
  }

  public static void clear() {
    loadingCache.invalidateAll();
  }

  public static Map<String, Object> getAll() {
    return loadingCache.asMap();
  }

  public static Collection<Object> getAllValue() {
    return loadingCache.asMap().values();
  }

}