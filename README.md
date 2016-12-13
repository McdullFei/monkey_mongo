# monkey_mongo
mongo 3.3驱动封装


- 由于spring-data框架封装的不够完善，所以准备封装一层client
- driver选择3.3版本（spring-data的驱动版本延后driver版本一年...）
- http://mongodb.github.io/mongo-java-driver/3.3/driver/reference/crud/ 官网可以看出渠道的2.X版本和3.X版本差别很大
- mongo driver自带连接池功能，可以在MongoClient中进行设置
- 需要自己与spring框架做集成
- 框架全部统一使用3.X的新特性
- monkey-mongodb使用fastjson进行序列化
- 如何与logback集成？
- 进行集群的测试（driver中支持多host的配置，需测试）
- 如何检测mongo server的可用（在MongoManager中实现）