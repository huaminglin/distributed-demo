package huaminglin.demo.distributed.db.repository;

import huaminglin.demo.distributed.db.model.Goods;
import huaminglin.demo.distributed.db.repository.master.GoodsMasterMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsRepository {
  private final GoodsMasterMapper goodsMasterMapper;

  public GoodsRepository(GoodsMasterMapper goodsMasterMapper) {
    this.goodsMasterMapper = goodsMasterMapper;
  }

  public void addGoods(Goods goods) {
    goodsMasterMapper.addGoods(goods);
  }
}
