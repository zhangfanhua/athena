### 新增( ${classInfo.schema+classInfo.tableName} [ ${classInfo.classComment} ]) ###

**${classInfo.schema+classInfo.tableName}**

|  参数名称   | 参数说明 |  类型  | schema |
| :---------: | :------: | :----: | :----- |
<#list classInfo.fieldList as fieldItem>
 |     ${fieldItem.fieldName}      |  ${fieldItem.fieldComment}   | ${fieldItem.fieldClass} |        |
</#list>
