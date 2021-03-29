--路由配置
CREATE TABLE ac.route_define (
     id character varying(50) PRIMARY KEY,
     path character varying(100),
     service_id character varying(50),
     enable_retry integer DEFAULT 0,
     enabled integer DEFAULT 1,
     strip_prefix integer DEFAULT 1
);
COMMENT ON COLUMN ac.route_define.id IS 'ID';
COMMENT ON COLUMN ac.route_define.path IS 'URL路径';
COMMENT ON COLUMN ac.route_define.service_id IS '服务ID';
COMMENT ON COLUMN ac.route_define.enable_retry IS '是否重试';
COMMENT ON COLUMN ac.route_define.enabled IS '是否启动';
COMMENT ON COLUMN ac.route_define.strip_prefix IS '去掉API前缀';