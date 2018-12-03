create table user_keypair(
  id bigInt(20) not null auto_increment comment '主键id'
  temail varchar(32) not null default '' comment 'temail 地址',
  token varchar(64) not null default '' comment 'token',
  private_key varchar(128) not null default '' comment '私钥',
  public_key varchar(128) not null default '' comment '公钥'
  create_time bigInt(20)  not null default '' comment '创建时间',
  update_time bigInt(20)  not null default '' comment '创建时间',
  primary key(`id`)
) ENGINE=InnoDB default charset=utf8mb4 comment=`temail密钥表`;
