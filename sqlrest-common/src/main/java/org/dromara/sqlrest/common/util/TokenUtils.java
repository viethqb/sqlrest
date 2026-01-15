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

import cn.hutool.core.util.HexUtil;
import java.security.MessageDigest;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Token utility class
 */
@Slf4j
public final class TokenUtils {

  public static String getRequestToken(HttpServletRequest httpRequest) {
    // Get token from header
    String authorization = httpRequest.getHeader("Authorization");
    if (!StringUtils.isEmpty(authorization)) {
      String[] splitString = authorization.split(" ");
      if (splitString.length == 2 && "Bearer".equalsIgnoreCase(splitString[0])) {
        return splitString[1];
      }
    }

    // If token does not exist in header, get token from parameters
    if (StringUtils.isEmpty(authorization)) {
      return httpRequest.getParameter("token");
    }

    return null;
  }

  public static String generateValue() {
    return generateValue(UUID.randomUUID().toString());
  }

  public static int getTokenStringLength() {
    return "9097ac1ab13198dfa4ddb2ecc1079693".length();
  }

  private static String generateValue(String param) {
    try {
      MessageDigest algorithm = MessageDigest.getInstance("MD5");
      algorithm.reset();
      algorithm.update(param.getBytes());
      byte[] messageDigest = algorithm.digest();
      return HexUtil.encodeHexStr(messageDigest);
    } catch (Exception e) {
      throw new RuntimeException("Generate Token String failed: " + e.getMessage());
    }
  }

}
