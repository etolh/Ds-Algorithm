
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;


public class SeamCarver {
	
	private Picture pic;
	private int W, H;			//Width(col)xHeight(row) of original picture
	private double[][] energy;
	
	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if(null == picture)  throw new IllegalArgumentException();
		this.pic = new Picture(picture);
		this.W = pic.width();
		this.H = pic.height();
		this.energy = new double[H][W];
		
		for(int r = 0; r < H; r++) 
			for(int c = 0; c < W; c++) { 
				if(c == 0 || c == (W - 1)) {
					energy[r][c] = 1000.0;
				} else if(r == 0 || r == (H - 1)) {
					energy[r][c] = 1000.0;
				}else {
					calEng(c, r);
				}
			}
	}
	
	// current picture
	public Picture picture() {
		Picture cpic = new Picture(this.pic);
		return cpic;
	}
	
	// width of current picture
	public int width() {
		return W;
	}
	
	// height of current picture
	public int height() {
		return H;
	}
	
	
	private void calEng(int x, int y) {
		//获取p(x,y)能量：DigX, DigY
		int rgbx1 = pic.get(x+1, y).getRGB();
		int rgbx2 = pic.get(x-1, y).getRGB();
		int rgby1 = pic.get(x, y+1).getRGB();
		int rgby2 = pic.get(x, y-1).getRGB();
		int result = disEng(rgbx1,rgbx2) +disEng(rgby1, rgby2);
		energy[y][x] = Math.sqrt(result);
	}
	
	private void validate(int x, int y){
		if(x < 0 || x >= W)	throw new IllegalArgumentException();
		if(y < 0 || y >= H)	throw new IllegalArgumentException();
	}
	
	private int disEng(int rgb1, int rgb2){
		int ry = ((rgb1 >> 16) & 0xFF) - ((rgb2 >> 16) & 0xFF);
		int gy = ((rgb1 >> 8) & 0xFF) - ((rgb2 >> 8) & 0xFF);
		int by = ((rgb1 >> 0) & 0xFF) - ((rgb2 >> 0) & 0xFF);
		return (ry * ry + gy * gy + by * by);
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		
		validate(x, y);
		if(x == 0 || x == (W - 1))	return 1000.0;
		if(y == 0 || y == (H - 1))	return 1000.0;
		
		//获取p(x,y)能量：DigX, DigY energy[y][x]
		return energy[y][x];
	}
	
	
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		// transpose the image
		Picture tpic = transposePic(pic);
		SeamCarver seamCarver = new SeamCarver(tpic);
		return seamCarver.findVerticalSeam();
	}
	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam()  {
		
		double[] distTo = new double[W*H];		//p(x,y)->index=y*W+x,distTo[v]表示v到s上路径顶点权重之和
		int[] edgeTo = new int[W*H];			//edgeTo[v]表示最短路径上v的父节点
		Queue<Integer> queue = new Queue<Integer>();	//将起点加入到队列中
		
		// 设置起点的edgeTo[s]=s,distTo[s]=自身权重,其他顶点的distTo为正无穷
		for(int i = 0; i < W; i++) //列
			for(int j = 0; j < H; j++){ //行
				int idx = cor2ind(i, j);
				distTo[idx] = Double.POSITIVE_INFINITY;
				if(j == 0) {
					distTo[idx] = energy[j][i];
					edgeTo[idx] = idx;
				}
			}
		
		// 遍历第一行(s,0),取顶点加入到队列中放松vg
		for(int s = 0; s < W; s++) { 
			// 起点为s(s,0),添加到队列中，同一层遍历用bfs
			queue.enqueue(cor2ind(s, 0));
			while(!queue.isEmpty()){
				int v = queue.dequeue();
				
				// 放松顶点v
				int x = v % W;
				int y = v / W;
				// 到达底层不动
				if(y == (H-1))	continue;
				
				// 分别放松v三个方向的边:下方顶点v+W
				if(distTo[v + W] > (distTo[v] + energy[y+1][x])){
					// 放松并加入到队列中
					distTo[v + W] = distTo[v] + energy[y+1][x];
					edgeTo[v + W] = v;
					queue.enqueue(v+W);
				}
				
				// x!=0时可以放松左下侧顶点
				if(x != 0) {
					if(distTo[v + W - 1] > (distTo[v] + energy[y+1][x-1])){
						// 放松并加入到队列中
						distTo[v + W - 1] = distTo[v] + energy[y+1][x-1];
						edgeTo[v + W - 1] = v;
						queue.enqueue(v + W - 1);
					}
				}
				
				// x!=w-1时可以放松右下侧顶点
				if(x != (W-1)) {
					if(distTo[v + W + 1] > (distTo[v] + energy[y+1][x+1])){
						// 放松并加入到队列中
						distTo[v + W + 1] = distTo[v] + energy[y+1][x+1];
						edgeTo[v + W + 1] = v;
						queue.enqueue(v + W + 1);
					}
				}
			}
		}
		
		// 完成所有顶点放松后，统计底层最小距离
		double total = distTo[cor2ind(0,H-1)];
		int target = 0;	//底层最短距离顶点
		for(int t = 0; t < W; t++){
			int index = cor2ind(t,H-1);
			if(distTo[index] < total){
				total = distTo[index];
				target = t;
			}
		}
		
		// 最短距离底层顶点为(target,H-1)根据edgeTo返回最短路径-列坐标x (每行一个，共H个)
		int[] res = new int[H];
		int i = H-1;
		int tidx = cor2ind(target, H-1);
		while(edgeTo[tidx] != tidx){
			res[i--] = (tidx % W); //res存储x坐标
			tidx = edgeTo[tidx];
		}
		res[i]  = (tidx % W);
		return res;
	}
	
	// 数组二维坐标变成一维坐标
	private int cor2ind(int x, int y){
		return (x + y * W);
	}
	
	// 转置图
	private Picture transposePic(Picture pic){
		Picture tpic = new Picture(pic.height(), pic.width());
		for(int col = 0; col < pic.height(); col++)
			for(int row = 0; row < pic.width(); row++){
				tpic.set(col, row, pic.get(row, col));
			}
		return tpic;
	}
	
	//判断seam是否合理：两相邻元素距离>1或超出范围
	private void validateSeam(int[] seam, int th){
		for(int i = 0; i < seam.length; i++){
			if(seam[i] >= th || seam[i] < 0) throw new IllegalArgumentException();
			if(i < seam.length - 1)
				if(Math.abs(seam[i]-seam[i+1])>1) throw new IllegalArgumentException();
		}
	}
	
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		// 先转置图，用removeVerticalSeam,再转置回来
		if(seam==null) throw new IllegalArgumentException();
		if(H <= 1) throw new IllegalArgumentException();
		if(seam.length != W)	throw new IllegalArgumentException();
		validateSeam(seam, H);
		
		Picture tpic = transposePic(this.picture());
		SeamCarver seamCarver = new SeamCarver(tpic);
		seamCarver.removeVerticalSeam(seam);
		this.pic = transposePic(seamCarver.picture());	//将裁剪后的pic转置回来
	}
	
	
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		
		if(seam==null) throw new IllegalArgumentException();
		if(width() <= 1)	throw new IllegalArgumentException();
		validateSeam(seam, W);
		if(seam.length != H)	throw new IllegalArgumentException();
		
		for(int row = 0; row < H; row++) {
			int tc = seam[row]; //当前行需被移出的像素位置
			if(tc == (W-1))	{
				energy[row][W-2]=1000.0;
				continue;	//最后一列删除无需移动
			}
			// 移动energy: energy[i]行tc+1~W-1位置的能量点向前移动
			System.arraycopy(energy[row], tc+1, energy[row], tc, W-tc-1);
			if(tc == 0)	energy[row][0] = 1000.0; //第一列删除，设为1000.0
		}
		
		this.W--;	//宽度-1
		//重新计算能量:对于每一列移除点，重新计算其上下左右点的变化
		for(int r = 1; r < H - 1; r++) {
			int tc = seam[r]; // 列
			if(tc > 1) 		calEng(tc-1, r); //left
			if(tc < (W-1)) 	calEng(tc, r); //right
			if(r > 1) 		calEng(tc, r-1);		//up
			if(r < H - 2) 	calEng(tc, r+1); //down
		}
	}
}
