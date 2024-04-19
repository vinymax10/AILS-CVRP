package Auxiliary;

import Solution.Node;
import Solution.Solution;

public class Distance 
{
	Node solutionB[];
	Node solutionA[];
	
	public int pairwiseSolutionDistance(Solution a, Solution b)
	{
		this.solutionB=b.getSolution();
		this.solutionA=a.getSolution();
		
		int neigh;
		int dist=0;
		for (int i = 0; i < solutionA.length; i++)
		{
			neigh=solutionA[i].next.name;
			if(solutionB[i].next.name!=neigh&&neigh!=solutionB[i].prev.name)
				dist++;

			if(solutionA[i].prev.name==0)
			{
				if(solutionB[i].next.name!=0&&0!=solutionB[i].prev.name)
					dist++;
			}
		}
		
		return dist;
	}
	
	public int pairwiseSolutionDistanceThreshold(Solution a, Solution b,double minDist)
	{
		this.solutionB=b.getSolution();
		this.solutionA=a.getSolution();
		
		int viz;
		int dist=0;
		for (int i = 0; i < solutionA.length &&dist<minDist; i++)
		{
			viz=solutionA[i].next.name;
			if(solutionB[i].next.name!=viz&&viz!=solutionB[i].prev.name)
				dist++;

			if(solutionA[i].prev.name==0)
			{
				if(solutionB[i].next.name!=0&&0!=solutionB[i].prev.name)
					dist++;
			}
			
		}
		
		return dist;
	}
	
}
