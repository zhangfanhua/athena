import java.util.Map;

/**
 * @description ${classInfo.classComment}
 * @author ${authorName}
 * @date ${.now?string('yyyy-MM-dd HH:mm:ss')}
 */
public interface ${classInfo.className}Service {

    /**
    * 新增
    */
    void insert(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 删除
    */
    void delete(String id);

    /**
    * 更新
    */
    void update(${classInfo.className} ${classInfo.className?uncap_first});

    /**
    * 根据主键 id 查询
    */
    ${classInfo.className} info(String id);

    /**
    * 分页查询
    */
    PageInfo<${classInfo.className}> pageList();

}
