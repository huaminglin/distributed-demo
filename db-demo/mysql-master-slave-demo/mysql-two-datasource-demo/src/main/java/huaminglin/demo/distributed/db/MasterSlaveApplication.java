package huaminglin.demo.distributed.db;

import huaminglin.demo.distributed.db.model.Goods;
import huaminglin.demo.distributed.db.repository.GoodsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MasterSlaveApplication {

  private static void addGoods(GoodsRepository goodsMasterRepository) {
    Goods goods = new Goods();
    goods.setCode("goods01");
    goods.setName("Goods 01");
    goodsMasterRepository.addGoods(goods);
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(MasterSlaveApplication.class, args);
    GoodsRepository goodsMasterRepository = context.getBean(GoodsRepository.class);
    addGoods(goodsMasterRepository);
  }
}
