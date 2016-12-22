# monkey_mongo
mongo 3.3驱动封装

> mongo-driver 3.3版本代码采用了链式开发,并且相对于2.X版本的驱动在操作CRUD上更加方便,并且兼容之前的2.X版本.

> MongoCollection覆盖了所有CRUD操作.

- 由于spring-data框架封装的不够完善，所以准备封装一层client
- driver选择3.3版本（spring-data的驱动版本延后driver版本一年...）
- http://mongodb.github.io/mongo-java-driver/3.3/driver/reference/crud/ 官网可以看出渠道的2.X版本和3.X版本差别很大
- 框架全部统一使用3.X的新特性
- mongo driver自带连接池功能，可以在MongoClient中进行设置
- monkey-mongodb使用fastjson进行序列化
- 对外暴露MongoManager来获取MongoClient和mongoDatabase(基于driver3.X)
- client端只需要实现AbstractMongoDao即可(里面封装了常用的CRUD操作)

# TODO
- 进行集群的测试（driver中支持多host的配置，需测试）
- 如何检测mongo server的可用（在MongoManager中实现）
- 集成JMX

# 废弃代码中的java bean映射方式集成morphia
