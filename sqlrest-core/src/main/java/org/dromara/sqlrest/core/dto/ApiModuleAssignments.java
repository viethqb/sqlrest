package org.dromara.sqlrest.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Module interface tree")
public class ApiModuleAssignments extends EntityIdNameResponse {

  @ApiModelProperty("Interface list")
  private List<SelectedEntityIdName> children;

}
