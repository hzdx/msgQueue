## msgQueue  
通过Hazelcast实现的分布式消息队列
### msgQueue-base
存放pojo和工具类  
### msgQueue-receiver
接收消息，加入队列，并立即给予响应  
### msgQueue-processor
从队列中取出消息，处理消息