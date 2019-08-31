# graal-bootstrap

> A simple project to get you quickly going with a non-GraalVM-based project that allows you to still use Graal to run JS code on a stock JDK.

## Tests

All tests are currently handled by running a one-liner of JavaScript code that computes the first 1000 elements of Fibonacci's sequence and places them into an array. The one liner is as such:

```javascript
new Array(1000).fill(0).reduce((out, _, i) => out.concat(i < 2 ? 1 : out[i - 1]  + out[i - 2]), [])
```

At the moment, the results for running the program on OpenJDK 8 with an i7-7700k on Arch Linux are as such:

```
----- Context Creation -----
580.37s total, 58.04ms avg, 55.62ms low (7154), 441.82ms high (0)

----- Single Context -----
578.84s total, 57.88ms avg, 54.97ms low (7667), 85.18ms high (8898)
```

Running the same tests in Node.js using my own custom tool called [benchmarky](https://github.com/Rayzr522/benchmarky) returns the following results:

NAME | REP | TOTAL | AVG | LOW | HIGH 
------|-----|-------|-----|-----|-----
fibonacci-oneliner | 10000 | 29292.00000000ms | 2.92920000ms | 2ms (5) | 5ms (193) 

*The numbers in parenthesis for low & high are the index of the repetition which resulted in that number.*
 
There is clearly a bit of a difference between the two benchmarks. I am going to continue researching ways to improve the speed of Graal on a stock JDK.
 
## Join Me

[![Discord Badge](https://github.com/Rayzr522/ProjectResources/raw/master/RayzrDev/badge-small.png)](https://rayzr.dev/join)
