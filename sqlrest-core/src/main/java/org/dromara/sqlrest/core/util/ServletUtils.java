// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.core.util;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Utility class for retrieving HTTP parameters from Servlet server
 */
@Slf4j
public final class ServletUtils {

  public static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  public static String getDomain() {
    HttpServletRequest request = getHttpServletRequest();
    StringBuffer url = request.getRequestURL();
    return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
  }

  public static String getOrigin() {
    HttpServletRequest request = getHttpServletRequest();
    return request.getHeader("Origin");
  }

  public static String getPathUri() {
    HttpServletRequest request = getHttpServletRequest();
    return request.getRequestURI();
  }

  public static String getUserAgent() {
    HttpServletRequest request = getHttpServletRequest();
    return request.getHeader("user-agent");
  }


  /**
   * Get IP address
   * <p>
   * When using reverse proxy software like Nginx, IP address cannot be obtained through request.getRemoteAddr()
   * </p>
   * <p>
   * If multiple levels of reverse proxy are used, X-Forwarded-For contains not just one IP address, but a series of IP addresses. The first valid IP string in X-Forwarded-For that is not "unknown" is the real IP address.
   * </p>
   */
  public static String getIpAddr() {
    HttpServletRequest request = getHttpServletRequest();
    String ip = "0.0.0.0";
    try {
      ip = request.getHeader("x-forwarded-for");
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
    } catch (Exception e) {
      log.error("get client IP address error: ", e);
    }

    return ip;
  }

}