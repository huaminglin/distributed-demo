package huaminglin.demo.distributed.db.repository.master;

import huaminglin.demo.distributed.db.model.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMasterMapper {
  void addGoods(@Param("goods") Goods goods);
}
