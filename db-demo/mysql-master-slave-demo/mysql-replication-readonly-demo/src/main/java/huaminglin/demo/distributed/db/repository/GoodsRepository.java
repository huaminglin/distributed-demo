package huaminglin.demo.distributed.db.repository;

import huaminglin.demo.distributed.db.model.Goods;
import huaminglin.demo.distributed.db.repository.mapper.GoodsMapper;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GoodsRepository {
  private final GoodsMapper goodsMapper;

  public GoodsRepository(GoodsMapper goodsMapper) {
    this.goodsMapper = goodsMapper;
  }

  @Transactional
  public void addGoods(Goods goods) {
    goodsMapper.addGoods(goods);
  }

  @Transactional(readOnly = true)
  public List<Goods> getGoods(String name) {
    return goodsMapper.getGoods(name);
  }
}
