# AILS-II: An Adaptive Iterated Local Search Heuristic for the Large-scale Capacitated Vehicle Routing Problem

AILS-II is an Adaptive Iterated Local Search (AILS) meta-heuristic that embeds adaptive strategies to tune  diversity control parameters. These parameters are the perturbation degree and the acceptance criterion. They are key parameters to ensure that the method escapes from local optima and keeps an adequate level of exploitation and exploration of the method. Its implementation is in JAVA language.

## References

Those interested in using any part of this algorithm in academic works must cite the following references:

[1] Máximo, Vinícius R., Cordeau, Jean-François, Nascimento, Mariá C.V. (2023).
AILS-II: An Adaptive Iterated Local Search Heuristic for the Large-scale Capacitated Vehicle Routing Problem. Under Review. (available at [HEC repository](https://chairelogistique.hec.ca/wp-content/uploads/2023/12/IJOC_Maximoetal2023_withouthighlights.pdf)).

[2] Máximo, Vinícius R., Nascimento, Mariá C.V. (2021).
A hybrid adaptive iterated local search with diversification control to the capacitated vehicle routing problem. European Journal of Operational Research, Volume 294, p. 1108-1119, https://doi.org/10.1016/j.ejor.2021.02.024 (also available at [aXiv](https://arxiv.org/abs/2012.11021)).

[3] Máximo, Vinícius R., Cordeau, Jean-François, Nascimento, Mariá C.V. (2022).
An adaptive iterated local search heuristic for the Heterogeneous Fleet Vehicle Routing Problem. Computers & Operations Research, Volume 148, p. 105954.
https://doi.org/10.1016/j.cor.2022.105954 (also available at [aXiv](https://arxiv.org/abs/2111.12821)).

## Cite

To cite the contents of this repository, please cite this the paper and this repo.
AILS-II: An Adaptive Iterated Local Search Heuristic for the Large-scale Capacitated Vehicle Routing Problem. Under Review. (available at [HEC repository](https://chairelogistique.hec.ca/wp-content/uploads/2023/12/IJOC_Maximoetal2023_withouthighlights.pdf)).

Below is the BibTex for citing this snapshot of the respoitory.

```
@misc{Maximoetal2024,
  author =        {V. R. Máximo and J.-F.Courdeau and M. C. V. Nascimento},
  publisher =     {INFORMS Journal on Computing},
  title =         {AILS-II: An Adaptive Iterated Local Search Heuristic for the Large-scale Capacitated Routing Problem},
  year =          {2024},
  doi =           {10.1287/ijoc.2023.0106.cd},
  url =           {https://github.com/INFORMSJoC/2023.0106},
  note =          {Available for download at https://github.com/INFORMSJoC/2023.0106},
}  
```

## To run the algorithm

```console
java -jar AILSII.jar -file Instances/X-n214-k11.vrp -rounded true -best 10856 -limit 100 -stoppingCriterion Time 
```

Run the AILSII class that has the following parameters:

**-file** : the file address of the problem instance.

**-rounded** :  A flag that indicates whether the instance has rounded distances or not. The options are: [false, true]. The default value is true.

**-stoppingCriterion** : It is possible to use two different stopping criteria:
* **Time** : The algorithm stops when a given time in seconds has elapsed; 
* **Iteration** :  The algorithm stops when the given number of iterations has been reached. 

**-limit** : Refers to the value that will be used in the stopping criterion. If the stopping criterion is a time limit, this parameter is the timeout in seconds. Otherwise, this parameter indicates the number of iterations. The default value is the maximum limit for a double precision number in the JAVA language (Double.MAX_VALUE).

**-best** :  Indicates the value of the optimal solution. The default value is 0.

**-varphi** :  Parameter of the feasibility and local search methods that refers to the maximum cardinality of the set of nearest neighbors of the vertices. The default value is 40. The larger it is, the greater the number of movements under consideration in the methods. 

**-gamma** :  Number of iterations for AILS-II to perform a new adjustment of variable 𝜔. The default value is 30.

**-dMax** : Initial reference distance between the reference solution and the  solution obtained after the local search. The default value is 30.

**-dMin** : Final Reference distance between the reference solution and the solution obtained after the local search. The default value is 15.

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](https://opensource.org/licenses/MIT)**
- Copyright(c) 2022 Vinícius R. Máximo
