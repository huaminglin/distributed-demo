package huaminglin.demo.distributed.db.repository;

import huaminglin.demo.distributed.db.model.Goods;
import huaminglin.demo.distributed.db.repository.master.GoodsMasterMapper;
import huaminglin.demo.distributed.db.repository.slave.GoodsSlaveMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsRepository {
  private final GoodsMasterMapper goodsMasterMapper;
  private final GoodsSlaveMapper goodsSlaveMapper;

  public GoodsRepository(GoodsMasterMapper goodsMasterMapper,
      GoodsSlaveMapper goodsSlaveMapper) {
    this.goodsMasterMapper = goodsMasterMapper;
    this.goodsSlaveMapper = goodsSlaveMapper;
  }

  public void addGoods(Goods goods) {
    goodsMasterMapper.addGoods(goods);
  }

  public List<Goods> getGoods(String name) {
    return goodsSlaveMapper.getGoods(name);
  }
}
