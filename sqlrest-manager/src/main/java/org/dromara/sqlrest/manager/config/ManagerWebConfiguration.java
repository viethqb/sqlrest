// Copyright tang.  All rights reserved.
// https://gitee.com/inrgihc/sqlrest
//
// Use of this source code is governed by a BSD-style license
//
// Author: tang (inrgihc@126.com)
// Date : 2024/3/31
// Location: beijing , china
/////////////////////////////////////////////////////////////
package org.dromara.sqlrest.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ManagerWebConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Access to http://localhost:8080/index.html will redirect to classpath:/index.html, i.e., resources/index.html
    registry.addResourceHandler("/index.html").addResourceLocations("classpath:/index.html");
    registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico");
    // Access to http://localhost:8080/static/*** will redirect to classpath:/static/, i.e., resources/static/
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:index.html");
  }
}
