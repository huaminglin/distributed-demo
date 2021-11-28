package huaminglin.demo.distributed.db.repository.mapper;

import huaminglin.demo.distributed.db.model.Goods;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMapper {
  void addGoods(@Param("goods") Goods goods);
  List<Goods> getGoods(@Param("name") String name);
}
