package huaminglin.demo.distributed.db.repository.slave;

import huaminglin.demo.distributed.db.model.Goods;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsSlaveMapper {
  List<Goods> getGoods(@Param("name") String name);
}
