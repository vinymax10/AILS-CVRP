variable# AILS-CVRP: An Adaptive Iterated Local Search Heuristic for the Large-scale Capacitated Vehicle Routing Problem

Adaptive Iterated Local Search (AILS) is an iterated local search-based meta-heuristic that embeds adaptive strategies to tune  diversity control parameters. These parameters are the perturbation degree and the acceptance criterion. They are key parameters to ensure that the method escapes from local optima and keeps an adequate level of exploitation and exploration of the method. This algorithm is an adaptation of the AILS-PR [1], but applied to the Heterogeneous Fleet Vehicle Routing Problem. Its implementation is in JAVA language.

## References

Those interested in using any part of this algorithm in academic works must cite the following references:

[1] Máximo, Vinícius R., Nascimento, Mariá C.V. (2021).
A hybrid adaptive iterated local search with diversification control to the capacitated vehicle routing problem. European Journal of Operational Research, Volume 294, p. 1108-1119, https://doi.org/10.1016/j.ejor.2021.02.024 (also available at [aXiv](https://arxiv.org/abs/2012.11021)).

[2] Máximo, Vinícius R., Cordeau, Jean-François, Nascimento, Mariá C.V. (2022).
An adaptive iterated local search heuristic for the Heterogeneous Fleet Vehicle Routing Problem. Computers & Operations Research, Volume 148, p. 105954.
https://doi.org/10.1016/j.cor.2022.105954 (also available at [aXiv](https://arxiv.org/abs/2111.12821)).

## To run the algorithm

```console
java -jar AILS.jar -file Instances/Taillard_20.txt -rounded false -best 4760.67 -limit 100 -stoppingCriterion Time 
```

Run the AILS class that has the following parameters:

**-file** : Enter the file address of the problem instance.

**-rounded** : Indicate whether the instance has rounded distances or not. The options are: [false, true].

**-limit** : Enter a value that will be used in the stopping criterion. If the stopping criterion is time, this parameter will be considered as the timeout in seconds. If the stopping criterion is based on the number of iterations then this parameter will indicate the number of iterations. The default value is Double.MAX_VALUE.

**-best** : Indicate the value of the optimal solution. The default value is 0.

**-varphi** : parameter of the feasibility and local search that refers to the maximum cardinality of 𝛿(𝑣) – nearest neighbors of 𝑣. The default value is 20.

**-gamma** : number of iterations for AILS to perform a new adjustment of variable 𝜔. The default value is 20.

**-dMax** : reference starting distance between the reference solution and the current solution obtained after the local search. The default value is 30.

**-dMin** : reference final distance between the reference solution and the current solution obtained after the local search. The default value is 15.

**-stoppingCriterion** : It is possible to use 2 types of stopping criteria. Below is a description of each:
* **Time** : The algorithm stops when the total time in seconds is reached.
* **Iteration** : The algorithm stops when the number of total iterations is reached. 

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](https://opensource.org/licenses/MIT)**
- Copyright(c) 2022 Vinícius R. Máximo
