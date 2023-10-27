package com.myblog1.payload;
/*import io.swagger.annotation.ApiModel;
import io.swagger.annotation.ApiModelPropertiey;*/
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
//@ApiModel(description="Post model information")
@Data
public class PostDto {
    private long id;
    @NotEmpty 
    @Size(min = 2,message="title should have at least 2 characters")
    private String title;
    @NotEmpty (message="should not be Empty")
    @Size(min = 4)
    private  String description;
    @NotEmpty (message="Name may not be empty")
    private String content;

}
