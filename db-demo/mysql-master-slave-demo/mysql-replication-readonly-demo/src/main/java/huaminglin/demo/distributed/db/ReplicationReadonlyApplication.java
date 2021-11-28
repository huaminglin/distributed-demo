package huaminglin.demo.distributed.db;

import huaminglin.demo.distributed.db.model.Goods;
import huaminglin.demo.distributed.db.repository.GoodsRepository;
import java.util.Date;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ReplicationReadonlyApplication {

  private static void addGoods(GoodsRepository goodsMasterRepository) {
    Goods goods = new Goods();
    goods.setCode("goods01");
    goods.setName("Goods 01: " + new Date());
    goodsMasterRepository.addGoods(goods);
  }

  private static List<Goods> getGoods(GoodsRepository goodsMasterRepository, String name) {
    return goodsMasterRepository.getGoods(name);
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(ReplicationReadonlyApplication.class, args);
    GoodsRepository goodsMasterRepository = context.getBean(GoodsRepository.class);
    addGoods(goodsMasterRepository);
    List<Goods> goodsList = getGoods(goodsMasterRepository, "Goods");
    for (Goods goods : goodsList) {
      System.out.println(goods);
    }
  }
}
