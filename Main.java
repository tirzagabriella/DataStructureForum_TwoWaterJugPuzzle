import java.util.HashSet;

public class Main {
    static int smallCap = 3;
    static int largeCap = 4;
    static int target = 2;
    static HashSet<String> visited = new HashSet<>();
    public static void main(String[] args) {


        long startTime = System.nanoTime();
        waterJugSolveIteration();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        System.out.println("Duration: " + duration/1000000 + " ms");

        System.out.println("==============================");

        long recStartTime = System.nanoTime();
        waterJugSolveRecursive(0,0);
        long recEndTime = System.nanoTime();
        long recursiveDuration = (recEndTime - recStartTime);
        System.out.println("Duration: " + recursiveDuration/1000000 + " ms");
    }


    static void waterJugSolveIteration(){
        // starting with empty jugs
        int largeJug = 0;
        int smallJug = 0;

        int excess = 0;

        // iterate while target amount is not within any of the jugs
        // example : if target is 2, then the answer should either be:
        // (largeJug = 2 & smallJug = 0) or (largeJug = 0 & smallJug = 2)
        while (!(largeJug == target && smallJug == 0) && !(largeJug == 0 && smallJug == target)) {
            if(largeJug == 0) {
                largeJug = largeCap;
            } else if (largeJug > 0){
                if(smallJug == smallCap) {
                    smallJug = 0;
                } else if(smallJug < smallCap) {
                    smallJug = smallJug + largeJug;
                    largeJug = 0;
                    if(smallJug > smallCap){
                        excess = smallJug - smallCap;
                        largeJug = excess;
                        smallJug = smallCap;
                    }
                }
            }
            System.out.println(largeJug + " in Large Jug, " + smallJug + " in Small Jug");
        }
    }

    static boolean waterJugSolveRecursive(int largeJug, int smallJug){
        if ((largeJug == target && smallJug == 0 ) || (smallJug == target && largeJug == 0)) {
            System.out.println(largeJug + " in Large Jug, " + smallJug + " in Small Jug");
            return true;
        }

        String state = largeJug + " in Large Jug, " + smallJug + " in Small Jug";
        if (!visited.contains(state)){
            System.out.println(state);

            // add combination to list of visited combinations
            visited.add(state);

            // check 6 outcome possibilities and check if one of them is the solution
            return (
                    waterJugSolveRecursive(0, smallJug)
                || waterJugSolveRecursive(largeJug, 0)
                || waterJugSolveRecursive(largeCap, smallJug)
                || waterJugSolveRecursive(largeJug, smallCap)
                || waterJugSolveRecursive(
                largeJug + Math.min(smallJug, (largeCap - largeJug)),
                smallJug - Math.min(smallJug, (largeCap - largeJug)))
                || waterJugSolveRecursive(
                largeJug - Math.min(largeJug, (smallCap - smallJug)),
                smallJug + Math.min(largeJug, (smallCap - smallJug))));
        } else {
            return false;
        }
    }
}

/*
ITERATIVE ANALYSIS PERFORMANCE
The time complexity using iterative solution for the water jug puzzle is O(target * largeCapacity)
and for the space complexity is O(target * largeCapacity) also. This is because, in the worst-case situation,
we might need to continually fill the larger jug with water before transferring its contents to the smaller jug
with smaller caps until we reach the appropriate level of water. The quantity of procedures necessary would therefore
be inversely proportional to the maximum capacity of each jug. The number of iterations is related to the goal
amount of water since we repeat the while loop until we reach the desired level of water.
O(target * largeCapacity) would be the overall time complexity as a result.
 */

/*
RECURSIVE ANALYSIS PERFORMANCE
On the other hand, the recursive method's is not too straightforward, as it might actually be quite faster
than the predicted worst case time complexity. Let's say n is the largest jug capacity and d is the depth of
the recursion tree (the most depth is the target amount of water we're looking for)/ In the worst case scenario,
if all jug state combinations are unique and each level of the recursive tree has 6 possible outcomes, number of
function call will be bounded by 6^d. Each function call takes O(n) time, therefore total time complexity is
O(target * n). So to put it bluntly, the worst case time complexity can be denoted as O(target * 6^d * n).
However that is the worst case scenario and in practice, the actual function call can be much smaller since
revisited states are skipped. Therefore the runtime might actually be a lot faster.
 */