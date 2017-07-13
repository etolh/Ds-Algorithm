###Programming Assignment 2: Deques and Randomized Queues
实现两种抽象数据类型的API，要求泛型和可迭代，使用动态数组和链表。

####Deques
1. Deque双端队列可以在首端、尾端都能进行插入和删除，要求所有deque的操作都是常数时间（最坏情况），空间不超过48N+192，能对异常情况抛出异常。

2. 分析
v1: 由于removeFirst要求常数时间，使用链式，由于要删除最后一个结点，则需要使用双向链表。

####Randomized Queues
1. A randomized queue类似stack和queue，但其删除操作是随机删除队列中的一个元素，每次迭代的顺序随机，其他操作不变；要求迭代器队列顺序随机，队列操作常数时间，空间不超过48N+192，迭代器构造函数线性时间，其他常数时间，能对异常情况抛出异常。

2. 分析：
v1: 要求每次删除一个随机元素，则只能采用数组表示，使用StdRandom获取随机数，再通过数组获取随机元素。

####Permutation
1. 客户端测试：读取标准输入的所有元素，再选出k个元素随机输出


####优化
v1: 89--RandomizedQueues测试用例部分失败，原因是dequeue出队时删除元素，将后面元素前移时，先N--导致最后一个元素未能前移。

v2: 96--Permutation部分用例理解错误：Permutation要求是读取标准输入的所有元素，再选出k个元素随机输出

v3：RandomizedQueue的时间空间复杂度：由于不关心顺序，因此删除一个元素时无需进行移动，只需要和最后一个元素替换。bonus:要求Permutation中使用的RandomizedQueues插入的元素不超过k个，需求是必须读取所有的输入，可以StdIn.readAllStrings()读取所有的输入并保存在数组中，使用shuffle生成随机顺序，再读取k个到RandomizedQueues中。
