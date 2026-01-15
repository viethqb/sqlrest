package org.dromara.sqlrest.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedEntityIdName {

  @ApiModelProperty("ID number")
  private Long id;

  @ApiModelProperty("Name")
  private String name;

  @ApiModelProperty("Whether selected")
  private Boolean selected;
}
