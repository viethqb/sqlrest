package org.dromara.sqlrest.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Client application list search")
public class AppClientSearchRequest extends EntitySearchRequest {

  @ApiModelProperty("Group ID")
  private Long groupId;
}
