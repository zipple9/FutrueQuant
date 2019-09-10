


开发日志

需求：
    1.交易系统
    
    
    
    
    
    
    
    
    
    
    
7.21
    查询了7w条数据记录 耗时25s？
    
    
8.3
    对于数据读取  分析处理 可以应用 生产/消费的 多线程模型来处理
    
    
    
8.4
    多线程的话 ，在spring中怎么创建多线程呢
    
    在for里面加 新线程去跑  原来25s  现在26s 并没有区别
    在for里面加线程没有意义 
    
    
    回到最初的问题 在数据库里 取1w条数据 耗时3s多
    取5w条数据 耗时18s多  区别不是很大
    
    
9.3
    上了线程池 在读取数据这个环节 耗时居然还比 直接for循环读数据慢
    NoTP strategy    over   completed in 27280 ms
    TP strategy    over   completed in 31012 ms
    简直是无语了       
    NoTPAll strategy    over   completed in 8209 ms
    远不如一次性把数据都查出来快
    
9.5
    被自己蠢哭了  ，全部查询只查询了20000条数据  当然快了
    真实的耗时
    All strategy    over   completed in 24431 ms
    还是比较快的 毕竟只用了一次连接
9.6 
    在参数调优中  经过大量循环后出现了较大的cpu性能开销
    跑一次策略需要  25s左右
    现在需要调用策略324次  
    TP strategy    over   completed in 30134 ms
    324次调用
    
    cpu耗时大概已经达到了4s
      
    如果参数调优中 参数间隔更密集的话
    TP strategy    over   completed in 92394 ms
    2116次调用
    
    cpu耗时已经非常明显了   使用线程池提高性能
    