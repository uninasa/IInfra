package cn.uninasa.core.result;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页请求参数")
public class PageRequest {

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序字段（驼峰格式）", example = "createTime")
    private String sortField;

    @ApiModelProperty(value = "排序方式（asc/desc）", example = "desc")
    private String sortOrder = "asc";

    /**
     * 转换为 MyBatis-Plus 的 Page 对象
     */
    public <T> Page<T> toPage() {
        Page<T> page = new Page<>(pageNo, pageSize);
        if (sortField != null && !sortField.isEmpty()) {
            page.addOrder("desc".equalsIgnoreCase(sortOrder) 
                ? OrderItem.desc(sortField) 
                : OrderItem.asc(sortField));
        }
        return page;
    }
}
