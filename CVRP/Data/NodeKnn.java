package Data;

public class NodeKnn implements Comparable<NodeKnn>
{
	public int name;
	public double dist;

	@Override
	public String toString()
	{
		return "NodeKnn [name=" + name + ", dist=" + dist + "]";
	}

	public int compareTo(NodeKnn x)
	{
		if(this.dist < x.dist)
			return -1;
		else if(this.dist > x.dist)
			return 1;
		return 0;
	}
}
