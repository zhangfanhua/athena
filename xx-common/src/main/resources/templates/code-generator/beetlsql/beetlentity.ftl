import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * @description ${classInfo.classComment}
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${classInfo.className} implements Serializable {

    private static final long serialVersionUID = 1L;

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
    /**
    * ${fieldItem.fieldComment}
    */
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};

</#list>
</#if>

}
