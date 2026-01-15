package org.dromara.sqlrest.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MCP server address prefix")
public class McpServerAddrResponse {

  @ApiModelProperty("SSE address path")
  private String sseAddrPrefix;

  @ApiModelProperty("StreamHttp address path")
  private String streamAddrPrefix;
}
