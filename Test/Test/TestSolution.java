package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Data.Instance;
import Improvement.FeasibilityPhase;
import Improvement.IntraLocalSearch;
import SearchMethod.Config;
import SearchMethod.ConstructSolution;
import SearchMethod.InputParameters;
import Solution.Solution;

public class TestSolution
{
	private Solution constructSolution()
	{
		String[] args= {"-file","Instances/X-n214-k11.vrp","-rounded","true","-best","10856","-limit","100","-stoppingCriterion","Time"};
		InputParameters reader=new InputParameters();
		reader.readingInput(args);
		
		Instance instance=new Instance(reader);
		Config config=reader.getConfig();
		
		Solution solution =new Solution(instance,config);
		ConstructSolution constructSolution=new ConstructSolution(instance,config);

		constructSolution.construct(solution);
		
		return solution;
	}
	
	private Solution constructSolutionFeasible()
	{
		String[] args= {"-file","Instances/X-n214-k11.vrp","-rounded","true","-best","10856","-limit","100","-stoppingCriterion","Time"};
		InputParameters reader=new InputParameters();
		reader.readingInput(args);
		
		Instance instance=new Instance(reader);
		Config config=reader.getConfig();
		
		Solution solution =new Solution(instance,config);
		ConstructSolution constructSolution=new ConstructSolution(instance,config);

		constructSolution.construct(solution);
		IntraLocalSearch intraLocalSearch=new IntraLocalSearch(instance,config);
		
		FeasibilityPhase feasibilityOperator=new FeasibilityPhase(instance,config,intraLocalSearch);
		feasibilityOperator.makeFeasible(solution);
		
		return solution;
	}
	
	@Test
	public void testObjectFuntionInRoute()
	{
		Solution solution =constructSolution();
		
		for(int i = 0; i < solution.numRoutes; i++)
		{
			assertEquals(solution.routes[i].F(), solution.routes[i].fRoute);
		}
	}
	
	@Test
	public void testRouteInitiatingWithoutDepot()
	{
		Solution solution =constructSolution();
		
		for(int i = 0; i < solution.numRoutes; i++)
		{
			assertEquals(solution.routes[i].first.name , 0);
		}
	}
	
	@Test
	public void testFeasibleRoute()
	{
		Solution solution =constructSolutionFeasible();

		for(int i = 0; i < solution.numRoutes; i++)
		{
			assertTrue(solution.routes[i].isFeasible());
		}
	}
	
	@Test
	public void testObjectFuntion()
	{
		Solution solution =constructSolution();
		double objectFuntion=0;
		for(int i = 0; i < solution.numRoutes; i++)
		{
			objectFuntion+=solution.routes[i].F();
		}
		
		assertEquals(solution.f, objectFuntion);
	}
	
	@Test
	public void testSumNumElements()
	{
		Solution solution =constructSolution();
		double sumNumElements=0;
		for(int i = 0; i < solution.numRoutes; i++)
		{
			sumNumElements+=solution.routes[i].getNumElements();
		}

		assertEquals((sumNumElements - solution.numRoutes), solution.getSize());
	}

}
