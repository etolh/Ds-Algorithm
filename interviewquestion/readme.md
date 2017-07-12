###一，UnionFind
####Question1: Social network connectivity. 

1. 问题描述：Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships...
即给一个包含n个成员和m个连接关系的社交网络，求出n个成员彼此连接的最早时间。时间复杂度不超过mlogn，空间复杂度不超过n。

2. 分析：n个成员彼此连接，即说明只有一个集合，直接调用WeightedQuickUnion模型，并设置一个setcount记录集合的数目，初始时集合数目为n，每次当两个互不相连的成员连接时，setcount--；当setcount=1时，即说明彼此相互连接。


####Question2：Union-find with specific canonical element.

1. 问题描述： Add a method find() to the union-find data type so that find(i) returns the largest element in the connected component containing i...
即添加一个方法findMax(i):返回包含i的集合中的最大元素，其他方法应该为对数级

2. 分析：直接使用一个数组max``[root]``来记录每个集合的最大元素，且记录最大元素下标为集合的根结点。使用WeightedUF模型，初始时每个集合的max为自身；进行union操作，寻找集合根结点max``[root]``，连接的集合之间进行比较，取最大值。


####Question3：Successor with delete.

1. 问题： Given a set of n integers S={0,1,...,n−1} and a sequence of requests of the following form:
    Remove x from S；Find the successor of x: the smallest y in S such that y≥x...
即一个数组s={0,1,2,...,n-1}，从中删除x，在剩下的s找到一个最小的y使得y>=x。且使得时间复杂度为logn

2. 分析：这个问题直观上一看觉得毫无意义，x后面的数不就是x+1,与UF问题毫无关系；但仔细思考便会发现，当删除x后y=x+1，继续删除x-1，此时y不是x，而是为x+1,是因为删除数后数组s在发生改变。使用一个数组rm记录数组s中x是否被删除，删除x时，令rm``[x]``=true，且若x前后也有被删除的数，则将其进行连接。这里联系上面Q2，当想要寻找y时，若x已被删除，则y是包含x的连接集合中最大值+1（findMax+1），未被删除，y=x。